package ccl.vm.core.bridge;

import ccl.vm.core.Array;
import ccl.vm.core.Expression;
import ccl.vm.core.Undefined;
import ccl.vm.core.expr.ArrayExpression;
import ccl.vm.core.expr.BooleanExpression;
import ccl.vm.core.expr.FloatExpression;
import ccl.vm.core.expr.IntegerExpression;
import ccl.vm.core.expr.StringExpression;

public class JPrimitiveWrapper {

	public static Expression<?> wrap(Object val) {
		if(val == null) return new Expression<>(new Undefined());
		
		if(val.getClass().isArray()){
			Class<?> c = val.getClass();
			if(c == boolean[].class) return array(Array.clone((boolean[]) val));
			if(c == byte[].class) return array(Array.clone((byte[]) val));
			if(c == char[].class) return array(Array.clone((char[]) val));
			if(c == double[].class) return array(Array.clone((double[]) val));
			if(c == float[].class) return array(Array.clone((float[]) val));
			if(c == int[].class) return array(Array.clone((int[]) val));
			if(c == long[].class) return array(Array.clone((long[]) val));
			if(c == short[].class) return array(Array.clone((short[]) val));
			return array(Array.clone((Object[]) val));
		}
		
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

	private static Expression<?> array(Array clone) {
		return new ArrayExpression(clone);
	}

}
