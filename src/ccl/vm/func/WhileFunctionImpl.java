package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;

public class WhileFunctionImpl implements IFunction {

	private IFunction func;
	private Expression condition;

	public WhileFunctionImpl(IFunction func,
			Expression condition) {
		this.func = func;
		this.condition = condition;
	}
	
	@Override
	public IExpression invoke(
			IExpression... parameters) throws CclException {
		Array a = new Array(0);
		while((Boolean) condition.invoke().getValue()){
			a.pushExpression((Expression) func.invoke(parameters));
		}
		return new Expression(a);
	}

}
