package class3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
/**
 * HW3 of standford algorithm II
 * 
 * **/
public class KnapsackDP
{
	//W is weight capacity, N is # of items
	private static int W, N;
	private static int[] values, weights;
	
	//the 2-dimensional D mattrix
	private static int[][] D;
	private static HashMap<String, Integer> memo;
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		readInFiles("/Users/leizhang/coursera/stanford/algorithm2/src/class3/knapsack2.txt");
		System.out.println(dp2());
	}
	
	private static void readInFiles(String file)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String firstline, line;
			firstline  = br.readLine();
			String[] tmp = firstline.split(" ");
			W = Integer.parseInt(tmp[0]);
			N = Integer.parseInt(tmp[1]);
			values = new int[N + 1 ];
			weights = new int[N + 1];
			
			int lineNumber = 1;
			while ( (line = br.readLine()) != null)
			{
				tmp = line.split(" ");
				values[lineNumber] = Integer.parseInt(tmp[0]);
				weights[lineNumber] = Integer.parseInt(tmp[1]);
				lineNumber++;
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static int dp() {
		D = new int[N+1][W+1];
		
		for (int i = 0; i <= N; i++)
			for (int j = 0; j <= W; j++)
				D[i][j] = 0; 
		
		for (int i = 1; i <= N; i++)
			for (int j = 0; j <= W; j++)
		{
			int w = weights[i];
			int v = values[i];
			
			D[i][j] = Math.max(j>=w ? D[i-1][j-w] + v : 0, D[i - 1][j]);
		}
		return D[N][W];
	}
	
	private static int dp2() 
	{
		memo = new HashMap<String, Integer>();
		int answer = findAnswer(N, W);
//		for (int i = 0; i <= N; i++) {
//			for (int w = 0; w <= W; w++)
//			{
//				String key = i + "," + w;
//				if (memo.containsKey(key))
//					System.out.format("%5d", memo.get(key));
//				else
//					System.out.format("%5d", -1);
//			}
//			System.out.println();
//		}
		return answer;
	}
	
	private static int findAnswer(int i, int w) {
		//if (i <= 0 || w <= 0) return 0;
		String key = i + "," + w;
		if (memo.containsKey(key)) return memo.get(key);
		int answer;
		if (i <= 0 || w <= 0) {
			answer = 0;
			memo.put(key, answer);
			return answer;
		}
		int a1 = findAnswer(i - 1, w);
		int a2 = w - weights[i] >= 0 ? findAnswer(i - 1, w - weights[i]) + values[i] : 0;
		answer = Math.max(a1, a2);
		memo.put(key, answer);
		return answer;
	}
}
