package ccl.vm.core.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.vm.core.Expression;
import ccl.vm.core.FunctionMarker;
import ccl.vm.core.Undefined;
import ccl.vm.core.expr.ArrayExpression;

public class ArrayGetFunction extends Expression<FunctionMarker<?,?>> {

	private ArrayExpression expr;

	public ArrayGetFunction(ArrayExpression expr) {
		this.expr = expr;
	}
	
	@Override
	public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters)
			throws CclException {
		return expr.getValue().getExpression((int) Double.parseDouble(parameters[0].getValue().toString()));
	}

}
