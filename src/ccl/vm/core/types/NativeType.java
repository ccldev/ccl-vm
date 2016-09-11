package ccl.vm.core.types;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.vm.core.Expression;
import ccl.vm.core.Undefined;
import ccl.vm.core.bridge.JClass;
import ccl.vm.core.bridge.JClassExpression;
import ccl.vm.core.bridge.JPackage;
import ccl.vm.core.bridge.JPackageExpression;

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
			System.out.println(e);
			JPackageExpression.initBasePackages();
			return JPackage.find(o + "");
		}
	}
	
	public Expression expr(Object o) throws CclException{
		if(o instanceof JClass) return new JClassExpression((JClass) o);
		if(o instanceof JPackage) return new JPackageExpression((JPackage) o);
		throw new CclException("Unknown object type: " + o);
	}
	
}
