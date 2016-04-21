#!/bin/bash

####Since the autograder only assumes the java files in the default package.
####this bash file removes all the 1st line of java files (package declaration)
####then zip all the related files to ~/tmp/

rm -rf ~/tmp/princeton.hw4
mkdir ~/tmp/princeton.hw4
cd ~/tmp/princeton.hw4

rm -rf * ;

cp  ~/coursera/princeton/algoritm2/2ndtime/src/class4/hw/BoggleSolver.java ./tmp.java;
tail -n +2 tmp.java > BoggleSolver.java;

cp  ~/coursera/princeton/algoritm2/2ndtime/src/class4/hw/TreeSetBoggleSolver.java ./tmp.java;
tail -n +2 tmp.java > TreeSetBoggleSolver.java;



cp  ~/coursera/princeton/algoritm2/2ndtime/src/class4/hw/TSTBoggleSolver.java ./tmp.java;
tail -n +2 tmp.java > TSTBoggleSolver.java;

cp  ~/coursera/princeton/algoritm2/2ndtime/src/class4/hw/TST.java ./tmp.java;
tail -n +2 tmp.java > TST.java;

cp  ~/coursera/princeton/algoritm2/2ndtime/src/class4/hw/TrieSET.java ./tmp.java;
tail -n +2 tmp.java > TrieSET.java;

cp  ~/coursera/princeton/algoritm2/2ndtime/src/class4/hw/TrieBoggleSolver.java ./tmp.java;
tail -n +2 tmp.java > TrieBoggleSolver.java;


zip hw4.zip TreeSetBoggleSolver.java BoggleSolver.java TSTBoggleSolver.java TST.java TrieSET.java TrieBoggleSolver.java