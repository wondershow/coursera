package ussd.ds.hw2;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class MergingTables {
    private final InputReader reader;
    private final OutputWriter writer;

    public MergingTables(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new MergingTables(reader, writer).run();
        writer.writer.flush();
    }

    class Table {
        Table parent;
        int rank;
        int numberOfRows;

        Table(int numberOfRows) {
            this.numberOfRows = numberOfRows;
            rank = 0;
            parent = this;
        }
        Table getParent() {
            // find super parent and compress path
            return parent;
        }
    }

    int maximumNumberOfRows = -1;

    void merge(Table destination, Table source) {
        Table realDestination = destination.getParent();
        Table realSource = source.getParent();
        if (realDestination == realSource) {
            return;
        }
        // merge two components here
        // use rank heuristic
        // update maximumNumberOfRows
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();
        Table[] tables = new Table[n];
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            int numberOfRows = reader.nextInt();
            tables[i] = new Table(numberOfRows);
            uf.setSize(i+1, numberOfRows);
            maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
        }
        for (int i = 0; i < m; i++) {
            int destination = reader.nextInt();
            int source = reader.nextInt();
            uf.union(source, destination);
            //merge(tables[destination], tables[source]);
            writer.printf("%d\n", uf.max());
        }
    }


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
    
    class UnionFind
    {
    	private int[] parent, rank;
    	private long[] size;
    	private int N;
    	private long maxSize;
    	
    	
    	public UnionFind(int n)
    	{
    		N = n;
    		parent = new int[N+1];
    		rank = new int[N+1];
    		size = new long[N+1];
    		for (int i = 1; i <=N; i++) {
    			parent[i] = i;
    			rank[i] = 0;
    			size[i] = 1;
    		}
    		maxSize = 1;
    	}
    	
    	public int find(int i)
    	{
    		if (i != parent[i])
    			parent[i] = find(parent[i]);
    		return parent[i];
    	}
    	
    	public void setSize(int i, int sz)
    	{
    		size[i] = sz;
    		if (sz > maxSize) maxSize = sz;
    	}
    	
    	public void union(int i, int j)
    	{
    		int r1 = find(i), r2 = find(j);
    		if (r1 == r2) return;
    		if (rank[r1] > rank[r2]) {
    			parent[r2] = r1;
    			size[r1] += size[r2];
    			if (size[r1] > maxSize)
    				maxSize = size[r1];
    		} else {
    			parent[r1] = r2;
    			size[r2] += size[r1];
    			if (r1 == r2)
    				rank[r2]++;
    			if (size[r2] > maxSize)
    				maxSize = size[r2];
    		}
    	}
    	
    	public long max() {
    		return maxSize;
    	}
    }

}
