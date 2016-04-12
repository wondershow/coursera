#!/bin/bash

####Since the autograder only assumes the java files in the default package.
####this bash file removes all the 1st line of java files (package declaration)
####then zip all the related files to ~/tmp/

rm -rf ~/tmp/princeton.hw2
mkdir ~/tmp/princeton.hw2
cd ~/tmp/princeton.hw2

rm -rf * ;

cp  ~/Documents/workspace/Princeton/src/class2/hw/SeamCarver.java ./tmp.java;
tail -n +2 tmp.java > SeamCarver.java;



zip hw2.zip SeamCarver.java