package ccl.vm.core.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.vm.core.Expression;
import ccl.vm.core.FunctionMarker;
import ccl.vm.core.Index;
import ccl.vm.core.Undefined;
import ccl.vm.core.expr.ArrayExpression;
import ccl.vm.core.expr.IndexExpression;

public class PushFunction extends Expression<FunctionMarker<?,?>> {
	
	private ArrayExpression expr;

	public PushFunction(ArrayExpression expr) {
		this.expr = expr;
	}

	@Override
	public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters)
			throws CclException {
		expr.getValue().pushExpression((Expression<?>) parameters[0]);
		if(parameters.length >= 2){
			expr.setProperty(parameters[1].getValue().toString(), (Expression<?>) parameters[0]);
		}
		return new Expression<>(new Undefined());
	}
	
}
