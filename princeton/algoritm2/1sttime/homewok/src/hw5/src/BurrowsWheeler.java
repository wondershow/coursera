import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
    	//CircularSuffixArray 
    	String fullString = BinaryStdIn.readString();
    	//System.out.println(fullString);
    	
    	//String fullString = "ABRACADABRA!";
    	
    	CircularSuffixArray cs = new CircularSuffixArray(fullString);
    	int N = fullString.length();
    	int first = -1;
    	//char[] output = new char[fullString.length()];
    	for (int i = 0; i < N; i++) {
    		if ( cs.index(i) == 0 ) first = i;
    	} 
    	
    	BinaryStdOut.write(first);
    	
    	first = 0;
    	for (int i = 0; i < N; i++) 
    		BinaryStdOut.write(fullString.charAt((N - 1 + cs.index(i)) % N));
    	BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
    	int first = 3;
    	
    	first = BinaryStdIn.readInt();
    	
    	String code = BinaryStdIn.readString();
    	
    	//String code = "ARD!RCAAAABB";
    	//key-indexed counting to sort the input sequence
    	char[] precedings = new char[code.length()];
    	
    	for (int i=0; i<code.length(); i++ )
    		precedings[i] = code.charAt(i);
    	
    	int R = 256;
    	int[] count = new int[257];
    	for (int i = 0; i < precedings.length; i++)
    		count[precedings[i] + 1]++;
    	
    	
    	for (int i = 1; i < count.length; i++) 
    		count[i] = count[i - 1] + count[i];
    	
    	char[] aux = new char[code.length()];
    	for (int i = 0; i < precedings.length; i++ )
    		aux[ count[precedings[i]]++] = precedings[i];
    		
    	
    	for (int i = 0; i < precedings.length; i++ )
    		precedings[i] = aux[i];
    	
    	//done preceeding sorting 
    	int[] next = new int[code.length()];
    	
    	next[0] = first;
    	
    	/*
    	for (int i = 0; i<code.length(); i++)
    		System.out.println(precedings[i] + ":" + code.charAt(i) );
    	*/
    	
    	//for those whose count is larger than 1, remember 
    	//the position(in preceding array) of last search ends
    	int[] lastPosition = new int[256];
    	
    	for (int i = 0; i < code.length(); i++) {
    		char prefix = precedings[i];
    		int pos;
    		//search starts only from last stopped place
    		for (pos = lastPosition[prefix]; pos < code.length() && code.charAt(pos) != prefix;pos++);
    		next[i] = pos;
    		lastPosition[prefix] = pos + 1;
    	}
    	
    	int pos = first;
    	while (true) {
    		BinaryStdOut.write(precedings[pos]);
    		pos = next[pos];
    		if (next[pos] == first) break;
    	}
    	BinaryStdOut.write(precedings[pos]);
    	BinaryStdOut.flush();
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
    	if (args[0].equals("-")) encode();
    	if (args[0].equals("+")) decode();
    	//decode();
    }
}
