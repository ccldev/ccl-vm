package ccl.vm.core.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.expr.FunctionExpression;

public class LinkFunction implements IFunction<Object, Object>{

	private IFunction<Object, Object> expression;

	public LinkFunction(IFunction<Object, Object> expression) {
		this.expression = expression;
	}

	@Override
	public IExpression<? extends Object> invoke(
			@SuppressWarnings("unchecked") IExpression<? extends Object>... parameters) throws CclException {
		return new FunctionExpression(new LinkedFunction(expression, parameters));
	}

}
