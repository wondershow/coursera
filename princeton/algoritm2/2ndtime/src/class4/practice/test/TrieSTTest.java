package class4.practice.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import class4.practice.TrieST;

public class TrieSTTest
{

	/**
	 * To create a trie according to the example at page 731 of textbook(RW V4)
	 * */
	private TrieST intialize() 
	{
		TrieST<Integer> st = new TrieST<Integer>();
		st.put("she", 0);
		st.put("sells", 1);
		st.put("sea", 2);
		st.put("shells", 3);
		st.put("by", 4);
		st.put("the", 5);
		st.put("sea", 6);
		st.put("shore", 7);
		//st.contains(key);
		return st;
	}
	
	@Test
	public void testGet()
	{	
		TrieST<Integer> example = intialize();
		Assert.assertEquals(example.get("she").intValue(),0);
		Assert.assertEquals(example.get("sells").intValue(),1);
		Assert.assertEquals(example.get("sea").intValue(),2);
		Assert.assertEquals(example.get("shells").intValue(),3);
		Assert.assertEquals(example.get("by").intValue(),4);
		Assert.assertEquals(example.get("the").intValue(),5);
		Assert.assertEquals(example.get("sea").intValue(),6);
		Assert.assertEquals(example.get("shore").intValue(),7);
	}
	
	@Test
	public void testSize()
	{
		TrieST<Integer> example = intialize();
		Assert.assertEquals(example.size(), 8);
	}
	
	@Test
	public void testDelete() 
	{
		TrieST<Integer> example = intialize();
		example.delete("shore");
		Assert.assertEquals(example.get("shore").intValue(), null);
		Assert.assertEquals(example.size(), 7);
	}
	
	
}
