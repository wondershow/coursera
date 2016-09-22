package cc150.ch1;

public class StringCompression
{
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public static String compress(String s) {
		StringBuilder sb = new StringBuilder("");
		//s = s.toLowerCase();
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			count++;
			if (i == s.length() - 2 || s.charAt(i) != s.charAt(i+1)) {
				sb.append(s.charAt(i));
				sb.append(count);
			}
		}
		return sb.toString();
	}
}
