package class2.hw;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.princeton.cs.algs4.Picture;

public class SeamCarverExceptionTester {
	
	@Test(expected=IllegalArgumentException.class)
	public void removeHorizontalSeamTest1() {
		String path = "./src/hw2/10x10.png";
		Picture picture = new Picture(path);
		SeamCarver sc = new SeamCarver(picture);
		int[] seam = new int[]{ 5, 7, 6, 6, 6, 5, 5, 6, 5, 4 };
		sc.removeHorizontalSeam(seam);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void removeHorizontalSeamTest2() {
		String path = "./src/hw2/3x7.png";
		Picture picture = new Picture(path);
		SeamCarver sc = new SeamCarver(picture);
		int[] seam = new int[]{ -1, 0, 0  };
		sc.removeHorizontalSeam(seam);
	}
	
	
	
	
	@Test(expected=IllegalArgumentException.class)
	public void removeHorizontalSeamTest3() {
		String path = "./src/hw2/7x3.png";
		Picture picture = new Picture(path);
		SeamCarver sc = new SeamCarver(picture);
		int[] seam = new int[]{0, 0, -1, 0, 1, 0, -1 };
		sc.removeHorizontalSeam(seam);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void removeHorizontalSeamTest4() {
		String path = "./src/hw2/10x12.png";
		Picture picture = new Picture(path);
		SeamCarver sc = new SeamCarver(picture);
		int[] seam = new int[]{10, 11, 10, 9, 10, 9, 10, 11, 12, 11 };
		sc.removeHorizontalSeam(seam);
	}
}
