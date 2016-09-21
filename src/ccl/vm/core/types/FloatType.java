package ccl.vm.core.types;

import ccl.iface.IExpression;
import ccl.vm.core.Undefined;
import ccl.vm.core.expr.FloatExpression;

public class FloatType extends NumberType<Double>{

	public static final FloatType INSTANCE = new FloatType();

	@Override
	public boolean check(IExpression<?> e) {
		if(e.getValue() instanceof Undefined) return true;
		return e.getType() == this;
	}

	@Override
	public Double parse(Object o) {
		if(o instanceof Number) return ((Number) o).doubleValue();
		return Double.parseDouble(o + "");
	}

	@Override
	public IExpression<Double> expr(Object o) {
		return new FloatExpression((double) o);
	}

}
