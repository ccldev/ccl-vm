package ccl.vm.types;

import ccl.iface.IExpression;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;

public class ArrayType extends ExpressionType{

	public static final ArrayType INSTANCE = new ArrayType();
	
	protected ArrayType(){}

	@Override
	public Array parse(Object o) {
		if(o instanceof Object[]) return Array.clone((Object[]) o);
		if(o instanceof boolean[]) return Array.clone((boolean[]) o);
		if(o instanceof char[]) return Array.clone((char[]) o);
		if(o instanceof byte[]) return Array.clone((byte[]) o);
		if(o instanceof short[]) return Array.clone((short[]) o);
		if(o instanceof int[]) return Array.clone((int[]) o);
		if(o instanceof long[]) return Array.clone((long[]) o);
		if(o instanceof float[]) return Array.clone((float[]) o);
		if(o instanceof double[]) return Array.clone((double[]) o);
		if(o instanceof Array) return (Array) o;
		return new Array(Integer.parseInt(o + ""));
	}

	@Override
	public IExpression expr(Object o) {
		return new Expression((Array) o);
	}
	
}
