package ccl.vm.types;

import ccl.iface.CclException;
import ccl.vm.bridge.JClass;
import ccl.vm.bridge.JClassExpression;
import ccl.vm.core.ErrorMarker;
import ccl.vm.core.Expression;

public class NativeType extends ExpressionType{
	
	public static final NativeType INSTANCE = new NativeType();
	
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
