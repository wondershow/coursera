package mit.unit2;

import java.util.NoSuchElementException;

public class AVL<Key extends Comparable<Key>, Value> extends BST
{
	private Node root;
	private class Node
	{
		Node left, right, parent;
		int N, H;
		Key key;
		Value val;
		public Node(Key k, Value v)
		{
			key = k;
			val = v;
		}
	}
	
	private int height(Node n)
	{
		if (n == null) return -1;
		return n.H;
	}
	
	private void update_height(Node n)
	{
		n.H = 1 + Math.max(height(n.left), height(n.right));
	}
	
	
	private Node rotateLeft(Node n)
	{
		Node y = n.right;
		
		//set up parent first (the node that points to n)
		y.parent = n.parent;
		if (n.parent == null) // n is the root;
			root = y;
		else if (n.parent.left == n)
			n.parent.left = y;
		else
			n.parent.right = y;
		
		n.right = y.left;
		if (n.right != null)
			n.right.parent = n;
		y.left = n;
		n.parent = y;
		
		update_height(n);
		update_height(y);
		
		return y;
	}
	
	private Node rotateRight(Node n)
	{
		Node y = n.left;
		n.parent = y.parent;
		
		//set up paretn
		if (y.parent == null)
			root = y;
		else if (y.parent.left == n)
			y.parent.left = y;
		else
			y.parent.right = y;

		n.left = y.right;
		if (n.left != null) n.left.parent = n;
		
		y.right = n;
		n.parent = y;
		
		update_height(n);
		update_height(y);
		return y;
	}
	
	private void rebalance(Node n)
	{
		int hl = 0, hr = 0;
		while (n != null) 
		{
			update_height(n);
			hl = height(n.left);
			hr = height(n.right);
			if (hl > hr + 1) 
			{
				int hll = height(n.left.left);
				int hlr = height(n.left.right);
				
				//make sure the taller node is always the left child
				if (hll < hlr) rotateLeft(n.left);
				//else rotateRight(n.left);
				
				rotateRight(n);
			} 
			else if (hr > hl + 1) 
			{
				int hrl = height(n.right.left);
				int hrr = height(n.right.right);
				if (hrl > hrr)
					rotateRight(n.right);
				rotateLeft(n);
			}
			n = n.parent;
		}
	}
	
	public void put(Key k, Value v)
	{
		//Node n = super.put(k, v, root);
		root = put(k, v, root);
	}
	
	private Node put(Key k, Value v, Node n)
	{
		if (n == null) return new Node(k, v);
		int cmp = k.compareTo(n.key);
		if (cmp == 0) n.val = v;
		if (cmp < 0) n.left = put(k, v, n.left);
		else 		 n.right = put(k, v, n.right);
		update_height(n);
		n.N = 1 + size(n.left) + size(n.right);
		
		//do rebalance after recursino call, means 
		// we carry out rebalance from leave nodes first
		//then esclates to roots node
		rebalance(n);
		return n;
	}
	
	
	public void delete(Key k)
	{
		if (isEmpty()) throw new NoSuchElementException();
		if (k == null) throw new NullPointerException();
		root = delete(k, root);
		
	}
	
	private Node delete(Key k, Node n)
	{
		if (n == null) return null;
		int cmp = k.compareTo(n.key);
		if (cmp > 0) n.right = delete(k, n.right);
		else if (cmp < 0) n.left = delete(k, n.left);
		else 
		{
			if (n.left == null) return n.right;
			if (n.right == null) return n.left;
			Node p = min(n.right);
			p.right = deleteMin(n.right);
			p.left = n.left;
			n = p;
		}
		update_height(n);
		rebalance(n);
		return n;
	}
	
	private Node min(Node n)
	{
		if (n.left == null) return n;
		return min(n.left);
	}
	
	private Node deleteMin(Node n)
	{
		if (n.left == null) return null;
		return deleteMin(n.left);
	}
	
	
	private int size(Node n)
	{
		if (n == null) return 0;
		return 1 + size(n.left) + size(n.right);
	}
	
	
	public AVL(BST bst)
	{
		
		
	}
}
