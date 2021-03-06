package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;

public class LinkFunction implements IFunction{

	private IFunction expression;

	public LinkFunction(IFunction expression) {
		this.expression = expression;
	}

	@Override
	public IExpression invoke(IExpression... parameters) throws CclException {
		return new Expression(new LinkedFunction(expression, parameters));
	}

}
