package ussd.ds.hw2;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;
    
    private class Job implements Comparable{
    		public long start, length, finish;
    		public int worker;
    		
    		public Job (long _start, long _length, int _worker)
    		{
    			start = _start;
    			length = _length;
    			worker = _worker;
    			finish = _start + _length;
    		}

		@Override
		public int compareTo(Object o)
		{
			if (o == null) throw new NullPointerException();
			Job that = (Job) o;
			if (this.finish < that.finish)
				return -1;
			else if (this.finish > that.finish)
				return 1;
			else if (this.worker < that.worker)
				return -1;
			return 1;
		}
    }

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        
        int N = jobs.length;
        PriorityQueue<Job> heap = new PriorityQueue<Job>();
        
        for (int i = 0; i < numWorkers && i < N; i++) {
        		assignedWorker[i] = i;
        		heap.add(new Job(0, jobs[i], i));
        		startTime[i] = 0;
        }
        
        int i = heap.size();
        while (!heap.isEmpty()) {
        		if (i >= N) break;
        		Job j = heap.poll();
        		assignedWorker[i] = j.worker;
        		startTime[i] = j.finish;
        		heap.add(new Job(j.finish, jobs[i++], j.worker));
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
