package ccl.vm.bridge;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import ccl.iface.IExpression;
import ccl.vm.core.Expression;
import ccl.vm.expr.ErrorExpression;

public class JBridgeTool {

	private static final int ERROR = 1;
	private static final int OK = 0;

	public static IExpression invoke(Method[] methods, Object o, IExpression[] params) {
		List<Throwable> errors = new ArrayList<Throwable>();
		
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
				if(e instanceof InvocationTargetException){
					errors.add(e);
				}
				continue mainloop;
			}
		}
		return new ErrorExpression(errors);
	}
	public static IExpression invoke(Constructor<?>[] methods, IExpression[] parameters) {
		List<Throwable> errors = new ArrayList<Throwable>();
		
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
				if(e instanceof InvocationTargetException){
					errors.add(e.getCause());
				}
				continue mainloop;
			}
		}
		return new ErrorExpression(errors);
	}
	
	private static IExpression invokeSingle(Constructor<?> m, Object[] parameters) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return new Expression(m.newInstance((Object[]) parameters));
	}
	
	public static int putToArray(Object[] array, int index, Class<?> type, Object param){
		if (type.isPrimitive()) {
			Class<?> paramType = null;
			if(param != null){
				paramType = param.getClass();
			}else{
				paramType = Object.class;
			}
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
	
	public static IExpression invokeSingle(Method m, Object o, Object[] parameters) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Object obj = m.invoke(o, (Object[]) parameters);
		if(m.getReturnType().isPrimitive() || m.getReturnType().isArray()){
			return (IExpression) JPrimitiveWrapper.wrap(obj);
		}
		return new Expression(obj);
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
	public static IExpression createInterfaceInstance(Class<?> iface,
			IExpression expression) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clss = JInterfaceBuilder.makeNormalClass(iface);
		Constructor<?> constructor = clss.getConstructor(InvocationHandler.class);
		return new Expression(constructor.newInstance(new JInvocationHandler(iface, expression)));
	}

}
