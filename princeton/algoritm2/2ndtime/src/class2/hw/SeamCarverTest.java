package class2.hw;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.princeton.cs.algs4.Picture;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.awt.Color;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;

public class SeamCarverTest {
	@Test
	public void testSeamCarverNullConstructor()
	{
	    // SeamCarver constructor throws java.lang.NullPointerException when
	    // passed a null parameter
	    try
	    {
	        SeamCarver sc = new SeamCarver(null);
	        fail("Expected a NullPointerException to be thrown");
	    } catch (NullPointerException e) {
	        assertThat(e.getMessage(), is("constructor called with null parameter"));
	    }
	}
	
	@Test
	public void testWidth() {
		SeamCarver sc = new SeamCarver(new Picture("./src/hw2/10x10.png"));
		//sc.energy(x, y)
		assertEquals(10, sc.width());
		assertEquals(10, sc.height());
	}
	
	@Test
	public void testEnergy() {
		SeamCarver sc = new SeamCarver(new Picture("./src/hw2/10x10.png"));
		double delta = 0.1;
		assertEquals(1000.00, sc.energy(0, 0), delta);
		assertEquals(136.73, sc.energy(1, 1), delta);
		assertEquals(272.84, sc.energy(2, 1), delta);
		assertEquals(293.85, sc.energy(1, 4), delta);
		assertEquals(245.94, sc.energy(8, 8), delta);
		assertEquals(124.04, sc.energy(8, 7), delta);
		assertEquals(222.50, sc.energy(1, 8), delta);
		assertEquals(127.18, sc.energy(5, 3), delta);
		assertEquals(90.64,  sc.energy(7, 8), delta);
	}

	
	
	@Test
	public void findVerticalSeamTest() {
		SeamCarver sc;
		int[] res, expected;
		
		sc = new SeamCarver(new Picture("./src/hw2/1x8.png"));
		res = sc.findVerticalSeam();
		expected = new int[] {0,0,0,0,0,0,0,0};
		Assert.assertArrayEquals( expected, res );
		
		
		sc = new SeamCarver(new Picture("./src/hw2/1x1.png"));
		res = sc.findVerticalSeam();
		expected = new int[] {0};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/3x4.png"));
		res = sc.findVerticalSeam();
		expected = new int[] {0,1,1,0};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/3x7.png"));
		res = sc.findVerticalSeam();
		expected = new int[] {0,1,1,1,1,1,0};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/4x6.png"));
		res = sc.findVerticalSeam();
		expected = new int[] {1,2,1,1,2,1};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/5x6.png"));
		res = sc.findVerticalSeam();
		expected = new int[] {1 ,2 ,2,  3, 2, 1};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/6x5.png"));
		res = sc.findVerticalSeam();
		expected = new int[] {3 ,4 ,3,   2, 1};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/7x10.png"));
		res = sc.findVerticalSeam();
		expected = new int[] {2,3 ,4 ,3,4,3,3,2,   2, 1};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/7x3.png"));
		res = sc.findVerticalSeam();
		expected = new int[] {2,3 ,2};
		Assert.assertArrayEquals( expected, res );
		
		
		sc = new SeamCarver(new Picture("./src/hw2/10x10.png"));
		res = sc.findVerticalSeam();
		expected = new int[] {6 ,7 ,7, 7, 7, 7, 8, 8, 7, 6};
		Assert.assertArrayEquals( expected, res );
	}
	
	@Test
	public void testRemoveVertical6x5(){
		String path = "./src/hw2/6x5.png";
		Picture picture = new Picture(path);
		SeamCarver carver = new SeamCarver(picture);
		int[] seam = new int[]{ 3, 4, 3, 2, 1 };
		
		PrintSeams.printSeam(carver,seam, PrintSeams.VERTICAL);
		carver.removeVerticalSeam(seam);
		PrintSeams.printSeam(carver,seam, PrintSeams.VERTICAL);
		
	}
	
	@Test
	public void removeVerticalSeamTest() {
		SeamCarver sc;
		int[] seam;
		Picture p;

		
		sc = new SeamCarver(new Picture("./src/hw2/6x5.png"));
		seam = new int[] {3, 4, 3, 2, 1};
		sc.removeVerticalSeam(seam);
		p = sc.picture();
		Assert.assertEquals(true, p.get(0, 0).equals(new Color(78,209,79)));
		Assert.assertEquals(true, p.get(1, 0).equals(new Color( 63, 118, 247)));
		Assert.assertEquals(true, p.get(2, 0).equals(new Color( 92, 175,  95)));
		Assert.assertEquals(true, p.get(0, 1).equals(new Color(224, 191, 182)));
		Assert.assertEquals(true, p.get(2, 2).equals(new Color(149, 164, 168)));
		Assert.assertEquals(true, p.get(3, 4).equals(new Color( 79, 125, 246)));
		
		/*
		sc = new SeamCarver(new Picture("./src/hw2/5x6.png"));
		
		seam = new int[] {3, 4, 5, 4, 3,2};
		sc.removeVerticalSeam(seam);
		p = sc.picture();*/
		
		
		sc = new SeamCarver(new Picture("./src/hw2/10x12.png"));
		seam = new int[]  { 5, 6, 7, 8, 7, 7, 6, 7, 6, 5, 6, 5 };
		sc.removeVerticalSeam(seam);
		p = sc.picture();
		Assert.assertEquals(9,p.width());
		Assert.assertEquals(12,p.height());
		
		
	}
	
	
	
	public void removeHorizontallSeamTest () {
		SeamCarver sc;
		int[] seam;
		Picture p;
		
		
		sc = new SeamCarver(new Picture("./src/hw2/6x5.png"));
		seam = new int[]  { 1, 2, 1, 2, 1, 0 };
		sc.removeHorizontalSeam(seam);
		p = sc.picture();
		Assert.assertEquals(6,p.width());
		Assert.assertEquals(4,p.height());
		
		/*
		sc = new SeamCarver(new Picture("./src/hw2/10x12.png"));
		seam = new int[]  { 1, 2, 1, 2, 1, 0 };
		sc.removeHorizontalSeam(seam);
		p = sc.picture();
		Assert.assertEquals(10,p.width());
		Assert.assertEquals(11,p.height());
		
		
		sc = new SeamCarver(new Picture("./src/hw2/10x10.png"));
		seam = new int[]  {  5, 7, 6, 6, 6, 5, 5, 6, 5, 4 };
		sc.removeHorizontalSeam(seam);*/
	}
	
	
	
	@Test
	public void findHorizontalSeamTest() {
		SeamCarver sc;
		int[] res, expected;
		
		sc = new SeamCarver(new Picture("./src/hw2/1x1.png"));
		res = sc.findHorizontalSeam();
		expected = new int[] {0};
		Assert.assertArrayEquals( expected, res );
		
		
		sc = new SeamCarver(new Picture("./src/hw2/1x8.png"));
		res = sc.findHorizontalSeam();
		expected = new int[] {0};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/3x4.png"));
		res = sc.findHorizontalSeam();
		expected = new int[] {1,2,1};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/3x7.png"));
		res = sc.findHorizontalSeam();
		expected = new int[] {1,2,1};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/4x6.png"));
		res = sc.findHorizontalSeam();
		expected = new int[] {1,2,1,0};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/5x6.png"));
		res = sc.findHorizontalSeam();
		expected = new int[] {2,3,2,3,2};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/6x5.png"));
		res = sc.findHorizontalSeam();
		expected = new int[] {1,2,1,2,1,0};
		Assert.assertArrayEquals( expected, res );
		
		
		sc = new SeamCarver(new Picture("./src/hw2/7x10.png"));
		res = sc.findHorizontalSeam();
		expected = new int[] {6,7,7,7,8,8,7};
		Assert.assertArrayEquals( expected, res );
		
		
		sc = new SeamCarver(new Picture("./src/hw2/7x3.png"));
		res = sc.findHorizontalSeam();
		expected = new int[] {0,1,1,1,1,1,0};
		Assert.assertArrayEquals( expected, res );
		
		sc = new SeamCarver(new Picture("./src/hw2/10x10.png"));
		res = sc.findHorizontalSeam();
		expected = new int[] {0,1,2,3,3,3,3,2,1,0};
		Assert.assertArrayEquals( expected, res );
	}
	
	@Test
	public void test12b() {
		SeamCarver sc;
		int[] res1,res2, expected;
		sc = new SeamCarver(new Picture("./src/hw2/10x12.png"));
		//res1 = sc.findHorizontalSeam();
		sc.removeHorizontalSeam(sc.findHorizontalSeam());
		sc.removeVerticalSeam(sc.findVerticalSeam());
		sc.removeHorizontalSeam(sc.findHorizontalSeam());
		sc.removeVerticalSeam(sc.findVerticalSeam());
		Picture pc = sc.picture();
		Assert.assertEquals(8, pc.width());
		Assert.assertEquals(10, pc.height());
	}
	
	@Test
	public void testSeamRemoval() {
		SeamCarver sc1, sc2;
		
		Picture p1, p2;
		
		p1 = new Picture("./src/hw2/10x12.png");
		sc1 = new SeamCarver(p1);
		
		//p2 = new Picture(p1);
		p2 = MyPicture.removeVerticalLine(p1, 7);
		p2 = MyPicture.removeHLine(new Picture(p2), 6);
		
		
		sc2 = new SeamCarver(p2);
		
		
		
		int[] seam = {7,7,7,7,7,7,7,7,7,7,7,7};
		
		sc1.removeVerticalSeam(seam);
		seam = new int []{6,6,6,6,6,6,6,6,6};
		
		sc1.removeHorizontalSeam(seam);
		
		p1 = sc1.picture();
		for (int col = 0; col< p2.width(); col++)
			for (int row = 0; row < p2.height(); row++) {
				Assert.assertEquals(true, p1.get(col, row).equals(p2.get(col, row)));
				Assert.assertEquals(sc1.energy(col, row), sc2.energy(col, row), 0.01);
			}
	}
}
