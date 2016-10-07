package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;
import ccl.vm.core.expr.ArrayExpression;

public class WhileFunctionImpl implements IFunction<Object, Object> {

	private IFunction<Object, Object> func;
	private Expression<? extends Object> condition;

	public WhileFunctionImpl(IFunction<Object, Object> func,
			Expression<? extends Object> condition) {
		this.func = func;
		this.condition = condition;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IExpression<? extends Object> invoke(
			IExpression<? extends Object>... parameters) throws CclException {
		Array a = new Array(0);
		while(condition.invoke().bool()){
			a.pushExpression((Expression<?>) func.invoke(parameters));
		}
		return new ArrayExpression(a);
	}

}
