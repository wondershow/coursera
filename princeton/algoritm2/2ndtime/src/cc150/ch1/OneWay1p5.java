package cc150.ch1;

public class OneWay1p5
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public static boolean checkOneWay(String s1, String s2) {
		if (s1 == null || s2 == null) return false;
		if (Math.abs(s1.length() - s2.length()) > 1) return false;
		String longer = s1.length() >= s2.length() ? s1:s2;
		String shorter = s1.length() >= s2.length() ? s2:s1;
		
		boolean diffCount = false;
		int i = 0, j = 0;
		
		
		for (; i < longer.length() && j < shorter.length(); i++) {
			if (longer.charAt(i) == shorter.charAt(j)) {
				j++;
				continue;
			}
			if (diffCount) return false;
			diffCount = false;
			if (longer.length() == shorter.length())
				j++;
		}
		
		return true;
	}
}
