package topcoder.dp;

public class ChessMetric
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public static long howMany(int size, int[] start, int[] end, int numMoves)
	{
		long[][] pre = new long[size][size];
		long[][] cur = new long[size][size];
		pre[start[0]][start[1]] = 1;
		
		for (int i = 0; i < numMoves; i++) {
			for (int row = 0; row < size; row++) 
			{
				for (int col = 0; col < size; col++)
				{
					if (pre[row][col] != 0)
					{
						if (row + 1 < size) cur[row+1][ col] += pre[row][col];
						if (col + 1 < size) cur[row][col+1] += pre[row][col];
						if (row > 0) cur[row - 1][col] += pre[row][col];
						if (col > 0) cur[row] [col - 1] += pre[row][col];
						if (row > 0 && col >0) cur[row-1][col-1] += pre[row][col];
						if (row + 1 < size && col +1 < size) cur[row+1][col+1] += pre[row][col];
						if (row > 0 && col + 1 < size) cur[row -1][col + 1] += pre[row][col];
						if (row + 1 < size && col > 0) cur[row + 1][col-1] += pre[row][col];
						
						if (row - 2 >= 0 && col + 1 < size) cur[row - 2][col + 1] += pre[row][col];
						if (row + 2 < size && col + 1 < size) cur[row + 2][col +1] += pre[row][col];
						if (row - 2 >= 0 && col - 1 >=0 ) cur[row - 2][col - 1] += pre[row][col];
						if (row + 2 < size && col - 1 >=0 ) cur[row + 2][col -1] += pre[row][col];
						
						if (row - 1 >=0 && col + 2 < size) cur[row - 1][col + 2] += pre[row][col];
						if (row + 1 <size && col + 2 < size) cur[row + 1][col + 2] += pre[row][col];
						if (row - 1 >=0 && col - 2 >= 0) cur[row - 1][col - 2] += pre[row][col];
						if (row + 1 < size && col - 2 >=0) cur[row + 1][col - 2] += pre[row][col];
					}
				}
			}
			for (int j = 0; j < size; j++)
				pre[j] = cur[j].clone();
		}
		return cur[end[0]][end[1]];
	}
	
}
