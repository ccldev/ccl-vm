package ccl.vm.expr;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;

public class FunctionExpression extends Expression {
	
	private IFunction func;

	public FunctionExpression(IFunction func){
		super(func);
		this.func = func;
	}
	
	@Override
	public IExpression invoke(IExpression... parameters)
			throws CclException {
		return func.invoke(parameters);
	}
	
}
