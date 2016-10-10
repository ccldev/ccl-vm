package ccl.vm.core.bridge;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.Tools;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;
import ccl.vm.core.expr.ArrayExpression;
import ccl.vm.core.expr.StringExpression;

public class JInvocationHandler implements InvocationHandler{

	private IExpression<? extends Object> expression;

	public JInvocationHandler(Class<?> iface, IExpression<? extends Object> expression) {
		this.expression = expression;
		try {
			Object type = expression.getProperty("type").getValue();
			if(type.equals("function")){
				Method[] ms = iface.getMethods();
				if(ms.length == 1){
					this.expression = wrapToArray(ms[0].getName(), expression);
				}
			}
		} catch (CclException e) {}
	}

	@SuppressWarnings("unchecked")
	private IExpression<? extends Object> wrapToArray(String name,
			IExpression<? extends Object> func) throws CclException {
		ArrayExpression expr = new ArrayExpression(new Array(0));
		expr.getProperty("push").invoke(func, new StringExpression(name));
		return expr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(Object o, Method m, Object[] params)
			throws Throwable {
		Object[] parameters = createParamArray(params);
		IExpression<?> e = findEqualMethod(m);
		if(e instanceof IFunction){
			return ((IFunction<Object, Object>) e).invoke(wrapToExpressions(parameters));
		}else{
			return Tools.asFunction((IExpression<Object>) e, o).invoke(wrapToExpressions(params));
		}
	}

	private Object[] createParamArray(Object[] params) {
		if(params == null) return new Object[0];
		return params;
	}

	@SuppressWarnings("unchecked")
	private IExpression<Object>[] wrapToExpressions(Object[] params) {
		Expression<Object>[] expressions = new Expression[params.length];
		for(int i = 0; i < expressions.length; i++){
			expressions[i] = new Expression<Object>(params[0]);
		}
		return expressions;
	}

	private IExpression<?> findEqualMethod(Method m) throws CclException {
		return expression.getProperty(m.getName());
	}

}
