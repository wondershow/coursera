package class3.practice;

import java.util.Iterator;

import edu.princeton.cs.algs4.Queue;

public class QueueMSD
{
	private static Queue<String>[] RQueue;
	private static final int R = 256;
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String path = "./src/class3/practice/input/words.txt";
		String[] words = LSD.readAllStrings(path);
		sort(words);
		for (int i = 0; i < words.length; i++)
			System.out.println(words);
	}
	
	
	private static int charAt(String a, int d) 
	{
		if (d < a.length()) return a.charAt(d);
		else return -1;
	} 
	
	public static void sort(String[] a) 
	{
		if (a==null) throw new java.lang.NullPointerException();
		int N = a.length;
		
		
		RQueue =  (Queue<String>[]) new Object[R+1];
		for (int i = 0; i < RQueue.length; i++)
			RQueue[i] = new Queue<String>();
	}
	
	private static void clearQbins() 
	{
		for (int i = 0; i < RQueue.length; i++)
			while(!RQueue[i].isEmpty()) RQueue[i].dequeue();
	}
	
	private static void sort(String[] a, int lo, int hi, int d) 
	{
		if (lo >= hi) return;
		clearQbins();
		for (int i = lo; i < hi; i++) 
			RQueue[charAt(a[i], d)].enqueue(a[i]);
		
		
		Queue<String> tmp = new Queue<String>();
		for (int i = 0; i < RQueue.length; i++)
		{
			Iterator<String> it = RQueue[i].iterator();
			while (it.hasNext()) tmp.enqueue(it.next());
		}
		
		Iterator<String> it = tmp.iterator();
		int j = lo;
		while (it.hasNext()) a[j++] = it.next();
		
		//skip empty string
		for (int i = 1; i < RQueue.length -1 ; i++)
			sort(a, lo + RQueue[i].size(),  lo + RQueue[i+1].size()-1, d+1 );
	}
}
