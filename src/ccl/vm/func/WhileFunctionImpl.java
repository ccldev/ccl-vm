package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;

public class WhileFunctionImpl implements IFunction<Object, Object> {

	private IFunction<Object, Object> func;
	private Expression<? extends Object> condition;

	public WhileFunctionImpl(IFunction<Object, Object> func,
			Expression<? extends Object> condition) {
		this.func = func;
		this.condition = condition;
	}

	@Override
	public IExpression<? extends Object> invoke(
			IExpression<? extends Object>... parameters) throws CclException {
		throw new RuntimeException("NI");
	}

}
