import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class TreeSetDict implements Dictionary {
	private TreeSet<String> dictPool;

	
	public TreeSetDict() {
		dictPool = new TreeSet<String>(new StringComparator());
	}
	
	@Override
	public void put(String word) {
		// TODO Auto-generated method stub
		dictPool.add(word);
	}

	@Override
	public boolean contains(String word) {
		// TODO Auto-generated method stub
		return dictPool.contains(word);
	}

	@Override
	public boolean hasPrefix(String pfx) {
		String toEle = pfx + "ZZZZZZZZZZZZZZZZZZZ";
		SortedSet<String> ss = dictPool.subSet(pfx, toEle);
		Iterator<String> it = ss.iterator();
		return it.hasNext();
	}
	
	
	public void printSelf() {
		Iterator<String> it = dictPool.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	public static void main(String[] s) {
		TreeSetDict ts = new TreeSetDict();
		ts.put("c");
		ts.put("a");
		ts.put("d");
		ts.put("b");
		ts.put("ab");
		ts.put("abd");
		ts.put("abda");
		ts.put("abdb");
		ts.put("abc");
		ts.put("abcd");
		ts.put("abcde");
		ts.put("abcc");
		ts.put("abcda");
		
		//ts.printSelf();
		SortedSet<String> ss = ts.dictPool.subSet("abc","abd");
		System.out.println(ts.hasPrefix("a"));
		System.out.println(ts.hasPrefix("b"));
		System.out.println(ts.hasPrefix("abcc"));
		System.out.println(ts.hasPrefix("f"));
	}
	
	
	
}

class StringComparator implements Comparator<String> {
	public int compare(String s1, String s2) {
		int a = 0, b = 0;
		while (a < s1.length() && b<s2.length()) {
			if (s1.charAt(a) != s2.charAt(b)) 
				return (s1.charAt(a) - s2.charAt(b) )> 0 ? 1 : -1;
			//else continue;
			a++;
			b++;
		}
		
		//if (a == b) return 0;
		if (a == s1.length() && b == s2.length()) return 0;
		else if(a == s1.length()) return -1;
		else return 1;
	}
}