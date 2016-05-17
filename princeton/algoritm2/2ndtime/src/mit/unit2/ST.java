package mit.unit2;

public abstract class ST<Key, Value>
{
	public abstract void put(Key key, Value val);
	
	public abstract Value get(Key key);
	
	public abstract void delete(Key key);
	
	public abstract boolean contains(Key key);
	
	public abstract boolean isEmpty();
	
	public abstract int size();
	
	public abstract Iterable<Key> keys();
}
