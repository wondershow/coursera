package class1;

public class Edge
{
	private final int u, v, weight;
	
	public Edge(int _u, int _v, int _w)
	{
		u = _u;
		v = _v;
		weight = _w; 
	}
	
	public int other (int w)
	{
		if (w == u) return v; 
		else return u;
	}
	
	public int weight()
	{
		//return this.weight();
		return weight;
	}
	
	public int vertex() {
		return u;
	}
	
	public String toString() {
		if (u > v)
			return u + ":" + v + ":" + weight;
		else
			return v + ":" + u + ":" + weight;
	}
}
