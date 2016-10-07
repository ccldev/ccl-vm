package ccl.vm.core.bridge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import ccl.iface.CclException;
import ccl.vm.core.Expression;
import ccl.vm.core.expr.ErrorExpression;

public class Property {
	
	public static Expression<?> getNative(String name, Object o){
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
			Object val = f.get(o);
			if(f.getType().isPrimitive() || f.getType().isArray()){
				return JPrimitiveWrapper.wrap(val);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {}
		try {
			return new JProperty(o, filter.toArray(new Method[0]), f);
		} catch (CclException e) {
			return new ErrorExpression(e);
		}
	}
	
}
