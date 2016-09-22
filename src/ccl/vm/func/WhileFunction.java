package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;
import ccl.vm.core.expr.FunctionExpression;

public class WhileFunction implements IFunction<Object, Object> {

	private IFunction<Object, Object> expression;

	public WhileFunction(IFunction<Object, Object> expression) {
		this.expression = expression;
	}

	@Override
	public IExpression<? extends Object> invoke(
			IExpression<? extends Object>... parameters) throws CclException {
		return new FunctionExpression(new WhileFunctionImpl(expression, (Expression<? extends Object>) parameters[0]));
	}

}
