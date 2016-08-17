package ucsd.ds.hw3;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
    
    private static final int P = 1000007;
    private static final int R = 256;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        long RM = 1;
        for (int i = 1; i < m; i++)
        		RM = (R*RM) % P;
        
        long pathash = hash(s, m);
        long txthash = hash(t, m);
        if (txthash ==  pathash && equals(s, t, 0))  occurrences.add(0);
        for (int i = 0; i + m < n; ++i) {
        		txthash = (txthash + P - RM*t.charAt(i) % P) % P;
        		txthash = (txthash * R + t.charAt(i+m)) % P;
        		if (txthash == pathash && equals(s, t, i))
        			occurrences.add(i);
	    }
        return occurrences;
    }

    static class Data {
        String pattern;
        String text;
        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
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
    
    private static long hash(String s, int len) {
        long hash = 0;
        for (int i = 0; i < len; i++)
            hash = (hash * R + s.charAt(i)) % P;
        return hash;
    }
    
    private static boolean equals(String pattern, String txt, int from)
    {
    		System.out.println("Comparing " + pattern + " and " + txt.substring(from, from + pattern.length()));
    		for (int i = 0; i < pattern.length(); i++)
    			if (pattern.charAt(i) != txt.charAt(i + from)) return false;
    		return true;
    }
}

