package ccl.vm.types;

import ccl.iface.IExpression;
import ccl.vm.expr.StringExpression;

public class StringType extends ExpressionType{

	public static final StringType INSTANCE = new StringType();

	@Override
	public String parse(Object o) {
		return o + "";
	}

	@Override
	public IExpression expr(Object o) {
		return new StringExpression(String.valueOf(o));
	}

}
