package ch5;

import java.util.Comparator;
import java.util.PriorityQueue;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class Huffman
{
	private static final int R = 256;
	
	
	private static class Node implements  Comparable<Node>
	{
		private char ch;
		private int freq;
		private final Node left, right;
		
		Node(char c, int f, Node l, Node r)
		{
			ch = c;
			freq = f;
			left = l;
			right = r;
		}
		
		public boolean isLeaf()
		{
			return left == null && right == null;
		}
		
		public int compareTo(Node that) 
		{
			return freq - that.freq;
		}
	}
	
	public static void expand()
	{
		Node root = readTrie();
		int N = BinaryStdIn.readInt();
		for (int i = 0; i < N; i++)
		{
			Node x = root;
			while (!x.isLeaf())
			{
				if (BinaryStdIn.readBoolean())
					x = x.right;
				else x = x.left;
			}
			BinaryStdOut.write(x.ch, 8);
		}
		BinaryStdOut.close();
	}
	
	private static String[] buildCode(Node root)
	{
		String[] st = new String[R];
		buildCode(st, root, "");
		return st;
	}
	
	private static void buildCode(String[] st, Node x, String s)
	{
		if (x.isLeaf()) {st[x.ch] = s; return;}
		buildCode(st, x.left, s+"0");
		buildCode(st, x.right, s+"1");
	}
	
	
	private static class NodeComparator implements Comparator<Node>
	{
		public int compare(Node n1, Node n2)
		{
			return n1.freq - n2.freq;
		}
	}
	
	private static Node buildTrie(int[] freq) 
	{
		PriorityQueue<Node> pq = new PriorityQueue<Node>(10, new NodeComparator());
		
		for (char i = 0; i < freq.length; i++)
			if (freq[i] > 0) pq.add(new Node(i, freq[i], null,null));
		
		while (pq.size() >=2)
		{
			Node x = pq.remove();
			Node y = pq.remove();
			Node parent = new Node('\0', x.freq + y.freq, x, y);
			pq.add(parent);
		}
		Node root = pq.remove();
		return root;
	}
	
	private static void writeTrie(Node x)
	{
		if (x.isLeaf())
		{
			BinaryStdOut.write(true);
			BinaryStdOut.write(x.ch, 8);
			return;
		}
		BinaryStdOut.write(false);
		writeTrie(x.left);
		writeTrie(x.right);
	}
	
	private static Node readTrie() 
	{
		if (BinaryStdIn.readBoolean())
		{
			char ch = BinaryStdIn.readChar();
			
			return new Node(ch, 1, null,null);
		}
		
		return new Node('\0', 1, readTrie(), readTrie());
	}
	
	public static void compress()
	{
		String s = BinaryStdIn.readString();
		char[] input = s.toCharArray();
		
		int[] freq = new int[R];
		for (int i = 0; i < input.length; i++)
			freq[input[i]]++;
		
		Node root = buildTrie(freq);
		
		String[] st = new String[R];
		buildCode(st, root, "");
		
		writeTrie(root);
		
		BinaryStdOut.write(input.length);
		
		for (int i = 0; i < input.length; i++)
		{
			String code = st[input[i]];
			for (int j = 0; j < code.length(); j++)
				if (code.charAt(j) == '1') 
					BinaryStdOut.write(true);
				else
					BinaryStdOut.write(false);
		}
		BinaryStdOut.close();
	}
}
