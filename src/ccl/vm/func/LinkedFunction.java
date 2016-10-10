package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;

public class LinkedFunction implements IFunction {

	private IFunction func;
	private IExpression[] params;

	public LinkedFunction(IFunction expression,
			IExpression[] parameters) {
		this.func = expression;
		this.params = parameters;
	}
	
	@Override
	public IExpression invoke(
			IExpression... parameters) throws CclException {
		Array ret = new Array(0);
		Object first = func.invoke(parameters);
		ret.pushExpression(first instanceof Expression ? (Expression) first : new Expression(first));
		for(int i = 0; i < params.length; i++){
			IFunction f = (IFunction) params[i];
			Object val = f.invoke(parameters);
			ret.pushExpression(val instanceof Expression ? (Expression) val : new Expression(val));
		}
		return new Expression(ret);
	}

}
