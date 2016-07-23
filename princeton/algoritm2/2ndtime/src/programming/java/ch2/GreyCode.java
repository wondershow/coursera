package programming.java.ch2;
/**
 * This code for recursion practice. 
 * See Rober Serdgwick Programming In java.
 * Chapter 2.3 Recursion GreyCode
 * */
public class GreyCode
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		move(4, true);
	}

	private static void move(int n, boolean enter) {
		if (n==0) return;
		move(n-1, true);
			if (enter) System.out.println(n + " enters " );
			else System.out.println(n + " exits " );
		move(n-1, false);
	}
}
