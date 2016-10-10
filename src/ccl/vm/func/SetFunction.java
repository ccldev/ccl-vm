package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;

public class SetFunction implements IFunction{

	private Expression expr;
	
	public SetFunction(Expression expr) {
		this.expr = expr;
	}

	@Override
	public IExpression invoke(IExpression... parameters) throws CclException {
		try {
			expr.set((Expression) parameters[0]);
		} catch (Exception e) {
			return Expression.err(e);
		}
		return expr;
	}

}
