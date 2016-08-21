package ucsd.ds.hw4;

import java.io.*;
import java.util.*;

public class SetRangeSum {

    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;

    // Splay tree implementation

    // Vertex of a splay tree
    class Vertex {
        int key;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        long sum;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(int key, long sum, Vertex left, Vertex right, Vertex parent) {
            this.key = key;
            this.sum = sum;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    void update(Vertex v) {
        if (v == null) return;
        v.sum = v.key + (v.left != null ? v.left.sum : 0) + (v.right != null ? v.right.sum : 0);
        if (v.left != null) {
            v.left.parent = v;
        }
        if (v.right != null) {
            v.right.parent = v;
        }
    }

    void smallRotation(Vertex v) {
        Vertex parent = v.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = v.parent.parent;
        if (parent.left == v) {
            Vertex m = v.right;
            v.right = parent;
            parent.left = m;
        } else {
            Vertex m = v.left;
            v.left = parent;
            parent.right = m;
        }
        update(parent);
        update(v);
        v.parent = grandparent;
        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = v;
            } else {
                grandparent.right = v;
            }
        }
    }

    void bigRotation(Vertex v) {
        if (v.parent.left == v && v.parent.parent.left == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }

    // Makes splay of the given vertex and returns the new root.
    Vertex splay(Vertex v) {
        if (v == null) return null;
        while (v.parent != null) {
            if (v.parent.parent == null) {
                smallRotation(v);
                break;
            }
            bigRotation(v);
        }
        return v;
    }

    class VertexPair {
        Vertex left;
        Vertex right;
        VertexPair() {
        }
        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }
    }

    // Searches for the given key in the tree with the given root
    // and calls splay for the deepest visited node after that.
    // Returns pair of the result and the new root.
    // If found, result is a pointer to the node with the given key.
    // Otherwise, result is a pointer to the node with the smallest
    // bigger key (next value in the order).
    // If the key is bigger than all keys in the tree,
    // then result is null.
    VertexPair find(Vertex root, int key) {
        Vertex v = root;
        Vertex last = root;
        Vertex next = null;
        while (v != null) {
            if (v.key >= key && (next == null || v.key < next.key)) {
                next = v;
            }
            last = v;
            if (v.key == key) {
                break;
            }
            if (v.key < key) {
                v = v.right;
            } else {
                v = v.left;
            }
        }
        this.root = splay(last);
        if (this.root != null) this.root.parent = null;
        return new VertexPair(next, this.root);
    }
    
    /***
     * 
     ***/
    VertexPair split(Vertex root, int key) {
        VertexPair result = new VertexPair();
        VertexPair findAndRoot = find(root, key);
        
        //findAndRoot.right is the largest element smaller or equal than key
        root = findAndRoot.right;
        
        //findAndRoot.left is next element of 
        result.right = findAndRoot.left;
        
        //if the key is larger than all elements in that tree
        //no need to split at all
        if (result.right == null) {
            result.left = root;
            return result;
        }
        
        //when result.right is the next element of key
        //make that node root of a splay tree
        result.right = splay(result.right);
        
        //
        result.left = result.right.left;
        result.right.left = null;
        if (result.left != null) {
            result.left.parent = null;
        }
        update(result.left);
        update(result.right);
        return result;
    }

    Vertex merge(Vertex left, Vertex right) {
        if (left == null) return right;
        if (right == null) return left;
        while (right.left != null) {
            right = right.left;
        }
        right = splay(right);
        right.left = left;
        update(right);
        return right;
    }

    // Code that uses splay tree to solve the problem

    Vertex root = null;

    void insert(int x) {
        Vertex left = null;
        Vertex right = null;
        Vertex new_vertex = null;
        VertexPair leftRight = split(root, x);
        left = leftRight.left;
        right = leftRight.right;
        if (right == null || right.key != x) {
            new_vertex = new Vertex(x, x, null, null, null);
        }
        root = merge(merge(left, new_vertex), right);
    }

    void erase(int x) {
        // Implement erase yourself
    		if (root == null) return;
    		
    		//this.printBST(root);
    		VertexPair vp = 	find(root, x);
    		//System.out.println();
    		//this.printBST(root);
    		//key x does not exist at all
    		if (vp.right.key != x) return;
    		
    		//
    		Vertex left = vp.right.left;
    		Vertex right = vp.right.right;
    		if (left != null) left.parent = null;
    		if (right != null) right.parent = null;
    		root = merge(left, right);
    		if (root != null) root.parent = null;
    }

    boolean find(int x) {
        // Implement find yourself
    		/*
    		Vertex v = find(x, root);
    		if (v.key != x) return false;
    		splay(v);
        return true;*/
    		if (root == null) return false;
    		VertexPair vp = 	find(root, x);
    		if (vp.right.key == x) return true;
    		return false;
    }
    
    /*
    Vertex find(int key, Vertex root)
    {
    		if (root == null) return null;
    		if (root.key == key)
    			return root;
    		if (key < root.key) {
    			if (root.left != null)
    				return find(key, root.left);
    			return root;
    		} else {
    			if (root.right != null)
    				return find (key, root.right);
    			return root;
    		}
    }*/

    long sum(int from, int to) {
    		if (to < from) return 0;
    		//boolean fromInTree = find(from), toInTree = find(to);
        VertexPair leftMiddle = split(root, from);
        Vertex left = leftMiddle.left;
        Vertex middle = leftMiddle.right;
        VertexPair middleRight = split(middle, to + 1);
        middle = middleRight.left;
        Vertex right = middleRight.right;
        long ans = 0;
        //Complete the implementation of sum
        if (middle != null) ans = middle.sum;
        //if (fromInTree) ans += from;
        //if (toInTree) ans += to;
        root = merge(merge(left, middle), right);
        if (root != null) root.parent = null;
        //if (fromInTree) insert(from);
        //if (toInTree) insert(to);
        return ans;
    }


    public static final int MODULO = 1000000001;

    void solve() throws IOException {
        int n = nextInt();
        int last_sum_result = 0;
        for (int i = 0; i < n; i++) {
            char type = nextChar();
            switch (type) {
                case '+' : {
                    int x = nextInt();
                    insert((x + last_sum_result) % MODULO);
                    //System.out.println("+++++++:" + x);
                } 
                //this.printBST(root); 
                break;
                case '-' : {
                    int x = nextInt();
                    erase((x + last_sum_result) % MODULO);
                    //System.out.println("--------:" + x);
                }
                //this.printBST(root); 
                break;
                case '?' : {
                    int x = nextInt();
                    out.println(find((x + last_sum_result) % MODULO) ? "Found" : "Not found");
                    //System.out.println("?????????:" + x);
                }
                //this.printBST(root); 
                break;
                case 's' : {
                		
                    int l = nextInt();
                    int r = nextInt();
                    //System.out.println("SSSSS b4:" + l + "," + r);
            			//this.printBST(root);
                    long res = sum((l + last_sum_result) % MODULO, (r + last_sum_result) % MODULO);
                    out.println(res);
                    //System.out.println("SSSSS after:" + l + "," + r);
                    last_sum_result = (int)(res % MODULO);
                    //this.printBST(root);
                }
            }
        }
        //this.printBST(root);
    }

    SetRangeSum() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    public static void main(String[] args) throws IOException {
    		SetRangeSum s = new SetRangeSum();
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
    
    char nextChar() throws IOException {
        return nextToken().charAt(0);
    }

    public void printBST(Vertex root) {
    		//Vertex v = root;
    		LinkedList<Vertex> queue = new LinkedList<Vertex>();
    		queue.add(root);
    		while (queue.size() != 0) {
    			Vertex v = queue.poll();
    			int left = -1;// = (v.left == null) ? "-1" : v.left.key;
    			if (v.left != null) { left = v.left.key; queue.add(v.left);}
    			int right = -1;
    			if (v.right != null){ right = v.right.key; queue.add(v.right);}
    			//System.out.println(v.key + " ( " + left + "," + right + ")");
    		}
    }
}
