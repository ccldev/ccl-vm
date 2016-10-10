package ccl.vm.bridge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;
import ccl.vm.core.Undefined;
import ccl.vm.expr.FunctionExpression;

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
		
		setProperty("_set_", new FunctionExpression(new IFunction(){

			@Override
			public IExpression invoke(
					IExpression... parameters)
					throws CclException {
				try {
					field.set(object, parameters[0].getValue());
					return new Expression(new Undefined());
				} catch (Exception e) {
					throw new CclException(e);
				}
			}
			
		}));
		
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

}
