
```
#
# A fatal error has been detected by the Java Runtime Environment:
#
#  EXCEPTION_ACCESS_VIOLATION (0xc0000005) at pc=0x70e64d28, pid=5204, tid=11908
#
# JRE version: 6.0_14-b08
# Java VM: Java HotSpot(TM) Client VM (14.0-b16 mixed mode, sharing windows-x86 )
# Problematic frame:
# C  [SocketUDT-windows-x86-32.dll+0x24d28]
#
# If you would like to submit a bug report, please visit:
#   http://java.sun.com/webapps/bugreport/crash.jsp
# The crash happened outside the Java Virtual Machine in native code.
# See problematic frame for where to report the bug.
#

---------------  T H R E A D  ---------------

Current thread (0x003a7400):  JavaThread "main" [_thread_in_native, id=11908, stack(0x008c0000,0x00910000)]

siginfo: ExceptionCode=0xc0000005, writing address 0x00000000

Registers:
EAX=0x00000001, EBX=0x269f9e68, ECX=0x003a7510, EDX=0x003d0000
ESP=0x0090fc78, EBP=0x0090fc78, ESI=0x269f9e68, EDI=0x003a7400
EIP=0x70e64d28, EFLAGS=0x00010202

Top of Stack: (sp=0x0090fc78)
0x0090fc78:   0090fcac 00919e37 003a7510 0090fcbc
0x0090fc88:   00000005 0090fc8c 269f9e68 0090fcbc
0x0090fc98:   269fb0c0 00000000 269f9e68 00000000
0x0090fca8:   0090fcbc 0090fce0 00912da1 00000000
0x0090fcb8:   00918269 22a35ca8 0090fcc0 26995ba8
0x0090fcc8:   0090fcf0 26995e00 00000000 26995bf8
0x0090fcd8:   0090fcbc 0090fcf0 0090fd0c 009102cb
0x0090fce8:   22a35ca8 2299eda8 22a70250 0090fd1c 

Instructions: (pc=0x70e64d28)
0x70e64d18:   e8 73 20 00 00 90 66 90 55 b8 01 00 00 00 89 e5
0x70e64d28:   a3 00 00 00 00 5d c2 08 00 90 8d b4 26 00 00 00 


Stack: [0x008c0000,0x00910000],  sp=0x0090fc78,  free space=319k
Native frames: (J=compiled Java code, j=interpreted, Vv=VM code, C=native code)
C  [SocketUDT-windows-x86-32.dll+0x24d28]
j  com.barchart.udt.SocketUDT.testCrashJVM0()V+0
j  com.barchart.udt.MainCrash.main([Ljava/lang/String;)V+24
v  ~StubRoutines::call_stub
V  [jvm.dll+0xecabc]
V  [jvm.dll+0x173d61]
V  [jvm.dll+0xecb3d]
V  [jvm.dll+0xf5705]
V  [jvm.dll+0xfd35d]
C  [javaw.exe+0x2155]
C  [javaw.exe+0x833e]
C  [kernel32.dll+0xb729]

Java frames: (J=compiled Java code, j=interpreted, Vv=VM code)
j  com.barchart.udt.SocketUDT.testCrashJVM0()V+0
j  com.barchart.udt.MainCrash.main([Ljava/lang/String;)V+24
v  ~StubRoutines::call_stub

---------------  P R O C E S S  ---------------

Java Threads: ( => current thread )
  0x02aaa000 JavaThread "Low Memory Detector" daemon [_thread_blocked, id=16220, stack(0x02d20000,0x02d70000)]
  0x02aa3c00 JavaThread "CompilerThread0" daemon [_thread_blocked, id=2232, stack(0x02cd0000,0x02d20000)]
  0x02aa2400 JavaThread "Attach Listener" daemon [_thread_blocked, id=23456, stack(0x02c80000,0x02cd0000)]
  0x02aa1000 JavaThread "Signal Dispatcher" daemon [_thread_blocked, id=15428, stack(0x02c30000,0x02c80000)]
  0x02a62000 JavaThread "Finalizer" daemon [_thread_blocked, id=7204, stack(0x02be0000,0x02c30000)]
  0x02a5d400 JavaThread "Reference Handler" daemon [_thread_blocked, id=19412, stack(0x02b90000,0x02be0000)]
=>0x003a7400 JavaThread "main" [_thread_in_native, id=11908, stack(0x008c0000,0x00910000)]

Other Threads:
  0x02a5bc00 VMThread [stack: 0x02b40000,0x02b90000] [id=30284]
  0x02ac5800 WatcherThread [stack: 0x02d70000,0x02dc0000] [id=12604]

VM state:not at safepoint (normal execution)

VM Mutex/Monitor currently owned by a thread: None

Heap
 def new generation   total 960K, used 734K [0x22990000, 0x22a90000, 0x22e70000)
  eden space 896K,  74% used [0x22990000, 0x22a37a38, 0x22a70000)
  from space 64K, 100% used [0x22a70000, 0x22a80000, 0x22a80000)
  to   space 64K,   0% used [0x22a80000, 0x22a80000, 0x22a90000)
 tenured generation   total 4096K, used 157K [0x22e70000, 0x23270000, 0x26990000)
   the space 4096K,   3% used [0x22e70000, 0x22e97500, 0x22e97600, 0x23270000)
 compacting perm gen  total 12288K, used 467K [0x26990000, 0x27590000, 0x2a990000)
   the space 12288K,   3% used [0x26990000, 0x26a04d00, 0x26a04e00, 0x27590000)
    ro space 8192K,  67% used [0x2a990000, 0x2aef8d98, 0x2aef8e00, 0x2b190000)
    rw space 12288K,  54% used [0x2b190000, 0x2b80d0f0, 0x2b80d200, 0x2bd90000)

Dynamic libraries:
0x00400000 - 0x00424000 	C:\Program Files\Java\jdk1.6.0_14\bin\javaw.exe
0x7c900000 - 0x7c9b2000 	C:\WINDOWS\system32\ntdll.dll
0x7c800000 - 0x7c8f6000 	C:\WINDOWS\system32\kernel32.dll
0x77dd0000 - 0x77e6b000 	C:\WINDOWS\system32\ADVAPI32.dll
0x77e70000 - 0x77f02000 	C:\WINDOWS\system32\RPCRT4.dll
0x77fe0000 - 0x77ff1000 	C:\WINDOWS\system32\Secur32.dll
0x7e410000 - 0x7e4a1000 	C:\WINDOWS\system32\USER32.dll
0x77f10000 - 0x77f59000 	C:\WINDOWS\system32\GDI32.dll
0x76390000 - 0x763ad000 	C:\WINDOWS\system32\IMM32.DLL
0x7c340000 - 0x7c396000 	C:\Program Files\Java\jdk1.6.0_14\jre\bin\msvcr71.dll
0x6d8b0000 - 0x6db3b000 	C:\Program Files\Java\jdk1.6.0_14\jre\bin\client\jvm.dll
0x76b40000 - 0x76b6d000 	C:\WINDOWS\system32\WINMM.dll
0x6d860000 - 0x6d86c000 	C:\Program Files\Java\jdk1.6.0_14\jre\bin\verify.dll
0x6d3e0000 - 0x6d3ff000 	C:\Program Files\Java\jdk1.6.0_14\jre\bin\java.dll
0x6d340000 - 0x6d348000 	C:\Program Files\Java\jdk1.6.0_14\jre\bin\hpi.dll
0x76bf0000 - 0x76bfb000 	C:\WINDOWS\system32\PSAPI.DLL
0x6d8a0000 - 0x6d8af000 	C:\Program Files\Java\jdk1.6.0_14\jre\bin\zip.dll
0x70e40000 - 0x71273000 	C:\user1\workspace\workspace-barchart-3.5\barchart-udt4\lib\bin\SocketUDT-windows-x86-32.dll
0x77c10000 - 0x77c68000 	C:\WINDOWS\system32\msvcrt.dll
0x71ab0000 - 0x71ac7000 	C:\WINDOWS\system32\WS2_32.DLL
0x71aa0000 - 0x71aa8000 	C:\WINDOWS\system32\WS2HELP.dll
0x6d6c0000 - 0x6d6d3000 	C:\Program Files\Java\jdk1.6.0_14\jre\bin\net.dll

VM Arguments:
jvm_args: -Dfile.encoding=UTF-8 -Xbootclasspath:C:\Program Files\Java\jdk1.6.0_14\jre\lib\resources.jar;C:\Program Files\Java\jdk1.6.0_14\jre\lib\rt.jar;C:\Program Files\Java\jdk1.6.0_14\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.6.0_14\jre\lib\jce.jar;C:\Program Files\Java\jdk1.6.0_14\jre\lib\charsets.jar 
java_command: com.barchart.udt.MainCrash
Launcher Type: SUN_STANDARD

Environment Variables:
JAVA_HOME=C:\Program Files\Java\jdk1.6.0_14
PATH=C:\MinGW\bin;C:\Program Files\Windows Resource Kits\Tools\;C:\Program Files\Microsoft Visual Studio 9.0\VC\bin;C:\Program Files\Java\jdk1.6.0_14\bin;C:\cygwin/bin;C:\Program Files\Pervasive Software\PSQL\bin\;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\Program Files\ATI Technologies\ATI Control Panel;c:\Program Files\Microsoft SQL Server\90\Tools\binn\;C:\Program Files\MySQL\MySQL Server 5.1\bin;C:\user1\apps\apache-maven-2.1.0\bin;C:\user1\apps\agntctrl.win_ia32-TPTP-4.5.2\plugins\org.eclipse.tptp.javaprofiler;C:\user1\apps\agntctrl.win_ia32-TPTP-4.5.2\bin;C:\Program Files\TortoiseSVN\bin;C:\Program Files\SlikSvn\bin\
USERNAME=user1
OS=Windows_NT
PROCESSOR_IDENTIFIER=x86 Family 15 Model 107 Stepping 2, AuthenticAMD



---------------  S Y S T E M  ---------------

OS: Windows XP Build 2600 Service Pack 3

CPU:total 2 (2 cores per cpu, 1 threads per core) family 15 model 107 stepping 2, cmov, cx8, fxsr, mmx, sse, sse2, sse3, mmxext, 3dnow, 3dnowext

Memory: 4k page, physical 2097151k(1932016k free), swap 4194303k(4194303k free)

vm_info: Java HotSpot(TM) Client VM (14.0-b16) for windows-x86 JRE (1.6.0_14-b08), built on May 21 2009 08:03:56 by "java_re" with MS VC++ 7.1

time: Thu Sep 10 09:19:29 2009
elapsed time: 0 seconds

```