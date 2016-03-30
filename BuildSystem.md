

# Introduction #

This project has 3 build systems:
  * Development CDT
  * Development NAR
  * Production NAR

each build system will produce artifacts with specific names and at specific locations which are arch and os dependent; barchart-udt native libarary loader will use the following search priority when looking for these artifacts:
  * Production NAR
  * Development CDT
  * Development NAR

you may want to delete specific artifact and/or location to force your java tests run only against the native artifacts you are interested in at the moment;

# Pre-Requisits #

## Ubuntu 10.10 ##

use 64 bit os;

use [multi-libs](https://help.ubuntu.com/community/InstallingCompilers);

sudo apt-get install gcc build-essential libc6-dev ia32-libs g++-multilib  libc6-dev-i386


## MacOSX 10.6.5 ##
use 64 bit os;

use [xcode](http://developer.apple.com/technologies/tools/xcode.html);

use [java developer package](https://issues.sonatype.org/browse/NAR-177);

## Windows 7 ##
use 64 bit os;

use [tdm-gcc](http://tdm-gcc.tdragon.net/) (install in c:\mingw32 & c:\mingw64);

use eclipse [launch script](http://code.google.com/p/barchart-udt/source/browse/trunk/barchart-udt4-parent/barchart-udt4/build/hudson/slave-job-windows-gpp.cmd) to set PATH for mingw32 or mingw64;

## JDK 1.6 ##
install both 32 and 64 bit java in separate folders;

do not include either java on the path;

macosx java comes as "bundle"; regardles of what (32/64 java) is active on your system by default you must explicitly provide -d32 / -d64 for all java invocations for this project;

## Eclipse 3.6 ##

use [pulse-eclipse](http://www.poweredbypulse.com/);

ask for current barchart-udt pulse-eclipse profile to be shared for you;

setup both 32 and 64 bit jre in your eclipse under the names "java32" and "java64"; macosx needs -d32 or -d64 options;

eclipse itself must run on top of JDK, not JRE;

## Tutorials ##

[intro for cdt](http://www.ibm.com/developerworks/opensource/library/os-eclipse-stlcdt/)

[intro for jni](http://www.cs.umanitoba.ca/~eclipse/8-JNI.pdf)

# Development CDT #

used for interactive development of both java and c++ code inside eclipse; allows to handle only single target os at a time; allows to test both 32/64 bits builds on the same os;

you must have eclipse cdt installed in your eclipse before you will be able to see cdt project configuration settings;

you must also activate [eclipse cdt perspective](http://www.ibm.com/developerworks/opensource/library/os-eclipse-stlcdt/fig08.jpg);

you must select your matching [build configuration](http://wiki.eclipse.org/images/f/ff/New_cdtwsconfig_wsContextMenu.png) Project->Build Configurations->Set Active->cdt-{arch}-{os}-gpp

there are 6 cdt configs in this project (for each arch and os):
  * cdt\_i386-Linux-gpp
  * cdt\_amd64-Linux-gpp
  * cdt\_i386-MacOSX-gpp
  * cdt\_x86\_64-MacOSX-gpp
  * cdt\_x86-Windows-gpp
  * cdt\_amd64-Windows-gpp

when cdt makes a build, you will see that a resource folder appears in you project with the same name as configuraton name; if you want cdt to re-build given configuration w/o "project clean" - just delete the resource folder; this folder is not part of svn;

you must select active build configuratin that matches your plaform arch and current java bitness;

you must have 2 named java jre defined in your eclipse: "java32" and "java64" (on macosx you must provide -d32/-d64 options to your java)

you must map one of these java jre to the "java se 1.6" in your eclipse;

every time you update/save your java / c++ code in eclipse the following will happen:
  * java will get compiled and placed in target/classes
  * c++ will get compiled and placed in cdt-{arch}-{os}-gpp folder as well as into target/test-classes
  * console will show c++ build errors, if any;
  * double-click on the highlighted c++ console error will bring you back to the c++ source at the location of the error;

to disspell the black magic of cdt build, take a look here: Project->Properties->Builders ; you will see, that when eclipse detects change, the following steps are performed:
  * Java Builder (provided by [JDT](http://www.eclipse.org/jdt/), runs incremental **javac**)
  * [Eclipse\_JNI\_Build](http://code.google.com/p/barchart-udt/source/browse/trunk/barchart-udt4-parent/barchart-udt4/build/script/eclipse-builder.ant) (provided by this project, runs incremental **javah**)
  * CDT Builder (provided by CDT, runs incremental **gcc**)
  * [Eclipse\_LIB\_Copy](http://code.google.com/p/barchart-udt/source/browse/trunk/barchart-udt4-parent/barchart-udt4/build/script/eclipse-builder.ant) (provided by this project, runs artifact **copy**)
  * Scanner Configuration Builder (provided by [CDT](http://www.eclipse.org/cdt/), runs cdt **parsers**)
  * Maven Project Builder (provided by [M2E](http://www.eclipse.org/m2e/), copies extra resources as defined in **pom.xml**)

location of "development cdt" native libs:<br>
${project}/target/test-classes/<br>
<br>
<h1>Development NAR</h1>

used for verification that maven-nar-plugin settings match to cdt settings; and that both cdt and nar produce identical libraries/artifacts;<br>
<br>
<a href='http://code.google.com/p/barchart-udt/source/browse/trunk/barchart-udt4-parent/barchart-udt4/aol.properties'>current nar settings</a>

there are numerous "maven launch configurations" provided for various maven-related tasks; study them and create your own;<br>
<br>
for example:<br>
<ul><li>"barchart-udt4_nar_macosx-32.m2e" will produce "development nar" libraries;<br>
</li><li>"barchart-udt4_test-macosx-32.m2e" will run all unit tests;</li></ul>

you must run appropriate version of m2e plugin in your eclipse;<br>
<br>
you must have appropriate version on maven installed in<br>
/opt/apache-maven-3.0.2<br>
<br>
alternatively, you do not have to have eclipse to run these builds; you can run these maven builds from command line;<br>
<br>
location of "development nar" native libs (example):<br>
${project}/target/test-classes/barchart-udt4-1.0.3-SNAPSHOT-i386-Linux-gpp-jni/lib/i386-Linux-gpp/jni<br>
<br>
<h1>Production NAR</h1>

used for multi-platform hudson auto build with maven-nar-plugin;<br>
<br>
the process involves brining up all target platforms (hudson slaves in vm), building libraries, running unit tests, running integration tests, and uploading jars and nars to the snapshot repo;