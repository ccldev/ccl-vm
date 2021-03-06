package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;

import static ccl.vm.Tools.raw;

public class AddParamFunction implements IFunction{

	private IFunction expression;

	public AddParamFunction(IFunction expression) {
		this.expression = expression;
	}
	
	@Override
	public IExpression invoke(
			IExpression... parameters) throws CclException {
		return new Expression(new CuttedFunction(raw(parameters[0].getValue()), expression));
	}

}
