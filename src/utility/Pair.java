package utility;

public class Pair<S,T> implements Cloneable{
	S key;
	T value;
	Pair(S key, T value){
		this.key = key;
		this.value = value;
	}
	public S getKey(){
		return key;
	}
	public T getValue(){
		return value;
	}
	public Pair<S,T> setValue(T value){
		this.value = value;
		return this;
	}
	public Pair<S,T> setKey(S key){
		this.key = key;
		return this;
	}
}
