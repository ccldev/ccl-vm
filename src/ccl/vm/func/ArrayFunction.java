package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;

public class ArrayFunction implements IFunction {

	private Expression expression;

	public ArrayFunction(Expression e) {
		this.expression = e;
	}

	@Override
	public IExpression invoke(
			IExpression... parameters) throws CclException {
		Array a = new Array(1);
		a.set(0, expression);
		Expression e = new Expression(a);
		e.setProperty(parameters[0].getValue().toString(), expression);
		return e;
	}

}
