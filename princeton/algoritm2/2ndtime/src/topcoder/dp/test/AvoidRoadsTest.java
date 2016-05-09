package topcoder.dp.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import topcoder.dp.AvoidRoads;

public class AvoidRoadsTest
{

	@Test
	public void test()
	{
		long res = AvoidRoads.numWays(6, 6, new String[] {"0 0 0 1","6 6 5 6"});
		assertEquals(252,res);
	}
	
	@Test
	public void test1()
	{
		long res = AvoidRoads.numWays(1, 1, new String[] {});
		assertEquals(2, res);
	}
	
	
	@Test
	public void test2()
	{
		long res = AvoidRoads.numWays(35, 31, new String[] {});
		assertEquals(6406484391866534976l, res);
	}
	
	@Test
	public void test3()
	{
		long res = AvoidRoads.numWays(2, 2, new String[] {"0 0 1 0", "1 2 2 2", "1 1 2 1"});
		assertEquals(res, 0);
	}
}
