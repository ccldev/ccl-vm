package ccl.vm.std;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.expr.IntegerExpression;

public class IntegerParser implements IFunction {

	@Override
	public IExpression invoke(
			IExpression... parameters) throws CclException {
		double res =  Double.parseDouble(String.valueOf(parameters[0].getValue()));
		return new IntegerExpression((long) res);
	}

}
