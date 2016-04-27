package topcoder.dp.test;

import static org.junit.Assert.*;
import org.junit.Test;
import topcoder.dp.ZigZag;


public class ZigZagTest {

	@Test
	public void test1() {
		int[] input = { 1, 7, 4, 9, 2, 5 };
		int ans = ZigZag.longestZigZag(input);
		assertEquals(6, ans);
	}
	
	@Test
	public void test2() {
		int[] input = { 1, 17, 5, 10, 13, 15, 10, 5, 16, 8};
		int ans = ZigZag.longestZigZag(input);
		assertEquals(7, ans);
	}
	
	@Test
	public void test3() {
		int[] input = { 44 };
		int ans = ZigZag.longestZigZag(input);
		assertEquals(1, ans);
	}
	
	@Test
	public void test4() {
		int[] input = { 1, 2, 3, 4, 5, 6, 7, 8, 9  };
		int ans = ZigZag.longestZigZag(input);
		assertEquals(2, ans);
	}
	
	@Test
	public void test5() {
		int[] input = { 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5, 5, 5, 1000, 32, 32  };
		int ans = ZigZag.longestZigZag(input);
		assertEquals(8, ans);
	}
	
	@Test
	public void test6() {
		int[] input = { 374, 40, 854, 203, 203, 156, 362, 279, 812, 955, 
				600, 947, 978, 46, 100, 953, 670, 862, 568, 188, 
				67, 669, 810, 704, 52, 861, 49, 640, 370, 908, 
				477, 245, 413, 109, 659, 401, 483, 308, 609, 120, 
				249, 22, 176, 279, 23, 22, 617, 462, 459, 244   };
		int ans = ZigZag.longestZigZag(input);
		assertEquals(36, ans);
	}
}
