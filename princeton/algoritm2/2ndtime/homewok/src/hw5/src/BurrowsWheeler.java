import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
    	//CircularSuffixArray 
    	//String fullString = BinaryStdIn.readString();
    	//System.out.println(fullString);
    	
    	String fullString = "ABRACADABRA!";
    	
    	CircularSuffixArray cs = new CircularSuffixArray(fullString);
    	int N = fullString.length();
    	int first = -1;
    	//char[] output = new char[fullString.length()];
    	for (int i = 0; i < N; i++) {
    		if ( cs.index(i) == 0 ) first = i;
    		//System.out.println("i = " + i + ", index = " + cs.index(i));
    	} 
    	
    	//BinaryStdOut.write(first);
    	
    	
    	for (int i = 0; i < N; i++) {
    		//BinaryStdOut.write(fullString.charAt(fullString.length() - 1 - cs.index(i)));
    		System.out.println(fullString.charAt( (N - 1 - cs.index(i) - first + i + N) % N) );
    	}
    	//BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
    	
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
    	if (args[0].equals("-")) encode();
    	if (args[0].equals("+")) decode();
    	
    }
}
