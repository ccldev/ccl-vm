package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.expr.FunctionExpression;

public class LinkFunction implements IFunction{

	private IFunction expression;

	public LinkFunction(IFunction expression) {
		this.expression = expression;
	}

	@Override
	public IExpression invoke(IExpression... parameters) throws CclException {
		return new FunctionExpression(new LinkedFunction(expression, parameters));
	}

}
