package topcoder.dp;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Assert;

public class AvoidRoads
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	
	public static long numWays(int w, int l, String[] bad)
	{
		int badN = bad.length;
		int[][] bads = new int[badN][4];
		
		//parse the bad string and put the blocked streets into an array
		for (int i = 0; i < badN; i++)
		{
			String[] tmp = bad[i].split("\\s+");
			int x1 = Integer.parseInt(tmp[0]);
			int y1 = Integer.parseInt(tmp[1]);
			int x2 = Integer.parseInt(tmp[2]);
			int y2 = Integer.parseInt(tmp[3]);
			
			int cmp = (x1 != x2)? x1 - x2 : y1 - y2;
			
			if (cmp > 0) 
			{ bads[i][0] = x1; bads[i][1] = y1; bads[i][2] = x2; bads[i][3] = y2;}
			else
			{ bads[i][0] = x2; bads[i][1] = y2; bads[i][2] = x1; bads[i][3] = y1;}
		}
		
		//sort the blocked streets
		Arrays.sort(bads, new Comparator<int[]>(){
			public int compare(int[] a, int b[]){
				if (a[0] != b[0]) return a[0] - b[0];
				else if (a[1] != b[1]) return a[1] - b[1];
				else if (a[2] != b[2]) return a[2] - b[2];
				return a[3] - b[3];
			}
		});
		
		
		long[][] res = new long[l+1][w+1];
		res[0][0] = 1;
		int[] street = new int[4]; 
		for (int row = 0; row <= l; row++ )
			for (int col = 0; col <= w; col++)
		{
			if (row > 0 ) 
			{
				street[0] = col; street[1] = row;
				street[2] = col; street[3] = row - 1;
				if (!search(bads, street))
					res[row][col] += res[row - 1][col];
			}
			if (col > 0)
			{
				street[0] = col; street[1] = row;
				street[2] = col - 1; street[3] = row;
				if (!search(bads, street))
					res[row][col] += res[row][col - 1];
			}
		}
		return res[l][w];
	}
	
	private static boolean search(int[][] bads, int[] street)
	{
		int lo = 0, hi = bads.length - 1;
		while (lo <= hi)
		{
			int mid = (lo + hi) / 2;
			int cmp = bads[mid][0] != street[0] ? bads[mid][0] - street[0] :
				      bads[mid][1] != street[1] ? bads[mid][1] - street[1] :
				      bads[mid][2] != street[2] ? bads[mid][2] - street[2] :
				      bads[mid][3] - street[3];
			if (cmp > 0) hi = mid - 1;
			else if (cmp < 0) lo = mid + 1;
			else return true;
		}
		return false;
	}
}
