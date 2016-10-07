package ccl.vm.core.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;
import ccl.vm.core.expr.ArrayExpression;

public class ArrayFunction implements IFunction<Object, Object> {

	private Expression<?> expression;

	public ArrayFunction(Expression<?> e) {
		this.expression = e;
	}

	@Override
	public IExpression<? extends Object> invoke(
			@SuppressWarnings("unchecked") IExpression<? extends Object>... parameters) throws CclException {
		Array a = new Array(1);
		a.set(0, expression);
		ArrayExpression e = new ArrayExpression(a);
		e.setProperty(parameters[0].getValue().toString(), expression);
		return e;
	}

}
