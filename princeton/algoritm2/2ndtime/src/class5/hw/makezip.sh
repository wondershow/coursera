#!/bin/bash

####Since the autograder only assumes the java files in the default package.
####this bash file removes all the 1st line of java files (package declaration)
####then zip all the related files to ~/tmp/

rm -rf ~/tmp/princeton.hw5
mkdir ~/tmp/princeton.hw5
cd ~/tmp/princeton.hw5

rm -rf * ;

cp  ~/coursera/princeton/algoritm2/2ndtime/src/class5/hw/BurrowsWheeler.java ./tmp.java;
tail -n +2 tmp.java > BurrowsWheeler.java;

cp  ~/coursera/princeton/algoritm2/2ndtime/src/class5/hw/CircularSuffixArray.java ./tmp.java;
tail -n +2 tmp.java > CircularSuffixArray.java;

cp  ~/coursera/princeton/algoritm2/2ndtime/src/class5/hw/MoveToFront.java ./tmp.java;
tail -n +2 tmp.java > MoveToFront.java;



zip hw5.zip MoveToFront.java CircularSuffixArray.java BurrowsWheeler.java 