package class4;

public class Edge implements Comparable<Edge>{
	public final int from, to;
	private int weight;
	private boolean isReweighed;
	private int offset;
	
	public Edge(int f, int t, int w)
	{
		from = f;
		to = t;
		weight = w;
		isReweighed = false;
	}
	
	public int compareTo(Edge o)
	{
		if (o == null) throw new NullPointerException();
		if (this.weight < o.weight)
			return -1;
		else if (this.weight > o.weight)
			return 1;
		return 0;
	}
	
	public void reweigh(int delta)
	{
		//isReweighed = true;
		offset = delta;
		if (weight + offset < 0)
			System.out.println("Less than zero!");
		//weight += delta;
	}
	
	public int johnsonWeight()
	{
		return weight + offset;
	}
	
	public int weight() 
	{
		return weight;
	}
}
