package ucsd.ds.hw4;

import java.io.IOException;
import java.util.Random;

public class StressTestRangeSum
{
	public static void main(String[] args) throws IOException
	{
		SetRangeSum mySolution = new SetRangeSum();
		SetRangeSumRaw refSolution = new SetRangeSumRaw();
		int RANGE = 10000;
		int N = 0;
		
		// TODO Auto-generated method stub
		while (N <= 10000) 
		{
			
			N++;
			Random rn = new Random();
			int number = rn.nextInt(RANGE) + 1;
			int number2 = number + rn.nextInt(RANGE) + 1; 
			
			int pro = rn.nextInt(10) + 1;
			
			if (pro <= 2) { // do erase
				mySolution.erase(number);
				refSolution.erase(number);
				//System.out.println("- " + number);
			} else if (pro <= 8)  { // do add
				mySolution.insert(number);
				refSolution.insert(number);
				//System.out.println("+ " + number);
			} else { // query
				long s1 = mySolution.sum(number, number2);
				long s2 = refSolution.sum(number, number2);
				if (s1 != s2){
					System.out.println(" wrong: s " + number + " " + number2 + " res = " + s1 + ", " + s2);
				}
			}
			
			boolean b1 = mySolution.find(number);
			boolean b2 = refSolution.find(number);
			
			if (b1 != b2)
			{
				System.out.println("wrong: ? "+number+" = "  +  mySolution.find(number) + "," + refSolution.find(number));
				
			}
			
		}
	}
}
