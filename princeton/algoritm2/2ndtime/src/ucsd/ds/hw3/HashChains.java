package ucsd.ds.hw3;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private List<String> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;
    private ArrayList<String>[] buckets;
    

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int)hash % bucketCount;
    }
    

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }
    
    private void add(Query query) {
    		int bucketNumber = hashFunc(query.s);
    		ArrayList<String> al = buckets[bucketNumber];
    		for (String st: al)
    			if (st.equals(query.s)) return;
    		al.add(0, query.s);
    }
    
    private void delete(Query query)
    {
    		int bucketNumber = hashFunc(query.s);
		ArrayList<String> al = buckets[bucketNumber];
		
		for (int i = 0; i < al.size(); i++)
			if (al.get(i).equals(query.s)) { al.remove(i); return; } 
    }
    
    private boolean search(Query query)
    {
    		int bucketNumber = hashFunc(query.s);
		ArrayList<String> al = buckets[bucketNumber];
    		return al.contains(query.s);
    }
    
    private void processQuery(Query query) {
        switch (query.type) {
            case "add":
            		add(query);
                break;
            case "del":
                delete(query);
                break;
            case "find":
                writeSearchResult(search(query));
                break;
            case "check":
                String tmp = "";
                ArrayList<String> al = buckets[query.ind];
                for (int i = 0; i < al.size(); i++)
                		tmp += " " + al.get(i);
                out.print(tmp.trim());
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        buckets = new ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++)
        		buckets[i] = new ArrayList<String>();
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
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
