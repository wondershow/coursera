package mit.unit2;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Random;

public class MaxPQTester
{

	@Test
	public void test()
	{
		
		Integer[] arr = new Integer[100];
		
		
		for (int i = 0; i < 100; i++)
			arr[i] = new Integer(i);
		
		
		//reshuffle the integer arragy
		Random random = new Random();
		for (int i = 0; i < 100; i++)
		{
			Integer tmp;
			int randomNext = random.nextInt(100 - i) + i;
			tmp = arr[i];
			arr[i] = arr[randomNext];
			arr[randomNext] = tmp;
		}
		
		MaxPQ<Integer> pq = new MaxPQ<Integer>(arr);
		
		while (!pq.isEmpty())
			System.out.println(pq.delMax() + " size = " + pq.size());
		
		System.out.println(pq.isEmpty());
		fail("Not yet implemented");
	}

}
