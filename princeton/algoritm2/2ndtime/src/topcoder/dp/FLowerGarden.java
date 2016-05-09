package topcoder.dp;

public class FLowerGarden
{
	public static int[] getOrdering(int[] height, int[] bloom, int[] wilt)
	{
		int N = height.length, i, j;
		for (i = 0; i < N; i++)
			for (j = i; j > 0 && compare(height, bloom, wilt, j, j-1) < 0 ; j--)
				exch(height,bloom, wilt, j, j-1);
		return height;
	}
	
	public static int compare(int[] height, int[] bloom, int[] wilt, int i, int j) 
	{
		if (bloom[i] > wilt[j] || bloom[j] > wilt[i]) // do not overlap at all
			return height[i] > height[j] ? -1 : 1;
		else
			return height[i] > height[j] ? 1 : -1;
	}
	
	public static void exch(int[] height, int[] bloom, int[] wilt, int i, int j)
	{
		int tmp = height[i];
		height[i] = height[j];
		height[j] = tmp;
		
		tmp = bloom[i];
		bloom[i] = bloom[j];
		bloom[j] = tmp;
		
		tmp = wilt[i];
		wilt[i] = wilt[j];
		wilt[j] = tmp;
	}
}
