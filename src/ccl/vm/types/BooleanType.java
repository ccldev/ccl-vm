package ccl.vm.types;

import ccl.iface.IExpression;
import ccl.vm.core.Expression;

public class BooleanType extends ExpressionType{

	public static final BooleanType INSTANCE = new BooleanType();

	@Override
	public Boolean parse(Object o) {
		return Boolean.parseBoolean("" + o);
	}

	@Override
	public IExpression expr(Object o) {
		return new Expression((boolean) o);
	}
	
}
