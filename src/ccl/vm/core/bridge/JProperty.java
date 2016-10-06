package ccl.vm.core.bridge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;
import ccl.vm.core.expr.FunctionExpression;
import ccl.vm.core.func.BindFunction;
import ccl.vm.err.InvokeException;
import ccl.vm.err.NoSuchNativePropertyException;

public class JProperty extends Expression<Object> implements IFunction<Object, Object>{

	private Field field;
	private Method[] methods;
	private Object object;

	public JProperty(Object o, Method[] array, Field f) throws NoSuchNativePropertyException {
		if(array.length == 0 && f == null) throw new NoSuchNativePropertyException();
		this.field = f;
		this.methods = array;
		this.object = o;
		this.value = getValue();
		if(array.length != 0){
			initAsFunction();
		}
	}
	
	private void initAsFunction() {
		setProperty("bind", new FunctionExpression(new BindFunction(this)));
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
	public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters) throws InvokeException {
		Method[] ok = JBridgeTool.filter(methods, parameters.length);
		return JBridgeTool.invoke(ok, object, parameters);
	}

}
