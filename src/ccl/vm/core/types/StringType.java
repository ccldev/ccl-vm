package ccl.vm.core.types;

import ccl.iface.IExpression;
import ccl.vm.core.Expression;
import ccl.vm.core.Undefined;

public class StringType extends ExpressionType<String>{

	public static final StringType INSTANCE = new StringType();

	@Override
	public boolean check(IExpression<?> e) {
		if(e.getValue() instanceof Undefined) return true;
		return e.getType() instanceof StringType;
	}

	@Override
	public String parse(Object o) {
		return o + "";
	}

	@Override
	public IExpression<String> expr(Object o) {
		return Expression.make((String) o);
	}

}
