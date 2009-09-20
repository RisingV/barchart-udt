/**
 * =================================================================================
 *
 * BSD LICENCE (http://en.wikipedia.org/wiki/BSD_licenses)
 *
 * ARTIFACT='barchart-udt4'.VERSION='1.0.0-SNAPSHOT'.TIMESTAMP='2009-09-20_18-55-32'
 *
 * Copyright (C) 2009, Barchart, Inc. (http://www.barchart.com/)
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *     * Neither the name of the Barchart, Inc. nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Developers: Andrei Pozolotin;
 *
 * =================================================================================
 */
package com.barchart.udt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.nio.channels.spi.SelectorProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barchart.udt.SocketUDT;

/**
 * you must use {@link SelectorProviderUDT#openSocketChannel()} to obtain
 * instance of this class; do not use JDK
 * {@link java.nio.channels.SocketChannel#open()};
 * 
 * example:
 * 
 * <pre>
 * 
 * SocketChannel clientChannel = SelectorProviderUDT.DATAGRAM.openSocketChannel();
 * 
 * clientChannel.configureBlocking(true);
 * 
 * Socket clientSocket = clientChannel.socket();
 * 
 * InetSocketAddress clientAddress = new InetSocketAddress(&quot;localhost&quot;, 10000);
 * 
 * clientSocket.bind(clientAddress);
 * 
 * assert clientSocket.isBound();
 * 
 * InetSocketAddress serverAddress = new InetSocketAddress(&quot;localhost&quot;, 12345);
 * 
 * clientChannel.connect(serverAddress);
 * 
 * assert clientSocket.isConnected();
 * 
 * </pre>
 */
public class ChannelSocketUDT extends SocketChannel implements ChannelUDT {

	private static final Logger log = LoggerFactory
			.getLogger(ChannelSocketUDT.class);

	final SocketUDT socketUDT;

	ChannelSocketUDT(SelectorProvider provider, SocketUDT socketUDT) {
		super(provider);
		this.socketUDT = socketUDT;
	}

	@Override
	protected void implCloseSelectableChannel() throws IOException {
		socketUDT.close();
	}

	/*
	 * local volatile variable, which mirrors super.blocking, to avoid the cost
	 * of synchronized call inside isBlocking()
	 */
	private volatile boolean isBlockingMode = isBlocking();

	@Override
	protected void implConfigureBlocking(boolean block) throws IOException {
		socketUDT.configureBlocking(block);
		isBlockingMode = block;
	}

	private volatile boolean isConnectionPending;

	@Override
	public boolean connect(SocketAddress remote) throws IOException {

		if (!isOpen()) {
			throw new ClosedChannelException();
		}

		if (isConnected()) {
			log.warn("already connected; ignoring remote={}", remote);
			return true;
		}

		if (remote == null) {
			close();
			throw new NullPointerException("remote == null");
		}

		InetSocketAddress remoteSocket = (InetSocketAddress) remote;
		if (remoteSocket.isUnresolved()) {
			log.error("can not use unresolved address: remote={}", remote);
			close();
			throw new UnresolvedAddressException();
		}

		if (isBlocking()) {
			if (isConnectionPending) {
				close();
				throw new ConnectionPendingException();
			} else {
				isConnectionPending = true;
			}
			try {
				begin();
				socketUDT.connect(remoteSocket);
			} finally {
				end(true);
			}
			isConnectionPending = false;
			return socketUDT.isConnected();
		} else { // non Blocking
			final SelectionKeyUDT key = channelKey;
			if (key == null) {
				// this channel is independent of any selector
				log.error("UDT channel is in non blocking mode;"
						+ "must register with a selector "
						+ "before trying to connect()");
				throw new IllegalBlockingModeException();
			} else {
				// this channel is registered with a selector
				key.selectorUDT.submitConnectRequest(key, remoteSocket);
				/*
				 * connection operation must later be completed by invoking the
				 * finishConnect() method.
				 */
				return false;
			}
		}

	}

	/**
	 * note this is redundant for blocking mode
	 */
	@Override
	public boolean finishConnect() throws IOException {
		if (!isOpen()) {
			throw new ClosedChannelException();
		}
		if (isBlocking()) {
			return isConnected();
		} else { // non blocking
			if (isConnected()) {
				return true;
			} else {
				IOException exception = connectException;
				if (exception == null) {
					return false;
				} else {
					throw exception;
				}
			}
		}
	}

	@Override
	public boolean isConnected() {
		return socketUDT.isConnected();
	}

	@Override
	public boolean isConnectionPending() {
		throw new RuntimeException("feature not available");
	}

	/**
	 * See {@link java.nio.channels.SocketChannel#read(ByteBuffer)} contract;
	 * note: this method does not return (-1) as EOS (end of stream flag)
	 * 
	 * @return <code><0</code> should not happen<br>
	 *         <code>=0</code> blocking mode: timeout occurred on receive<br>
	 *         <code>=0</code> non-blocking mode: nothing is received by the
	 *         underlying UDT socket<br>
	 *         <code>>0</code> actual bytes received count<br>
	 * @see com.barchart.udt.SocketUDT#receive(ByteBuffer)
	 * @see com.barchart.udt.SocketUDT#receive(byte[], int, int)
	 */
	@Override
	public int read(ByteBuffer buffer) throws IOException {

		if (buffer == null) {
			throw new NullPointerException("buffer == null");
		}

		final int remaining = buffer.remaining();

		if (remaining > 0) {

			final SocketUDT socket = socketUDT;
			final boolean isBlocking = isBlockingMode;

			final int sizeReceived;
			try {
				if (isBlocking) {
					begin(); // JDK contract for blocking calls
				}
				if (buffer.isDirect()) {
					sizeReceived = socket.receive(buffer);
				} else {
					assert buffer.hasArray();
					byte[] array = buffer.array();
					int position = buffer.position();
					int limit = buffer.limit();
					sizeReceived = socket.receive(array, position, limit);
					if (0 < sizeReceived && sizeReceived <= remaining) {
						buffer.position(position + sizeReceived);
					}
				}
			} finally {
				if (isBlocking) {
					end(true); // JDK contract for blocking calls
				}
			}

			// see contract for receive()

			if (sizeReceived < 0) {
				log.debug("nothing was received; socketID={}", socket.socketID);
				return 0;
			}

			if (sizeReceived == 0) {
				log.debug("receive timeout; socketID={}", socket.socketID);
				return 0;
			}

			if (sizeReceived <= remaining) {
				return sizeReceived;
			} else { // should not happen
				log.error("unexpected: sizeReceived > remaining; socketID={}",
						socket.socketID);
				return 0;
			}

		} else {
			return 0;
		}
	}

	@Override
	public long read(ByteBuffer[] dsts, int offset, int length)
			throws IOException {
		throw new RuntimeException("feature not available");
	}

	// guarded by 'this'
	protected Socket socketAdapter;

	@Override
	public Socket socket() {
		synchronized (this) {
			if (socketAdapter == null) {
				socketAdapter = new AdapterSocketUDT(this, socketUDT);
			}
			return socketAdapter;
		}
	}

	/**
	 * See {@link java.nio.channels.SocketChannel#write(ByteBuffer)} contract;
	 * 
	 * @return <code><0</code> should not happen<br>
	 *         <code>=0</code> blocking mode: timeout occurred on send<br>
	 *         <code>=0</code> non-blocking mode: buffer is full in the
	 *         underlying UDT socket<br>
	 *         <code>>0</code> actual bytes sent count<br>
	 * @see com.barchart.udt.SocketUDT#send(ByteBuffer)
	 * @see com.barchart.udt.SocketUDT#send(byte[], int, int)
	 */
	@Override
	public int write(ByteBuffer buffer) throws IOException {

		if (buffer == null) {
			throw new NullPointerException("buffer == null");
		}

		final int remaining = buffer.remaining();

		if (remaining > 0) {

			final SocketUDT socket = socketUDT;
			final boolean isBlocking = isBlockingMode;

			final int sizeSent;
			try {
				if (isBlocking) {
					begin(); // JDK contract for blocking calls
				}
				if (buffer.isDirect()) {
					sizeSent = socket.send(buffer);
				} else {
					assert buffer.hasArray();
					byte[] array = buffer.array();
					int position = buffer.position();
					int limit = buffer.limit();
					sizeSent = socket.send(array, position, limit);
					if (0 < sizeSent && sizeSent <= remaining) {
						buffer.position(position + sizeSent);
					}
				}
			} finally {
				if (isBlocking) {
					end(true); // JDK contract for blocking calls
				}
			}

			// see contract for send()

			if (sizeSent < 0) {
				log.debug("no buffer space for send; socketID={}",
						socket.socketID);
				return 0;
			}

			if (sizeSent == 0) {
				log.debug("send timeout; socketID={}", socket.socketID);
				return 0;
			}

			if (sizeSent <= remaining) {
				return sizeSent;
			} else { // should not happen
				log.error("unexpected: sizeSent > remaining; socketID={}",
						socket.socketID);
				return 0;
			}

		} else {
			return 0;
		}
	}

	@Override
	public long write(ByteBuffer[] srcs, int offset, int length)
			throws IOException {
		throw new RuntimeException("feature not available");
	}

	@Override
	public SocketUDT getSocketUDT() {
		return socketUDT;
	}

	@Override
	public KindUDT getChannelKind() {
		return KindUDT.CONNECTOR;
	}

	// note: 1<->1 mapping of channels and keys

	protected volatile SelectionKeyUDT channelKey;

	protected void setRegistredKey(SelectionKeyUDT key) {
		// one time init
		assert channelKey == null;
		channelKey = key;
	}

	// set by connector task

	protected volatile IOException connectException;

	protected void setConnectException(IOException exception) {
		this.connectException = exception;
	}

	@Override
	public boolean isOpenSocketUDT() {
		return socketUDT.isOpen();
	}

}
