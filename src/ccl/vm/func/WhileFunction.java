package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;

public class WhileFunction implements IFunction {

	private IFunction expression;

	public WhileFunction(IFunction expression) {
		this.expression = expression;
	}
	
	@Override
	public IExpression invoke(
			IExpression... parameters) throws CclException {
		return new Expression(new WhileFunctionImpl(expression, (Expression) parameters[0]));
	}

}
