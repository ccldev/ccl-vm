package ccl.vm.core.bridge;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.Tools;
import ccl.vm.core.Expression;
import ccl.vm.err.InvokeException;

public class JInvocationHandler implements InvocationHandler{

	private IExpression<? extends Object> expression;

	public JInvocationHandler(IExpression<? extends Object> expression) {
		this.expression = expression;
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
			try{
				return Tools.asFunction((IExpression<Object>) e, o).invoke(wrapToExpressions(params));
			}catch(InvokeException ex){
				if(params.length <= 0) throw ex;
				return invoke(o, m, removeOne(params));
			}
		}
	}

	private Object[] removeOne(Object[] params) {
		if(params.length == 0) throw new RuntimeException("Array length is 0!");
		Object[] ret = new Object[params.length - 1];
		for(int i = 0; i < ret.length; i++){
			ret[i] = params[i];
		}
		return ret;
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
