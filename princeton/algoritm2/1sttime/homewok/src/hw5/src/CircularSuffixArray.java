public class CircularSuffixArray {
	private String oriString;
	private int[] mapping;
	private final int N;
	private final int R = 256;
	
    public CircularSuffixArray(String s) {  // circular suffix array of s 
    	if (s == null) throw new java.lang.NullPointerException();
    	oriString = s;
    	N = s.length();
    	mapping = new int[N];
    	for (int i = 0; i < N; i++)
    		mapping[i] = i;
    	
    	String[] cirStrArr = new String[N];
    	
    	
    	for (int delta = 0; delta < N; delta++) {
    		StringBuilder sb = new StringBuilder("");
    		for (int i = 0; i < N; i++) 
    			sb.append(oriString.charAt( (i + delta) % N  ) );
    		cirStrArr[delta] = sb.toString();
    	}
    	
    	
    	quickSort(cirStrArr, 0, cirStrArr.length - 1, 0);
    	
    	
    	
    	for (int i = 0; i < cirStrArr.length; i++)
    		System.out.println(index(i));
    	
    	for (int i = 0; i < cirStrArr.length; i++)
    		System.out.println(cirStrArr[i]);
    	
    	//sort();
    }
    
    public int length()   {               // length of s
    	return oriString.length();
    }
    
    public int index(int i)   {            // returns index of ith sorted suffix
    	if (i < 0 || i >= oriString.length()) throw new java.lang.IndexOutOfBoundsException();
    	return mapping[i];
    }
    
    public static void main(String[] args) {// unit testing of the methods (optional)
    	
    	CircularSuffixArray cs = new CircularSuffixArray("ABRACADABRA!");
    }
    
    
    private static int charAt(String s, int d) {
    	if (d < s.length()) return s.charAt(d); else return -1;
    }
    
    private void quickSort(String[] cirArr, int lo, int hi, int d) {
    	if (hi <= lo) return; 
    	//System.out.println("quicksorting " + lo + ", to " + hi + "d = " + d + ", len = " + cirArr.length);
    	int lt = lo, gt = hi;
    	int v = charAt(cirArr[lo],d);
    	int i = lo + 1;
    	
    	while (i <= gt) {
    		int t = charAt(cirArr[i], d);
    		if (t < v) exch(cirArr, lt++, i++);
    		else if (t > v) exch(cirArr, i, gt--);
    		else i++;
    	}
    	quickSort(cirArr,lo, lt - 1, d);
    	if (v > 0) quickSort(cirArr,lt, gt, d + 1);
    	quickSort(cirArr, gt + 1, hi, d);
    }
    
    private void exch(String[] cirArr, int i, int j) {
    	//System.out.println("exchanging " + i + ", " + j);
    	String tmp = cirArr[i];
    	cirArr[i] = cirArr[j];
    	cirArr[j] = tmp;
    	int t = mapping[i];
    	mapping[i] = mapping[j];
    	mapping[j] = t;
    }
}
