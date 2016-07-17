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
	
	int other (int w)
	{
		if (w == u) return v; 
		else return u;
	}
	
	int weight()
	{
		//return this.weight();
		return weight;
	}
	
	int vertex() {
		return u;
	}
}
