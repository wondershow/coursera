package cc150.ch1;

public class PalindromePermutation1p4
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public static boolean isPalPermu(String s) {
		int N = s.length();
		int R = 256;
		int[] freq = new int[R];
		for (int i = 0; i < N; i++)
			freq[s.charAt(i)]++; 
		int oddCount = 0;
		for (int i = 0; i < R; i++)
			oddCount += freq[i] % 2;
		return oddCount <= 1;
	}
}
