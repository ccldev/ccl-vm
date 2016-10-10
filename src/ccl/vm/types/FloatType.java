package ccl.vm.types;

import ccl.iface.IExpression;
import ccl.vm.expr.FloatExpression;

public class FloatType extends ExpressionType{

	public static final FloatType INSTANCE = new FloatType();

	@Override
	public Double parse(Object o) {
		if(o instanceof Number) return ((Number) o).doubleValue();
		return Double.parseDouble(o + "");
	}

	@Override
	public IExpression expr(Object o) {
		return new FloatExpression((double) o);
	}

}
