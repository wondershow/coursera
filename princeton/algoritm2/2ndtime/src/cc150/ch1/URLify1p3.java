package cc150.ch1;

public class URLify1p3
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public static void handleURL(char[] url) {
		int len = 0, space = 0;
		for (int l = url.length - 1; l > 0; l--){
			if (len == 0 && url[l] == ' ') continue;
			len++;
			if (url[l] == ' ') space++ ;
		}
		int j = len + space *2 -1,  i = len - 1;
		for (; i >= 0; i--) 
		{	
			if (url[i] != ' ')
				url[j--] = url[i];
			else {
				url[j--] = '0';
				url[j--] = '2';
				url[j--] = '%';
			}
		}
	}
}
