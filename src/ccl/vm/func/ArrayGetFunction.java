package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;

public class ArrayGetFunction extends Expression {

	private Expression expr;

	public ArrayGetFunction(Expression expr) {
		this.expr = expr;
	}
	
	@Override
	public IExpression invoke(IExpression... parameters)
			throws CclException {
		return ((Array) expr.getValue()).getExpression((int) Double.parseDouble(parameters[0].getValue().toString()));
	}

}
