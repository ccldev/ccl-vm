package ccl.vm.core.bridge;

import ccl.vm.core.Expression;
import ccl.vm.core.Undefined;
import ccl.vm.core.expr.BooleanExpression;
import ccl.vm.core.expr.FloatExpression;
import ccl.vm.core.expr.IntegerExpression;
import ccl.vm.core.expr.StringExpression;

public class JPrimitiveWrapper {

	public static Expression<?> wrap(Object val) {
		if(val == null) return new Expression<>(new Undefined());
		switch(val.getClass().getName()){
		case "java.lang.Integer":
		case "java.lang.Long":
		case "java.lang.Short":
		case "java.lang.Byte":
			return new IntegerExpression(((Number) val).longValue());
		case "java.lang.Double":
		case "java.lang.Float":
			return new FloatExpression(((Number) val).doubleValue());
		case "java.lang.Boolean":
			return new BooleanExpression((Boolean) val);
		case "java.lang.Character":
			return new StringExpression(val + "");
		default: throw new RuntimeException("Unexpected type: " + val.getClass());
		}
	}

}
