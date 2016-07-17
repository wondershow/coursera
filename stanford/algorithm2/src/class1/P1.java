package class1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class P1
{
	private static class Job {
		public int weight, length;
		Job(int w, int l) {
			weight = w;
			length = l;
		}
	}
	
	private static Job[] readInJobs(String file)
	{
		Job[] jbs = null;// 
		int N;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String firstline, line;
		    firstline  = br.readLine();
		    N = Integer.parseInt(firstline);
		    jbs = new Job[N];
		    int len = 0;
		    //weights = new int[N];
		    //lengths = new int[N];
		    while ((line = br.readLine()) != null) {
		    		// process the line.
		    		if(line.trim().equals("")) continue;
		    		String[] tmp = line.split(" ");
		    		//weights[len] = Integer.parseInt(tmp[0]);
		    		//lengths[len++] = Integer.parseInt(tmp[1]);
		    		jbs[len++] = new Job(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1]));
		    }
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return jbs;
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String file = "/Users/leizhang/coursera/stanford/algorithm2/src/class1/jobs.txt";
		//int[] weights, lengths;
		
		Job[] jbs = readInJobs(file);
		
		Arrays.sort(jbs, new JobRatioComp());
		
		long cmpl = 0, sumOfW = 0;
		for (Job j : jbs)
		{
			cmpl += j.length;
			sumOfW += j.weight * cmpl;
		}
		
		System.out.println(sumOfW);
	}
	
	//this comparator compares two jobs by its (weight - length)
	private static class JobDiffComp implements Comparator<Job> {
		public int compare(Job o1, Job o2)
		{
			int score1 = o1.weight - o1.length;
			int score2 = o2.weight - o2.length;
			if (score1 > score2) return -1;
			else if (score1 < score2) return 1;
			if (o1.weight > o2.weight) return -1;
			else if (o1.weight < o2.weight) return 1;
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	private static class JobRatioComp implements Comparator<Job> {
		public int compare(Job o1, Job o2)
		{
			int w1l2 = o1.weight*o2.length;
			int w2l1 = o2.weight*o1.length;
			if (w1l2 > w2l1) return -1;
			else if (w1l2 < w2l1) return 1;
			//if (o1.weight > o2.weight) return -1;
			//else if (o1.weight < o2.weight) return 1;
			// TODO Auto-generated method stub
			return 0;
		}
	}
}
