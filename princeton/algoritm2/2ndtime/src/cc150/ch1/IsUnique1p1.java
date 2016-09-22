package cc150.ch1;

public class IsUnique1p1
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * space o(R)
	 * time o(n)
	 * will be a problem if R(characterset) is too large
 	 * **/
	public static boolean isUnique(String s) {
		int R = 256;
		if (s.length() > R) return false;
		boolean[] freq = new boolean[R];
		for (int i = 0; i < s.length(); i++)
		if (freq[(int)s.charAt(i)]) return false;
		else freq[(int)s.charAt(i)] = true;
		return true;
	}
	
	/**
	 * Assumes: string only contains a-z
	 * 
	 ***/
	private static boolean isUnique2(String s) {
		int checker = 0;
		for (int i = 0; i < s.length(); i++)
		{
			int val = s.charAt(i) - 'a';
			if ( (checker & (1<<val)) > 0) return false;
			checker |= (1<<val);
		}
		return true;
	}
}
