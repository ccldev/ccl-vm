package ccl.vm.types;

import ccl.iface.IExpression;
import ccl.vm.expr.IntegerExpression;

public class IntegerType extends ExpressionType{

	public static final IntegerType INSTANCE = new IntegerType();

	@Override
	public Long parse(Object o) {
		if(o instanceof Number) return ((Number) o).longValue();
		return (long) Double.parseDouble(o + "");
	}

	@Override
	public IExpression expr(Object o) {
		return new IntegerExpression((long) o);
	}

}
