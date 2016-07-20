package class1;

import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {
	public int compare(Edge o1, Edge o2)
	{
		if (o1.weight() < o2.weight())
			return -1;
		else if (o1.weight() > o2.weight())
			return 1;
		return 0;
	}
}
