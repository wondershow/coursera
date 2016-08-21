package ucsd.ds.hw4;
import java.io.*;
import java.util.*;

public class SetRangeSumRaw {

    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;
    public static final int MODULO = 1000000001;
    TreeSet<Integer> tree = new TreeSet<Integer>();
    
    public void insert(int key) {
    		tree.add(key);
    	
    }
    
    public void erase(int key) {
    		tree.remove(key);
    }
    
    public boolean find(int key) {
    		if (tree.contains(key))
    			return true;
    		return false;
    }
    
    public long sum(int from, int to) {
    		Set s = tree.subSet(from, to + 1);
    		long res = 0;
    		for (Object i : s)
    			res += (Integer)i;
    		return res;
    }
    
    void solve() throws IOException {
        int n = nextInt();
        int last_sum_result = 0;
        for (int i = 0; i < n; i++) {
            char type = nextChar();
            switch (type) {
                case '+' : {
                    int x = nextInt();
                    insert((x + last_sum_result) % MODULO);
                } break;
                case '-' : {
                    int x = nextInt();
                    erase((x + last_sum_result) % MODULO);
                } break;
                case '?' : {
                    int x = nextInt();
                    out.println(find((x + last_sum_result) % MODULO) ? "Found" : "Not found");
                } break;
                case 's' : {
                    int l = nextInt();
                    int r = nextInt();
                    long res = sum((l + last_sum_result) % MODULO, (r + last_sum_result) % MODULO);
                    out.println(res);
                    last_sum_result = (int)(res % MODULO);
                }
            }
        }
    }

    SetRangeSumRaw() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new SetRangeSumRaw();
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
    char nextChar() throws IOException {
        return nextToken().charAt(0);
    }
}
