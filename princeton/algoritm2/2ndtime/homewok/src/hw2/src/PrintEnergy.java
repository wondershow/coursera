/******************************************************************************
 *  Compilation:  javac PrintEnergy.java
 *  Execution:    java PrintEnergy input.png
 *  Dependencies: SeamCarver.java
 *                
 *
 *  Read image from file specified as command line argument. Print energy
 *  of each pixel as calculated by SeamCarver object. 
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class PrintEnergy {

    public static void main(String[] args) {
        Picture picture = new Picture("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/homewok/src/hw2/src/1x8.png");
        StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture.width(), picture.height());
        
        SeamCarver sc = new SeamCarver(picture);
        
        StdOut.printf("Printing energy calculated for each pixel.\n");        
        
        
        //StdOut.printf("\n Alo a %9.0f \n", sc.energy(1, 1));
        //StdOut.println();
        /*
        for (int j = 0; j < sc.height(); j++) {
            for (int i = 0; i < sc.width(); i++)
                StdOut.printf("%9.0f ", sc.energy(i, j));
            StdOut.println();
        } */
        
        //int[] seam = sc.findVerticalSeam();
        //int[] seam = { 5 ,6, 5, 5, 6, 7, 8, 7, 8, 8, 9, 8  };
        //sc.removeVerticalSeam(seam);
        //sc.picture();
        int[] seam = sc.findHorizontalSeam();
        System.out.println("length = " + seam.length);
        sc.removeHorizontalSeam(seam);
        //System.out.println("done");
    }

}
