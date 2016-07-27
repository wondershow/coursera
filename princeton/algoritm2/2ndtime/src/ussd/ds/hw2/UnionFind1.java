package ussd.ds.hw2;

public class UnionFind1
{
	private int[] parent, rank;
	private int N;
	private long maxSize;
	private long[] size;
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public UnionFind1(int n)
	{
		N = n;
		parent = new int[N+1];
		rank = new int[N+1];
		size = new long[N+1];
		for (int i = 1; i <=N; i++) {
			parent[i] = i;
			rank[i] = 0;
			size[i] = 1;
		}
		maxSize = 1;
	}
	
	public int find(int i)
	{
		if (i != parent[i])
			parent[i] = find(parent[i]);
		return parent[i];
	}
	
	public void setSize(int i, int sz)
	{
		size[i] = sz;
		if (sz > maxSize) maxSize = sz;
	}
	
	public void union(int i, int j)
	{
		int r1 = find(i), r2 = find(j);
		if (r1 == r2) return;
		if (rank[r1] > rank[r2]) {
			parent[r2] = r1;
			size[r1] += size[r2];
			if (size[r1] > maxSize)
				maxSize = size[r1];
		} else {
			parent[r1] = r2;
			size[r2] += size[r1];
			if (r1 == r2)
				rank[r2]++;
			if (size[r2] > maxSize)
				maxSize = size[r2];
		}
	}
	
	public long max() {
		return maxSize;
	}
}
