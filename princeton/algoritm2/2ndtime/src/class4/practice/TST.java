package class4.practice;

import edu.princeton.cs.algs4.Queue;

public class TST<Value>
{
	private Node root = new Node();
	
	private class Node 
	{
		Value val;
		char c;
		Node left,mid,right;
	}
	
	public static void main(String[] args)
	{

	}

	public void put(String key, Value val) 
	{
		put(root, key, val, 0);
	}
	
	private Node put(Node x, String key, Value val, int d) 
	{
		if (x == null) 
		{ x = new Node(); x.c = key.charAt(d);}
		
		int c = key.charAt(d);
		if (c == x.c) 
		{  
			if (d == key.length() - 1) x.val = val;
			else x.mid = put(x.mid, key, val, d+1);
		}
		else if (c < x.c) x.left = put(x.left, key, val, d);
		else x.right = put(x.right, key, val, d);
		return x;
	}
	
	public Value get(String key)
	{
		Node x = get(root, key, 0);
		if (x == null) return null;
		return x.val;
	}
	
	private Node get(Node x, String key, int d)
	{
		if (x == null) return null;
		int c = key.charAt(d);
		if (c == x.c) 
		{
			if (d == key.length() - 1) return x;
			return get(x.mid, key, d+1);
		} else if (c < x.c) return get(x.left, key, d);
		else return get(x.right, key, d);
	}
	
	public void delete(String key)
	{
		root = delete(root, key, 0);
	}
	
	private Node delete(Node x, String key, int d)
	{
		if (x == null) return null;
		int c = key.charAt(d);
		
		if (c < x.c) return delete(x.left, key, d);
		else if ( c > x.c) return delete(x.right, key, d);
		
		if (d < key.length()) return delete(x.mid, key, d+1);
		x.val = null;
		if (x.left == null && x.right == null) x = null;
		return x;
	}
	
	public boolean contains(String key)
	{
		return get(key) != null;
	}
	
	public boolean isEmpty()
	{
		return size() == 0;
	}
	
	public int size()
	{
		return size(root);
	}
	
	private int size(Node x) 
	{
		if (x == null) return 0;
		int res = 0;
		if (x.val != null) res++;
		res += size(x.mid) + size(x.left) + size(x.right);
		return res;
	}
	
	public Iterable<String> keys() 
	{
		Queue<String> res = new Queue<String>();
		collect(root, "", res);
		return res;
	}
	
	private void collect(Node x, String pre, Queue<String> res) 
	{
		if (x == null) return;
		collect(x.left, pre, res);
		if (x.val != null) res.enqueue(pre + x.c);
		collect(x.mid, pre + x.c, res);
		collect(x.right, pre, res);
	}
}
