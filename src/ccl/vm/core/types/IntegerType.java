package ccl.vm.core.types;

import ccl.iface.IExpression;
import ccl.vm.core.Undefined;
import ccl.vm.core.expr.IntegerExpression;

public class IntegerType extends ExpressionType<Long>{

	public static final IntegerType INSTANCE = new IntegerType();

	@Override
	public boolean check(IExpression<?> e) {
		if(e.getValue() instanceof Undefined) return true;
		return e.getType() == this || e.getType() == FloatType.INSTANCE;
	}

	@Override
	public Long parse(Object o) {
		if(o instanceof Number) return ((Number) o).longValue();
		return (long) Double.parseDouble(o + "");
	}

	@Override
	public IExpression<Long> expr(Object o) {
		return new IntegerExpression((long) o);
	}

}
