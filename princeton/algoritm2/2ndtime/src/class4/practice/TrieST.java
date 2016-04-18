package class4.practice;

import edu.princeton.cs.algs4.Queue;

public class TrieST<Value>
{
	private static final int R = 256;
	private Node root = new Node();
	
	private static class Node 
	{
		private Object val;
		private Node[] next = new Node[256];
	}
	
	public static void main(String[] args)
	{
			
	}
	
	public void put(String key, Value value) 
	{
		root = put(root, key, value, 0);
	}
	
	private Node put(Node x, String key, Value val, int d) 
	{
		if (x == null) x = new Node();
		if (key.length() == d)  x.val = val;
		else 
		{
			int c = key.charAt(d);
			x.next[c] = put(x.next[c], key, val, d+1);
		}
		return x;
	}
	
	public Value get(String key) 
	{
		Node x = get(root, key, 0);
		if (x == null) return null;
		return (Value) x.val;
	}
	
	private Node get(Node x, String key, int d) 
	{
		if (x == null) return null;
		if (d == key.length()) return x;
		int c = key.charAt(d);
		return get(x.next[c], key, d+1);
	}
	
	public void delete(String key) 
	{
		delete(root, key, 0);
	}
	
	private Node delete(Node x, String key, int d)
	{
		if (x == null) return null;
		if (d == key.length())
			x.val = null;
		else
		{
			char c = key.charAt(d);
			x.next[c] = delete(x.next[c], key, d+1);
		}
		
		if (x.val != null) return x;
		for (char c = 0 ; c < R; c++)
			if (x.next[c] != null) return x;
		return null;
	}
	
	public boolean contains(String key)
	{
		Node x = get(root, key, 0);
		if (x == null || x.val == null) return false;
		return true;
	}
	
	public boolean isEmpty()
	{
		return size(root) == 0;
	}
	
	public String longestPrefixOf(String s) 
	{
		int len = search(root, s, 0, 0);
		return s.substring(0,len);
	}
	
	private int search(Node x, String s, int d, int len) 
	{
		if (x == null) return len;
		if (x.val != null) len = d;
		if (d == s.length()) return len;
		int c = s.charAt(d);
		return search(x.next[c],s, d+1, len);
	}
	
	public Iterable<String> keysWithPrefix(String pfx)
	{
		Queue<String> res = new Queue<String>();
		collect(get(root, pfx, 0), pfx, res);
		return res;
	}
	
	public Iterable<String> keysThatMatch(String s)
	{
		Queue<String> res = new Queue<String>();
		collect(root, "", s, res);
		return res;
	}
	
	private void collect(Node x, String pre, String patn, Queue<String> q) 
	{
		int d = pre.length();
		if (x == null) return;
		if (d == patn.length() && x.val != null) q.enqueue(pre);
		
		char next = patn.charAt(d);
		for (char c = 0; c < R; c++)
			if (next == '.' || next == c)
				collect(x, pre + c, patn, q);
	}
	
	private void collect(Node x, String prefix, Queue<String> que) 
	{
		if (x == null) return;
		if (x.val != null) que.enqueue(prefix);
		for (int c = 0; c < R; c++)
			collect(x.next[c], prefix + c, que);
	} 
	
	public int size() 
	{
		return size(root);
	}
	
	private int size(Node x) 
	{
		if (x == null) return 0;
		int mycout = 0;
		if (x.val != null) mycout++;
		for (int i = 0; i < R; i++)
			mycout += size(x.next[i]);
		return mycout;
	} 
	
	public Iterable<String> keys()
	{
		return keysWithPrefix("");
	}
}


