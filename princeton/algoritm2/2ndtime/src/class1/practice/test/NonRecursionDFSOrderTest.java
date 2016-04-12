package class1.practice.test;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import class1.practice.NonRecursionDFSOrder;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class NonRecursionDFSOrderTest {
	@Test
	public void orderTest() {
		Digraph g = new Digraph(new In("./src/class1/practice/files/tinyDAG.txt"));
		DepthFirstOrder answer = new DepthFirstOrder(g);
		NonRecursionDFSOrder my = new NonRecursionDFSOrder(g);
		
		int[] arr2 = iterator2array(my.preOrder().iterator(), g.V());
		int[] arr1 = iterator2array(answer.pre().iterator(), g.V());
		Assert.assertArrayEquals(arr1,arr2);
		
		
		arr2 = iterator2array(my.postOrder().iterator(), g.V());
		arr1 = iterator2array(answer.post().iterator(), g.V());
		Assert.assertArrayEquals(arr1,arr2);
		
		arr2 = iterator2array(my.reversPostOrder().iterator(), g.V());
		arr1 = iterator2array(answer.reversePost().iterator(), g.V());
		Assert.assertArrayEquals(arr1,arr2);
	}
	
	private int[] iterator2array (Iterator<Integer> it, int size) {
		int[] res = new int[size];
		int i = 0;
		while (it.hasNext()) { res[i++] = it.next(); }
		for (int j = 0; j < size; j++)
			System.out.print(res[j] + " ");
		System.out.println();
		return res;
	}
}
