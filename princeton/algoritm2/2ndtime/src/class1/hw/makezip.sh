#!/bin/bash

####Since the autograder only assumes the java files in the default package.
####this bash file removes all the 1st line of java files (package declaration)
####then zip all the related files to ~/tmp/

rm -rf ~/tmp/princeton.hw1
mkdir ~/tmp/princeton.hw1
cd ~/tmp/princeton.hw1

rm -rf * ;

cp  ~/Documents/workspace/Princeton/src/class1/hw/WordNet.java ./tmp.java; 
tail -n +2 tmp.java > WordNet.java;

cp  ~/Documents/workspace/Princeton/src/class1/hw/SAP.java ./tmp.java; 
tail -n +2 tmp.java > SAP.java;

cp  ~/Documents/workspace/Princeton/src/class1/hw/Outcast.java ./tmp.java; 
tail -n +2 tmp.java > Outcast.java;


zip hw1.zip WordNet.java SAP.java Outcast.java