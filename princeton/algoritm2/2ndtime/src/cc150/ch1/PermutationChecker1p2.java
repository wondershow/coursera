package cc150.ch1;

import java.util.Arrays;

public class PermutationChecker1p2
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * space o(R)
	 * time o(n)
	 * **/
	public static boolean checkPermut1(String s1, String s2) {
		if (s1 == null && s2 == null) return true;
		if (s1 == null || s2 == null) return false;
		if (s1.length() != s2.length()) return false;
		int R = 26; //only lowercases
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();
		int[] f = new int[R];
		//int[] f2 = new int[R];
		
		for (int i = 0; i < s1.length();i++)
			f[s1.charAt(i) - 'a']++;
		for (int i = 0; i < s2.length(); i++) {
			f[s2.charAt(i) - 'a']--;
			if (f[s2.charAt(i) - 'a'] < 0) return false;
		}
		return true;
	}
	
	
	private static String sortString(String s) {
		char[] content = s.toCharArray();
		Arrays.sort(content);
		return new String(content);
	}
	
	public static boolean checkPermut2(String s1, String s2) {
		if (s1.length() != s2.length()) return false;
		return sortString(s1).equals(sortString(s2));
	}
}
