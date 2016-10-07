package ccl.vm.core.types;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.iface.exec.IExecuterFactory;
import ccl.vm.core.expr.FunctionExpression;
import ccl.vm.core.func.FunctionImpl;

public class FunctionType extends ExpressionType<IFunction<Object, Object>>{
	
public static final FunctionType INSTANCE = new FunctionType();
	
	public static IExecuterFactory factory;
	
	@Override
	public boolean check(IExpression<?> e) {
		return e.getType() == this;
	}
	
	@SuppressWarnings("unchecked")
	public IFunction<Object, Object> parse(Object o) throws CclException{
		if(o instanceof IFunction) return (IFunction<Object, Object>) o;
		return new FunctionImpl(factory, o + "");
	}
	
	@SuppressWarnings("unchecked")
	public FunctionExpression expr(Object o) throws CclException{
		return new FunctionExpression((IFunction<Object, Object>) o);
	}
	
}
