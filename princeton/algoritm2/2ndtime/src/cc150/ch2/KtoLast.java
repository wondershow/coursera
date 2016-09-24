package cc150.ch2;

public class KtoLast
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	private static SingleListNode KLast(SingleListNode head, int K) {
		SingleListNode second = head;
		int i = 1;
		for (;i <= K && second != null; second = second.next, i++);
		SingleListNode first = head;
		if (second == null) return null;
		while (second.next != null) {
			first = first.next;
			second = second.next;
		}
		return first;
	}
	
	
	//private static SingleListNode KLastRecusrsion()
	
}
