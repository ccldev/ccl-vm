package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;
import ccl.vm.core.Undefined;

public class PushFunction extends Expression {
	
	private Expression expr;

	public PushFunction(Expression expr) {
		this.expr = expr;
	}
	
	@Override
	public IExpression invoke(IExpression... parameters)
			throws CclException {
		((Array) expr.getValue()).pushExpression((Expression) parameters[0]);
		if(parameters.length >= 2){
			expr.setProperty(parameters[1].getValue().toString(), (Expression) parameters[0]);
		}
		return new Expression(new Undefined());
	}
	
}
