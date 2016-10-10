package ccl.vm.types;

import ccl.iface.CclException;
import ccl.iface.IFunction;
import ccl.iface.exec.IExecuterFactory;
import ccl.vm.core.Expression;
import ccl.vm.func.FunctionImpl;

public class FunctionType extends ExpressionType{
	
public static final FunctionType INSTANCE = new FunctionType();
	
	public static IExecuterFactory factory;
	
	public IFunction parse(Object o) throws CclException{
		if(o instanceof IFunction) return (IFunction) o;
		return new FunctionImpl(factory, o + "");
	}
	
	public Expression expr(Object o) throws CclException{
		return new Expression((IFunction) o);
	}
	
}
