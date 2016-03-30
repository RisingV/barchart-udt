# Native Call Crashes JVM #

follow these steps to find which of your native JNI calls crashes JVM:
<p>
1) barchart-udt build comes with <a href='SampleLinkerMap.md'>liker map</a> stored inside the jar; find the map that matches your current platform.<br>
<p>
2) inside <a href='SampleJvmCrashLog.md'>jvm crash log</a> look for <b>Problematic frame</b> call offset:<br>
<pre><code>...<br>
# Problematic frame:<br>
# C  [SocketUDT-windows-x86-32.dll+0x24d28]<br>
...<br>
</code></pre>
note call offset = <b>0x24d28</b>
<p>
3) inside <a href='SampleJvmCrashLog.md'>jvm crash log</a> look for <b>Dynamic libraries</b> base address:<br>
<pre><code>Dynamic libraries:<br>
...<br>
      0x76bf0000 - 0x76bfb000 	C:\WINDOWS\system32\PSAPI.DLL<br>
      0x6d8a0000 - 0x6d8af000 	C:\Program Files\Java\jdk1.6.0_14\jre\bin\zip.dll<br>
   -&gt; 0x70e40000 - 0x71273000 	C:\user1\workspace\workspace-barchart-3.5\barchart-udt4\lib\bin\SocketUDT-windows-x86-32.dll<br>
      0x77c10000 - 0x77c68000 	C:\WINDOWS\system32\msvcrt.dll<br>
      0x71ab0000 - 0x71ac7000 	C:\WINDOWS\system32\WS2_32.DLL<br>
...<br>
</code></pre>
note library base = <b>0x70e40000</b>
<p>
4) calculate call address:<br>
<pre><code>entry point =  base address + call offset<br>
</code></pre>
<pre><code>0x70e64d28 = 0x70e40000 + 0x24d28<br>
</code></pre>
note call address = <b>0x70e64d28</b>
<p>
5) look through <a href='SampleLinkerMap.md'>liker map</a> and find <b>address range</b> around your calculated <b>call address</b>:<br>
<pre><code>                0x70e62df0                Java_com_barchart_udt_SocketUDT_bind0@12<br>
                0x70e62f90                Java_com_barchart_udt_SocketUDT_close0@8<br>
   start   -&gt;   0x70e64d20                Java_com_barchart_udt_SocketUDT_testCrashJVM0@8<br>
   finish  -&gt;   0x70e656f0                Java_com_barchart_udt_SocketUDT_selectEx1@24<br>
                0x70e64b70                Java_com_barchart_udt_SocketUDT_testGetSetArray0@16<br>
</code></pre>
note:<br>
<pre><code><br>
// location of a crash:<br>
start      &lt;= crash      &lt;= finish<br>
0x70e64d20 &lt;= 0x70e64d28 &lt;= 0x70e656f0<br>
<br>
// hence crashed in the following call:<br>
0x70e64d20                Java_com_barchart_udt_SocketUDT_testCrashJVM0@8<br>
<br>
</code></pre>