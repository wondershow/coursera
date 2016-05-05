package topcoder.dp;

public class BadNeighbors
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	public static int maxDonations(int[] donations)
	{
		int N = donations.length;
		
		if (N == 1) return donations[0];
		if (N == 2) return donations[0] > donations[1] ? donations[0] : donations[1];

		int i = 0;
		
		boolean[] donated = new boolean[N];
		boolean[] include_0 = new boolean[N];
		int[] max = new int[N];
		max[0] = donations[0];
		include_0[0] = true;
		
		if ( donations[0] > donations[1] ) 
		{ max[1] = donations[0]; donated[1] = false; include_0[1] = true; 	}
		else 
		{ max[1] = donations[1]; donated[1] = true; include_0[1] = false;  }
		
		for (i = 2; i <= N - 1; i++)
		if (max[i-2] +  donations[i] >= max[i-1]) 
		{
			max[i] = max[i-2] + donations[i];
			include_0[i] = include_0[i - 2];
			donated[i] = true;
		} 
		else 
		{
			max[i] = max[i-1];
			include_0[i] = include_0[i - 1];
			donated[i] = false;
		}
			
		int res = 0;
		if (include_0[N-1] && donated[N-1]) 
			res = Math.max(Math.max(max[N-2], max[N-1] - donations[N-1]), max[N-1] - donations[0]);
		else
			res = max[N-1];
		
		
		return res;
	}
}
