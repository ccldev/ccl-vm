package ccl.vm.core.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.expr.FunctionExpression;

public class AddParamFunction implements IFunction<Object, Object>{

	private IFunction<Object, Object> expression;

	public AddParamFunction(IFunction<Object, Object> expression) {
		this.expression = expression;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IExpression<? extends Object> invoke(
			IExpression<? extends Object>... parameters) throws CclException {
		return new FunctionExpression(new CuttedFunction((Double) parameters[0].getValue(), expression));
	}

}
