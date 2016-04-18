package class3.practice;

import java.util.Hashtable;
import java.util.Iterator;

import edu.princeton.cs.algs4.Queue;

/**
 * This is for excercise 5.1.1
 ***/
public class ch5p1p1
{
	public static void sort(String[] a) {
		if (a==null) throw new java.lang.NullPointerException();
		
		int N = a.length;
		String[] aux = new String[N];
		
		Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
		int numKeys =0;
		for (int i = 0; i < a.length; i++)
		{
			if (ht.get(a[i]) == null) 
			{	
				numKeys++;
				ht.put(a[i], 1); 
			}
			else 
			{
				Integer count = ht.get(a[i]);
				ht.put(a[i], count+1);
			}
		}
		
		int[] count = new int[numKeys+1];
		int i = 0;
		
		String[] keyArr = (String[])ht.keySet().toArray();
		
		
		
		
		
		
		Iterator<String> keys = ht.keySet().iterator();
		Hashtable<String, Integer> mapping = new Hashtable<String, Integer>();
		
		
		
		
		while (keys.hasNext()) 
		{
			String key = keys.next();
			mapping.put(key, i);
			count[i+1] = ht.get(key);
			i++;
		}
		
		for(int j = 0; j< count.length-1; j++)
			count[j+1] += count[j];
		
		for (int j = 0; j < N; j++ )
			aux [count[mapping.get(a[j])]++] = a[j];
			
		for (int j = 0; j < N; j++)
			a[j] = aux[j];
		
	}
	
	public static void main(String[] args) {
		
		
		
		Queue<Integer> [] ques = (Queue<Integer> [])new Object[10];
		for (int i = 0; i < ques.length; i++)
			ques[i] = new Queue<Integer>();
		
		/*
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < 15; i++)
			list.add((int)  (Math. random() * 50 + 1));
		Iterator<Integer> it = list.iterator();
		
		while (it.hasNext())
			System.out.print(it.next() + " ");
		
		System.out.println();
		
		it = list.iterator();
		
		while (it.hasNext())
			System.out.print(it.next() + " ");*/
		String path = "./src/class3/practice/input/words.txt";
		String[] a = LSD.readAllStrings(path);
		//LSD.sort(allWords, 3);
		ch5p1p1.sort(a);
		
		for (int i =0; i < a.length;i++)
			System.out.println(a[i]);
	}
}



