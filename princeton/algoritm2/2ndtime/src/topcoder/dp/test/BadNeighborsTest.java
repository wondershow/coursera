package topcoder.dp.test;

import static org.junit.Assert.*;

import org.junit.Test;

import topcoder.dp.BadNeighbors;

public class BadNeighborsTest
{
	/*
	@Test
	public void test1()
	{
		int[] input = { 10, 3, 2, 5, 7, 8 };
		int res = BadNeighbors.maxDonations(input);
		assertEquals(res, 19);
	}

	@Test
	public void test2()
	{
		int[] input = { 11, 15 };
		int res = BadNeighbors.maxDonations(input);
		assertEquals(res, 15);
	}
	
	@Test
	public void test3()
	{
		int[] input = { 7, 7, 7, 7, 7, 7, 7 };
		int res = BadNeighbors.maxDonations(input);
		assertEquals(res, 21);
	}
	
	
	
	@Test
	public void test5()
	{
		int[] input = { 94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61,  
				  6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397,
				  52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72  };
		int res = BadNeighbors.maxDonations(input);
		assertEquals(res, 2926);
	} 
	
	@Test
	public void test6()
	{
		int[] input = {1,3,1,1,5,1};
		int res = BadNeighbors.maxDonations(input);
		assertEquals(res, 8);
	}*/
	
	
	@Test
	public void test4()
	{
		int[] input = { 1, 2, 3, 4, 5, 1, 2, 3, 4, 5  };
		int res = BadNeighbors.maxDonations(input);
		assertEquals(res, 16);
	}
}
