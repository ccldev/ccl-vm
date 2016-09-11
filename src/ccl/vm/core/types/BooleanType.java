package ccl.vm.core.types;

import ccl.iface.IExpression;
import ccl.vm.core.expr.BooleanExpression;

public class BooleanType extends ExpressionType<Boolean>{

	public static final BooleanType INSTANCE = new BooleanType();

	@Override
	public boolean check(IExpression<?> e) {
		return e.getType() == this;
	}

	@Override
	public Boolean parse(Object o) {
		return Boolean.parseBoolean("" + o);
	}

	@Override
	public IExpression<Boolean> expr(Object o) {
		return new BooleanExpression((boolean) o);
	}
	
}
