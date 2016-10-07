package ccl.vm.core.types;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.vm.core.ErrorMarker;
import ccl.vm.core.Expression;
import ccl.vm.core.Undefined;
import ccl.vm.core.bridge.JClass;
import ccl.vm.core.bridge.JClassExpression;

public class NativeType extends ExpressionType<Object>{
	
	public static final NativeType INSTANCE = new NativeType();
	
	@Override
	public boolean check(IExpression<?> e) {
		if(e.getValue() instanceof Undefined) return true;
		return e.getType() == this;
	}
	
	public Object parse(Object o) throws CclException{
		try {
			return new JClass(o + "");
		} catch (Exception e) {
			return new ErrorMarker(e);
		}
	}
	
	public Expression expr(Object o) throws CclException{
		return new JClassExpression((JClass) o);
	}
	
}
