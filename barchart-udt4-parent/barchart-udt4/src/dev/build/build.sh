#!/bin/bash

# c
# sudo apt-get install gcc-multilib

# c++
# sudo apt-get install g++-multilib
	
# sudo apt-get install mingw32	

g++ -m32 -o hello32 hello.cpp 
g++ -m64 -o hello64 hello.cpp

i586-mingw32msvc-g++ -m32 -o hello32.exe hello.cpp
# i586-mingw32msvc-g++ -m64 -o hello64.exe hello.cpp

hello32
hello64

hello32.exe
#hello64.exe
