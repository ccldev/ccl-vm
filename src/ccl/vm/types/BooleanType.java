package ccl.vm.types;

import ccl.iface.IExpression;
import ccl.vm.expr.BooleanExpression;

public class BooleanType extends ExpressionType{

	public static final BooleanType INSTANCE = new BooleanType();

	@Override
	public Boolean parse(Object o) {
		return Boolean.parseBoolean("" + o);
	}

	@Override
	public IExpression expr(Object o) {
		return new BooleanExpression((boolean) o);
	}
	
}
