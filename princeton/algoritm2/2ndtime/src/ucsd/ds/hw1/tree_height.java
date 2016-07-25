package ucsd.ds.hw1;
import java.util.*;
import java.io.*;

public class tree_height {
	
    class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader in;

		FastScanner() {
			in = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(in.readLine());
			return tok.nextToken();
		}
		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}

	public class TreeHeight {
		int n, root;
		int parent[], height[];
		ArrayList[] children;
		
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = in.nextInt();
				if (parent[i] == -1) root = i;
				//nodes[i] = new Node(i);
			}
		}

		int computeHeight() {
			height = new int[n];
			Arrays.fill(height, 1);
			children = new ArrayList[n];
			
			HashSet<Integer> l1 = new HashSet<Integer>(),
							   l2 = new HashSet<Integer>(); 
			
			for (int i = 0; i < n; i++) {
				children[i] = new ArrayList<Integer>();
				l1.add(i);
			}
			
			for (int i = 0; i < n; i++)
			{
				int p = parent[i];
				if (p == -1) continue;
				children[p].add(i);
			}
			return height(root);//height[root];
		}
		
		
		private int height(int n) {
			if (children[n].size() == 0) return 1;
			int max = Integer.MIN_VALUE;
			List<Integer> childlist = children[n];
			for (int i : childlist)
			{
				int h = height(i);
				if (h > max) max = h; 
			}
			return max + 1;
		}
		
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_height().run();
                        } catch (IOException e) {
                        }
                    }
                }, "Increase Stack", 1 << 26).start();
	}
	public void run() throws IOException {
		TreeHeight tree = new TreeHeight();
		tree.read();
		System.out.println(tree.computeHeight());
	}
}
