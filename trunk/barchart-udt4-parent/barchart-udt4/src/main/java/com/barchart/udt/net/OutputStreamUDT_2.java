package com.barchart.udt.net;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.IllegalBlockingModeException;

import com.barchart.udt.SocketUDT;

/**
 * {@link OutputStream} for UDT sockets.
 */
public class OutputStreamUDT_2 extends OutputStream {

	private final SocketUDT socketUDT;

	/**
	 * 
	 * @param socketUDT
	 *            The UDT socket.
	 */
	public OutputStreamUDT_2(final SocketUDT socketUDT) {

		if (!socketUDT.isBlocking()) {
			throw new IllegalBlockingModeException();
		}

		this.socketUDT = socketUDT;

	}

	@Override
	public void write(final int b) throws IOException {

		// Just cast it -- this is the same thing SocketOutputStream does.
		final byte[] bytes = { (byte) b };

		write(bytes);

	}

	@Override
	public void write(final byte[] bytes) throws IOException {

		write(bytes, 0, bytes.length);

	}

	@Override
	public void write(final byte[] bytes, final int off, final int len)
			throws IOException {

		final int count = socketUDT.send(bytes, off, off + len);

		if (count > 0) {
			assert count == len;
			return;
		}

		if (count == 0) {
			throw new IOException("UDT send time out");
		}

		throw new IllegalStateException("should not happen");

	}

	@Override
	public synchronized void close() throws IOException {
		if (socketUDT.isOpen()) {
			socketUDT.close();
		}
	}

}
