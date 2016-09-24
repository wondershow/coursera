package cc150.ch2;

public class Partition
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public static SingleListNode partition (SingleListNode head, int k) {
		if (head == null) return;
		SingleListNode newHead = new SingleListNode(-1, head);
		SingleListNode prev = newHead, cur = prev.next;
		
		while (cur != null) {
			if (cur.val >= k) { 
				prev = prev.next;
				cur = cur.next; 
				continue;
			}
			prev.next = cur.next;
			cur.next = newHead.next;
			newHead.next = cur;
			cur = prev.next;
		}
		return newHead.next;
		
	}
}
