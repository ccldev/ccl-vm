package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;

public class PropertyFunction implements IFunction{

	private Expression expr;

	public PropertyFunction(Expression expr) {
		this.expr = expr;
	}

	@Override
	public IExpression invoke(IExpression... parameters) throws CclException {
		return expr.getProperty(parameters[0].getValue().toString());
	}

}
