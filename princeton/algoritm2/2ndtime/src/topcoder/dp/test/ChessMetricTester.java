package topcoder.dp.test;

import static org.junit.Assert.*;

import org.junit.Test;

import topcoder.dp.ChessMetric;

public class ChessMetricTester
{

	@Test
	public void test()
	{
		long res = ChessMetric.howMany(3, new int[] {0,0}, new int[] {1,0}, 1);
		assertEquals(1,res);
	}
	
	@Test
	public void test1()
	{
		long res = ChessMetric.howMany(3, new int[] {0,0}, new int[] {1,2}, 1);
		assertEquals(1,res);
	}
	
	@Test
	public void test2()
	{
		long res = ChessMetric.howMany(3, new int[] {0,0}, new int[] {2,2}, 1);
		assertEquals(0,res);
	}
	
	@Test
	public void test3()
	{
		long res = ChessMetric.howMany(3, new int[] {0,0}, new int[] {0,0}, 2);
		assertEquals(5,res);
	}
	
	@Test
	public void test4()
	{
		long res = ChessMetric.howMany(100, new int[] {0,0}, new int[] {0,99}, 50);
		assertEquals(243097320072600l,res);
	}
}
