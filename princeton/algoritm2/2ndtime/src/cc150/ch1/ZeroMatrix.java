package cc150.ch1;

public class ZeroMatrix
{
	
	
	public static void setZeros(int[][] mattrix) {
		if (mattrix == null || mattrix.length == 0) return;
		int rows = mattrix.length;
		int cols = mattrix[0].length;
		boolean[] zeroRows = new boolean[rows];
		boolean[] zeroCols = new boolean[cols];
		
		for (int i = 0; i < rows; i++)
		{
			if (zeroRows[i]) continue;
			for (int j = 0; j < cols; j++)
			{
				if (zeroCols[j]) continue;
				if (mattrix[i][j] == 0)
				{
					//set i row j col as 0;
				}
			}
		}
		
	}
}
