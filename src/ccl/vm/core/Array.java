package ccl.vm.core;

import java.util.ArrayList;
import java.util.List;

public class Array {

	public List<Expression<?>> list;
	
	public Array(int length) {
		list = new ArrayList<>();
		for(int i = 0; i < length; i++){
			list.add(new Expression<>(new Undefined()));
		}
	}

	public int length(){
		return list.size();
	}
	
	public Expression<?> getExpression(int index) {
		return list.get(index);
	}
	
	public Object get(int index){
		return getExpression(index).getValue();
	}
	
	public void pushExpression(Expression<?> expr){
		list.add(expr);
	}
	
	public void set(int index, Object value){
		list.set(index, new Expression<>(value));
	}
	
	public String toString(){
		return list.toString();
	}

	public static Array clone(Object[] o) {
		Array a = new Array(o.length);
		for(int i = 0; i < o.length; i++){
			a.set(i, o[i]);
		}
		return a;
	}
	public static Array clone(boolean[] o) {
		Array a = new Array(o.length);
		for(int i = 0; i < o.length; i++){
			a.set(i, o[i]);
		}
		return a;
	}
	public static Array clone(char[] o) {
		Array a = new Array(o.length);
		for(int i = 0; i < o.length; i++){
			a.set(i, o[i]);
		}
		return a;
	}
	public static Array clone(short[] o) {
		Array a = new Array(o.length);
		for(int i = 0; i < o.length; i++){
			a.set(i, o[i]);
		}
		return a;
	}
	public static Array clone(byte[] o) {
		Array a = new Array(o.length);
		for(int i = 0; i < o.length; i++){
			a.set(i, o[i]);
		}
		return a;
	}
	public static Array clone(double[] o) {
		Array a = new Array(o.length);
		for(int i = 0; i < o.length; i++){
			a.set(i, o[i]);
		}
		return a;
	}
	public static Array clone(int[] o) {
		Array a = new Array(o.length);
		for(int i = 0; i < o.length; i++){
			a.set(i, o[i]);
		}
		return a;
	}
	public static Array clone(float[] o) {
		Array a = new Array(o.length);
		for(int i = 0; i < o.length; i++){
			a.set(i, o[i]);
		}
		return a;
	}
	public static Array clone(long[] o) {
		Array a = new Array(o.length);
		for(int i = 0; i < o.length; i++){
			a.set(i, o[i]);
		}
		return a;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T[] raw(Class<T> clss){
		T[] arr = (T[]) java.lang.reflect.Array.newInstance(clss, length());
		for(int i = 0; i < arr.length; i++){
			arr[i] = (T) get(i);
		}
		return arr;
	}
	
}
