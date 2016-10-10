package ccl.vm.bridge;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import ccl.iface.CclException;

public class JClass {

	private Field[] fields;
	private Method[] methods;
	private Constructor<?>[] constructors;
	private Class<?> normal;

	public Field[] getFields() {
		return fields;
	}

	public Method[] getMethods() {
		return methods;
	}
	
	public Constructor<?>[] getConstructors(){
		return constructors;
	}

	public JClass(String s) throws CclException {
		try{
			init(s);
		}catch(Exception e){
			throw new CclException(e);
		}
	}

	private void init(String s) throws Exception{
		Class<?> c = Class.forName(s);
		this.normal = c;
		initFields(c);
		initMethods(c);
		initConstructors(c);
	}
	
	private void initConstructors(Class<?> c) {
		Constructor<?>[] constructors = c.getConstructors();
		ArrayList<Constructor<?>> filtered = new ArrayList<Constructor<?>>();
		for(int i = 0; i < constructors.length; i++){
			Constructor<?> f = constructors[i];
			filtered.add(f);
		}
		this.constructors = filtered.toArray(new Constructor<?>[0]);
	}

	private void initMethods(Class<?> c) {
		Method[] methods = c.getMethods();
		ArrayList<Method> filtered = new ArrayList<Method>();
		for(int i = 0; i < methods.length; i++){
			Method f = methods[i];
			if(Modifier.isStatic(f.getModifiers())) filtered.add(f);
		}
		this.methods = filtered.toArray(new Method[0]);
	}

	private void initFields(Class<?> c) {
		Field[] fields = c.getFields();
		ArrayList<Field> filtered = new ArrayList<Field>();
		for(int i = 0; i < fields.length; i++){
			Field f = fields[i];
			if(Modifier.isStatic(f.getModifiers())) filtered.add(f);
		}
		this.fields = filtered.toArray(new Field[0]);
	}

	public Class<?> getNormal() {
		return normal;
	}
	
	public Field getField(String name) {
		for(int i = 0; i < fields.length; i++){
			Field f = fields[i];
			if(f.getName().equals(name)) return f;
		}
		return null;
	}

	public Method[] getMethods(String name) {
		ArrayList<Method> list = new ArrayList<Method>();
		for(int i = 0; i < methods.length; i++){
			Method m = methods[i];
			if(m.getName().equals(name)) list.add(m);
		}
		return list.toArray(new Method[0]);
	}

}
