package ccl.vm.core.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;
import ccl.vm.core.expr.ArrayExpression;

public class LinkedFunction implements IFunction<Object, Object> {

	private IFunction<Object, Object> func;
	private IExpression<? extends Object>[] params;

	public LinkedFunction(IFunction<Object, Object> expression,
			IExpression<? extends Object>[] parameters) {
		this.func = expression;
		this.params = parameters;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IExpression<? extends Object> invoke(
			IExpression<? extends Object>... parameters) throws CclException {
		Array ret = new Array(0);
		Object first = func.invoke(parameters);
		ret.pushExpression(first instanceof Expression ? (Expression<?>) first : new Expression<>(first));
		for(int i = 0; i < params.length; i++){
			IFunction<Object, Object> f = (IFunction) params[i];
			Object val = f.invoke(parameters);
			ret.pushExpression(val instanceof Expression ? (Expression<?>) val : new Expression<>(val));
		}
		return new ArrayExpression(ret);
	}

}
