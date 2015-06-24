package utility;

import java.util.ArrayList;

public class Stack<T extends Cloneable> implements Cloneable{
	ArrayList<T> stack;
	public Stack(){
		stack = new ArrayList<>();
	}
	public T pull(){
		T temp = stack.get(0);
		stack.remove(0);
		return temp;
	}
	public Stack<T> push(T object){
		stack.add(object);
		return this;
	}
	public T top(){
		return stack.get(0);
	}
	public int size(){
		return stack.size();
	}
	public boolean isEmpty(){
		if(stack.size() == 0){
			return true;
		}
		return false;
	}
	public Object toArray(){
		return stack.toArray();
	}
	@SuppressWarnings("unchecked")
	public Stack<T> clone(){
		try {
			return (Stack<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
