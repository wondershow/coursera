package topcoder.dp.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import topcoder.dp.FLowerGarden;

public class FlowerGardenTest
{
	@Test
	public void test()
	{
		int[] height = {5,4,3,2,1};
		int[] bloom = {1,1,1,1,1};
		int[] wilt = {365,365,365,365,365};
		
		int[] res = FLowerGarden.getOrdering(height, bloom, wilt);
		
		Assert.assertArrayEquals(new int[] { 1,  2,  3,  4,  5  }, res);
	}
	
	@Test
	public void test1()
	{
		int[] height = {5,4,3,2,1};
		int[] bloom = {1,5,10,15,20};
		int[] wilt = {4,9,14,19,24}
;
		
		int[] res = FLowerGarden.getOrdering(height, bloom, wilt);
		
		Assert.assertArrayEquals(new int[] {5, 4, 3, 2, 1}, res);
	}
	
	
	
	@Test
	public void test2()
	{
		int[] height = {5,4,3,2,1};
		int[] bloom = {1,5,10,15,20};
		int[] wilt = {5,10,15,20,25};
		
		int[] res = FLowerGarden.getOrdering(height, bloom, wilt);
		
		Assert.assertArrayEquals(new int[] {1,  2,  3,  4,  5}, res);
	}
	
	@Test
	public void test3()
	{
		int[] height = {5,4,3,2,1};
		int[] bloom = {1,5,10,15,20};
		int[] wilt = {5,10,14,20,25};
		
		int[] res = FLowerGarden.getOrdering(height, bloom, wilt);
		
		Assert.assertArrayEquals(new int[] {3,  4,  5,  1,  2 }, res);
	}
	
	@Test
	public void test4()
	{
		int[] height = {1,2,3,4,5,6};
		int[] bloom = {1,3,1,3,1,3};
		int[] wilt = {2,4,2,4,2,4};
		
		int[] res = FLowerGarden.getOrdering(height, bloom, wilt);
		
		Assert.assertArrayEquals(new int[] {2,  4,  6,  1,  3,  5}, res);
	}
	
	@Test
	public void test5()
	{
		int[] height = {3,2,5,4};
		int[] bloom = {1,2,11,10};
		int[] wilt = {4,3,12,13};
		
		int[] res = FLowerGarden.getOrdering(height, bloom, wilt);
		
		Assert.assertArrayEquals(new int[] {4,  5,  2,  3 }, res);
	}
	
	
}
