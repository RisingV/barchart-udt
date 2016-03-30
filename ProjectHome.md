######################################
# MOVED #

the project is moved to github
https://github.com/barchart/barchart-udt

this site is no longer maintained

######################################


**What is this?**


### TCP ###
TCP is [slow](http://barchart-udt.googlecode.com/svn/site/presentation/img6.html). UDT is [fast](http://barchart-udt.googlecode.com/svn/site/presentation/img9.html).

### UDT ###

[UDT is a reliable UDP](http://udt.sourceforge.net) based application level data transport protocol for distributed data intensive applications.
<br>
UDT is developed by<br>
<a href='http://users.lac.uic.edu/~yunhong'>Dr.Yunhong Gu</a>
and others at University of Illinois.<br>
<br>
UDT C++ implementation is available under<br>
<a href='http://udt.sourceforge.net/license.html'>BSD license</a>


<h3>Barchart-UDT</h3>

Barchart-UDT is a Java wrapper around native C++ UDT protocol implementation.<br>
<br>
Barchart-UDT is developed by Andrei Pozolotin and others at<br>
<a href='http://www.barchart.com'>Barchart, Inc.</a>
<br>
Barchart-UDT is available under<br>
<a href='http://en.wikipedia.org/wiki/BSD_licenses'>BSD license</a>
as well.<br>
<br>
Barchart-UDT exposes UDT protocol as both<br>
<a href='http://java.sun.com/javase/6/docs/api/java/net/Socket.html'>java.net.Socket</a>
and<br>
<a href='http://java.sun.com/javase/6/docs/api/java/nio/channels/SocketChannel.html'>java.nio.channels.SocketChannel</a>
<br>
and comes with a<br>
<a href='http://java.sun.com/javase/6/docs/api/java/nio/channels/spi/SelectorProvider.html'>java.nio.channels.spi.SelectorProvider</a>.<br>
<br>
<br>
<h3>Developers Welcome</h3>

If you are an expert in Java, NIO, JNI, C++, if you need this project for yourself,<br>
<br>
and if you can contribute to this project - please get in touch.<br>
<br>
<br>
<h3>Maven Dependency</h3>

Barchart-UDT <b>RELEASE</b> is available in <a href='http://mavencentral.sonatype.com/#search|ga|1|barchart'>maven central repository</a>.<br>
<br>
To use Barchart-UDT <b>SNAPSHOT</b> as maven2 dependency in your java project,<br>
<br>
please provide the following <b>repository</b> and <b>dependency</b> definitions in your <b>pom.xml</b>:<br>
<br>
<pre><code>	&lt;repositories&gt;<br>
		&lt;repository&gt;<br>
			&lt;id&gt;sonatype-nexus-snapshots&lt;/id&gt;<br>
			&lt;name&gt;Sonatype Nexus Snapshots&lt;/name&gt;<br>
			&lt;url&gt;https://oss.sonatype.org/content/repositories/snapshots&lt;/url&gt;<br>
			&lt;releases&gt;<br>
				&lt;enabled&gt;false&lt;/enabled&gt;<br>
			&lt;/releases&gt;<br>
			&lt;snapshots&gt;<br>
				&lt;enabled&gt;true&lt;/enabled&gt;<br>
			&lt;/snapshots&gt;<br>
		&lt;/repository&gt;<br>
	&lt;/repositories&gt;<br>
	&lt;dependencies&gt;<br>
		&lt;dependency&gt;<br>
			&lt;groupId&gt;com.barchart.udt&lt;/groupId&gt;<br>
			&lt;artifactId&gt;barchart-udt4-bundle&lt;/artifactId&gt;<br>
			&lt;version&gt;X.X.X-SNAPSHOT&lt;/version&gt;<br>
			&lt;type&gt;jar&lt;/type&gt;<br>
			&lt;scope&gt;compile&lt;/scope&gt;<br>
		&lt;/dependency&gt;<br>
	&lt;/dependencies&gt;<br>
</code></pre>
Here is an<br>
<a href='http://code.google.com/p/barchart-udt/source/browse/trunk/test-deps/pom.xml'>example pom.xml</a>
and<br>
<a href='http://code.google.com/p/barchart-udt/source/browse/trunk/test-deps/'>eclipse project</a>
.<br>
<br>
You can find out current <b>RELEASE</b> and <b>SNAPSHOT</b> versions here:<br>
<br>
Current <b>RELEASE</b>:<br>
<br>
<a href='http://repo1.maven.org/maven2/com/barchart/udt/'>http://repo1.maven.org/maven2/com/barchart/udt/</a>

Current <b>SNAPSHOT</b>:<br>
<br>
<a href='https://oss.sonatype.org/content/repositories/snapshots/com/barchart/udt/'>https://oss.sonatype.org/content/repositories/snapshots/com/barchart/udt/</a>

Please make sure you update <b>barchart-udt4-bundle</b> artifact version in your pom.xml<br>
<br>
<br>
<h3>Supported Platforms</h3>

Barchart-UDT is currently used on:<br>
<br>
<ul><li>Sun JDK: 6;</li></ul>

<table><thead><th> Platform </th><th> x86/i386 </th><th> x86-64/amd64 </th></thead><tbody>
<tr><td> Linux    </td><td>     YES  </td><td>     YES      </td></tr>
<tr><td> Mac OS X </td><td>     YES  </td><td>     YES      </td></tr>
<tr><td> Windows  </td><td>     YES  </td><td>     YES      </td></tr></tbody></table>

<br>

<h3>Documentation</h3>

Read the<br>
<a href='http://barchart-udt.googlecode.com/svn/site/presentation/udt-2009.html'>presentation</a>
, study<br>
<a href='http://barchart-udt.googlecode.com/svn/site/barchart-udt4/apidocs/index.html'>javadoc</a>
,<br>
<a href='http://barchart-udt.googlecode.com/svn/site/barchart-udt4/doxygen/index.html'>doxygen</a>
or browse the<br>
<a href='http://code.google.com/p/barchart-udt/source/browse/#svn/trunk/barchart-udt4'>source</a>
.<br>
<br>
Unit Tests in the source will provide good starting points for your java code.<br>
<br>
<h3>Development Environment</h3>
<a href='BuildSystem.md'>Build System</a>
<ul><li>jdk 1.6.0_23<br>
</li><li>maven 3.0.2<br>
</li><li>hudson 1.394<br>
</li><li>eclipse 3.6<br>
</li><li>cdt 7.1<br>
</li><li>gcc 4.5.1<br>
</li><li>tdm-gcc 4.5.1<br>
</li><li>vmware 7.1<br>
</li><li>ubuntu 10.10<br>
</li><li>macosx 10.6.5<br>
</li><li>windows 7</li></ul>


<h3>Contact Information</h3>
Please:<br>
<ol><li>join project's <a href='http://groups.google.com/group/barchart-udt'>mailing list / discussion group</a>
</li><li>enter your  <a href='http://code.google.com/p/barchart-udt/issues/list'>bug reports / feature requests</a> in the "Issues";<br>
</li><li>leave your  <a href='Comments.md'>valuable thoughts</a> in the "Comments";<br>
</li><li>email project owner at:  <a href='http://code.google.com/u/Andrei.Pozolotin/'>username</a> at gmail dot com;</li></ol>

Thank you.