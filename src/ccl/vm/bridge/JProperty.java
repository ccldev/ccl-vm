package ccl.vm.bridge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.vm.core.Expression;

public class JProperty extends Expression {

	private Field field;
	private Method[] methods;
	private Object object;

	public JProperty(String name, Object o, Method[] array, Field f) throws CclException {
		if(array.length == 0 && f == null) throw new CclException("No such native property! " + name);
		this.field = f;
		this.methods = array;
		this.object = o;
		this.value = getValue();		
	}

	public Object getValue(){
		if(field == null) return value = methods;
		try {
			return value = field.get(object);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public IExpression invoke(IExpression... parameters){
		Method[] ok = JBridgeTool.filter(methods, parameters.length);
		return JBridgeTool.invoke(ok, object, parameters);
	}
	
	public void set(Expression newVal) throws IllegalArgumentException, IllegalAccessException {
		field.set(object, newVal.getValue());
	}

}
