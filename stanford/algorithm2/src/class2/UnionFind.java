package class2;


/***
 * 
 * A UnionFind with find as log*N
 * and union as logN
 * */
public class UnionFind
{
	private int[] parent, rank;
	
	//N is the original points, connected is #
	//of connected parts.
	private int N, connected; 
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * How many points in this UF structure
	 * **/
	public UnionFind(int _N) {
		N = _N;
		connected = N;
		parent = new int[N+1];
		rank = new int[N+1];
		for (int i = 1; i <= N; i++)
		{
			parent[i] = i;
			rank[i] = 0;
		}
	}
	
	/***
	 * Root of a node
	 */
	public int find(int n) {
		while (n != parent[n])
			n = parent[n];
		return n;
	}
	
	/**
	 * Union two nodes
	 * */
	public void union(int u, int v)
	{
		int r1 = find(u), r2 = find(v);
		if (r1 == r2) return;
		//System.out.println("Unioning " + u + " , " + v);
		if (rank[r1] <= rank[r2])
		{
			parent[r1] = parent[r2];
			compressPath(u, r2);
			if (rank[r1] == rank[r2])
				rank[r2]++;
		} else {
			parent[r2] = parent[r1];
			compressPath(v, r1);
		}
		connected--;
	}
	
	/**
	 * do the path compression
	 * **/
	private void compressPath(int u, int newroot)
	{
		while (parent[u] != newroot) {
			int oldParent = parent[u];
			parent[u] = newroot;
			u = oldParent;
		}
	}
	
	/***
	 * How many connected components in this UF 
	 * structure
	 * */
	public int clusters()
	{
		return this.connected;
	}
}
