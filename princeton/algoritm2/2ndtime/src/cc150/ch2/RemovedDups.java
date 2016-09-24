package cc150.ch2;

import java.util.HashSet;

public class RemovedDups
{
	class ListNode {
		int val;
		ListNode next;
		
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * space o(n)
	 * time o(n)
	 * */
	public static SingleListNode removeDups(SingleListNode head) {
		HashSet<Integer> preset = new HashSet<Integer>();
		SingleListNode node = head;
		preset.add(node.val);
		while (node.next != null) {
			if (preset.contains(node.next.val)) {
				node.next = node.next.next;
			} else {
				preset.add(node.next.val);
				node = node.next;
			}
		}
		return head;
	}
	
	public static SingleListNode removeDups1(SingleListNode head) {
		if (head == null) return null;
		SingleListNode node = head;
		
		while (node != null) 
		{
			removeDups1Helper(node);
			node = node.next;
		}
		return head;
	}
	
	//remove all the duplicates with same value as the head
	private static void removeDups1Helper(SingleListNode head) {
		SingleListNode node = head;
		while (node.next != null && node.next.val == head.val) {
			node.next = node.next.next;
			node = node.next;
		}
	}
}
