package ccl.vm.core.bridge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import ccl.vm.core.Expression;
import ccl.vm.err.NoSuchNativePropertyException;

public class Property {
	
	public static Expression<?> getNative(String name, Object o) throws NoSuchNativePropertyException{
		Class<?> c = o.getClass();
		Method[] methods = c.getMethods();
		ArrayList<Method> filter = new ArrayList<Method>();
		for(int i = 0; i < methods.length; i++){
			Method m = methods[i];
			if(m.getName().equals(name)) filter.add(m);
		}
		Field f = null;
		try {
			f = c.getField(name);
		} catch (NoSuchFieldException | SecurityException e) {}
		return new JProperty(o, filter.toArray(new Method[0]), f);
	}
	
}
