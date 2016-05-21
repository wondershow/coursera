package mit.unit2;

public class RedBlackBST<Key extends Comparable<Key>, Value> 
{
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	private Node root;
	
	private class Node 
	{
		Node left, right;
		boolean color;
		Key key;
		Value val;
		int N;
		public Node(Key k, Value v, int n, boolean c) 
		{
			key = k;
			val = v;
			N = n;
			color = c;
		}
	}
	
	public RedBlackBST()
	{
		
	}
	
	private boolean isRed(Node n)
	{
		if (n == null) return false; // null link is black
		return n.color;
	}
	
	private Node rotateLeft(Node n)
	{
		Node rightChild = n.right;
		n.right = rightChild.left;
		rightChild.left = n;
		rightChild.color = n.color;
		n.color = RED;
		
		rightChild.N = n.N;
		n.N = 1 + size(n.left)  + size(n.right);
		return rightChild;
	}
	
	private Node rotateRight(Node n)
	{
		Node leftChild = n.left;
		n.left = leftChild.right;
		leftChild.right = n;
		leftChild.color = n.color;
		n.color = RED;
		
		leftChild.N = n.N;
		n.N = 1 + size(n.right)  + size(n.right);
		return leftChild;
	}
	
	private void flipColors(Node n)
	{
		n.left.color = !n.left.color; 
		n.right.color = !n.right.color; 
		n.color = !n.color;
	}
	
	private int size() 
	{
		
		
	}
	
	public void put(Key k, Value v)
	{
		checkNull(k, "Key is not supposed to be null");
		if (v == null) { delete(k); return ; }
		root = put(k, v, root);
		root.color = BLACK; //root always black
	}
	
	private Node put(Key k, Value v, Node n)
	{
		if (n == null) return new Node(k, v, 1, RED); // root key is red
		int cmp = k.compareTo(n.key);
		if (cmp == 0) n.val = v;
		if (cmp > 0) n.right = put(k, v, n.right);
		n.left = put(k, v, n.left);
		
		//check color violation at the end of the recursion
		//means from leaf to root
		if (!isRed(n.left) && isRed(n.right))  n = rotateLeft(n);
		if (isRed(n.left) && isRed(n.left.left)) n= rotateRight(n);
		if (isRed(n.left) && isRed(n.right)) flipColors(n);
		
		n.N = 1 + size(n.left) + size(n.right);
		return n;
	}
	
	private void checkNull(Object o, String s)
	{
		if (o == null) throw new NullPointerException(s);
	}
	
	private int size(Node n)
	{
		if (n == null) return 0;
		return 1 + size(n.left) + size(n.right);
	}
	
	// This function makes sure that 
	//the node n will be a 3-node or 4-node
	private Node moveRedLeft(Node n)
	{
		flipColors(n);
		if (isRed(n.right.left))
		{
			n.right = rotateRight(n.right);
			n = rotateLeft(n);
			flipColors(n);
		}
		return n;
	}
	
	public void deleteMin()
	{
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;
		root = deleteMin(root);
		if (!isEmpty()) root.color = BLACK;
	}
	
	public Node deleteMin(Node n)
	{
		if (n.left == null) return null;
		if (!isRed(n.left) && !isRed(n.left.left))
			n = moveRedLeft(n);
		n.left = deleteMin(n.left);
		return balance(n);
	}
	
	//check all the possible violations 
	//agianst the Red-Black Tree rules
	private Node balance(Node n)
	{
		if (isRed(n.right)) n = rotateLeft(n);
		if (isRed(n.left) && isRed(n.left.left)) n = rotateRight(n);
		if (isRed(n.left) && isRed(n.right)) flipColors(n);
		n.N = 1 + size(n.left) + size(n.right);
		return n;
	}
	
	public boolean isEmpty()
	{
		return root != null;
	}
	
	private Node moveRight(Node n)
	{
		//if (n == null) return null;
		flipColors(n);
		if (isRed(n.left.left))
			n.left = rotateRight(n.left);
		return n;
	}
	
	public void deleteMax()
	{
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;
		root = deleteMax(root);
		if (!isEmpty()) root.color = BLACK;
	}
	
	private Node deleteMax(Node n)
	{
		if (isRed(n.left)) n = rotateRight(n.left);
		if (n.right == null) return null;
		if (!isRed(n.right) && !isRed(n.right.left)
		
		
	}
}
