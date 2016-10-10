package ccl.vm.bridge;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;
import ccl.vm.expr.StringExpression;

public class JInvocationHandler implements InvocationHandler{

	private IExpression expression;

	public JInvocationHandler(Class<?> iface, IExpression expression) {
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
	
	private IExpression wrapToArray(String name,
			IExpression func) throws CclException {
		Expression expr = new Expression(new Array(0));
		expr.getProperty("push").invoke(func, new StringExpression(name));
		return expr;
	}
	
	@Override
	public Object invoke(Object o, Method m, Object[] params)
			throws Throwable {
		Object[] parameters = createParamArray(params);
		IExpression e = findEqualMethod(m);
		if(e instanceof IFunction){
			return e.invoke(wrapToExpressions(parameters));
		}else{
			return e.invoke(wrapToExpressions(params));
		}
	}

	private Object[] createParamArray(Object[] params) {
		if(params == null) return new Object[0];
		return params;
	}
	
	private IExpression[] wrapToExpressions(Object[] params) {
		Expression[] expressions = new Expression[params.length];
		for(int i = 0; i < expressions.length; i++){
			expressions[i] = new Expression(params[0]);
		}
		return expressions;
	}

	private IExpression findEqualMethod(Method m) throws CclException {
		return expression.getProperty(m.getName());
	}

}
