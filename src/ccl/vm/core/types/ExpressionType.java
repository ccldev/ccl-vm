package ccl.vm.core.types;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IType;
import ccl.vm.core.Expression;
import ccl.vm.core.bridge.JProperty;

public class ExpressionType<T> implements IType<T>{

	public static ExpressionType<Object> INSTANCE = new ExpressionType<Object>();
	
	protected ExpressionType(){}

	@Override
	public boolean check(IExpression<?> e) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T parse(Object o) throws CclException{
		return (T) o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IExpression<T> expr(Object o) throws CclException{
		if(o instanceof IExpression) return (IExpression<T>) o;
		return new Expression<T>((T) o);
	}

}
