package cc150.ch1;

public class RotateMatrix
{
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public static void rotate(int[][] image) {
		int N = image.length;
		for (int r = 0; r < N/2; r++) {
			for (int i = r; i < N/2; i++) {
				int first = r, last = N - 1 - r;
				int tmp = image[r][i];
				image[r][i] = image[N-1-i][r];
				image[N-1-i][r] =  image[N-1-r][N-1-r - i];
				image[N-1-r][N-1-r - i] = image[N-1-r][r+i];
			}
		}
	}
}
