package ccl.vm.core.bridge;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import ccl.iface.IExpression;
import ccl.vm.core.Expression;
import ccl.vm.err.InvokeException;

public class JBridgeTool {

	private static final int ERROR = 1;
	private static final int OK = 0;

	public static IExpression<Object> invoke(Method[] methods, Object o, IExpression<? extends Object>[] params) throws InvokeException {
		mainloop: 
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			Class<?>[] types = m.getParameterTypes();
			Object[] array = new Object[params.length];
			for (int k = 0; k < array.length; k++) {
				Object param = params[k].getValue();
				Class<?> type = types[k];
				if(putToArray(array, k, type, param) == ERROR) continue mainloop;
			}
			try {
				return invokeSingle(m, o, array);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace(System.out);
				continue mainloop;
			}
		}
		throw new InvokeException("Unable to invoke one of " + Arrays.toString(methods) + " with arguments " + Arrays.toString(params));
	}
	public static IExpression<Object> invoke(Constructor<?>[] methods, IExpression<? extends Object>[] parameters) throws InvokeException {
		mainloop:
		for (int i = 0; i < methods.length; i++) {
			Constructor<?> m = methods[i];
			Class<?>[] types = m.getParameterTypes();
			Object[] array = new Object[parameters.length];
			for (int k = 0; k < array.length; k++) {
				Object param = parameters[k].getValue();
				Class<?> type = types[k];
				if(putToArray(array, k, type, param) == ERROR) continue mainloop;
			}
			try {
				return invokeSingle(m, array);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | InstantiationException e) {
				continue mainloop;
			}
		}
		throw new InvokeException("Unable to invoke one of " + Arrays.toString(methods) + " with arguments " + Arrays.toString(parameters));
	}
	
	private static IExpression<Object> invokeSingle(Constructor<?> m, Object[] parameters) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return new Expression<Object>(m.newInstance((Object[]) parameters));
	}
	
	public static int putToArray(Object[] array, int index, Class<?> type, Object param){
		if (type.isPrimitive()) {
			Class<?> paramType = param.getClass();
			try {
				array[index] = paramType
						.getMethod(type.getName() + "Value").invoke(
								param);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				return ERROR;
			}
		} else {
			array[index] = param;
		}
		return OK;
	}
	
	public static IExpression<Object> invokeSingle(Method m, Object o, Object[] parameters) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Object obj = m.invoke(o, (Object[]) parameters);
		if(m.getReturnType().isPrimitive() || m.getReturnType().isArray()){
			return (IExpression<Object>) JPrimitiveWrapper.wrap(obj);
		}
		return new Expression<Object>(obj);
	}

	public static Method[] filter(Method[] methods, int paramCount) {
		ArrayList<Method> ok = new ArrayList<Method>();
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			if (m.getParameterCount() == paramCount) {
				ok.add(m);
			}
		}
		return ok.toArray(new Method[0]);
	}
	
	public static Constructor<?>[] filter(Constructor<?>[] constructors, int paramCount) {
		ArrayList<Constructor<?>> ok = new ArrayList<Constructor<?>>();
		for (int i = 0; i < constructors.length; i++) {
			Constructor<?> m = constructors[i];
			if (m.getParameterCount() == paramCount) {
				ok.add(m);
			}
		}
		return ok.toArray(new Constructor<?>[0]);
	}
	public static IExpression<Object> createInterfaceInstance(Class<?> iface,
			IExpression<? extends Object> expression) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clss = JInterfaceBuilder.makeNormalClass(iface);
		Constructor<?> constructor = clss.getConstructor(InvocationHandler.class);
		return new Expression<Object>(constructor.newInstance(new JInvocationHandler(expression)));
	}

}
