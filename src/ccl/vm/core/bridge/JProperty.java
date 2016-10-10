package ccl.vm.core.bridge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;
import ccl.vm.core.Undefined;
import ccl.vm.core.expr.FunctionExpression;

public class JProperty extends Expression<Object> implements IFunction<Object, Object>{

	private Field field;
	private Method[] methods;
	private Object object;

	public JProperty(Object o, Method[] array, Field f) throws CclException {
		if(array.length == 0 && f == null) throw new CclException("No such native property!");
		this.field = f;
		this.methods = array;
		this.object = o;
		this.value = getValue();
		
		setProperty("_set_", new FunctionExpression(new IFunction<Object, Object>(){

			@Override
			public IExpression<? extends Object> invoke(
					IExpression<? extends Object>... parameters)
					throws CclException {
				try {
					field.set(object, parameters[0].getValue());
					return new Expression<>(new Undefined());
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

	@SuppressWarnings("unchecked")
	@Override
	public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters){
		Method[] ok = JBridgeTool.filter(methods, parameters.length);
		return JBridgeTool.invoke(ok, object, parameters);
	}

}
