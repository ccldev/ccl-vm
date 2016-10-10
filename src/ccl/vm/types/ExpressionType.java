package ccl.vm.types;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IType;
import ccl.vm.core.Expression;

public class ExpressionType implements IType{

	public static ExpressionType INSTANCE = new ExpressionType();
	
	protected ExpressionType(){}
	
	@Override
	public Object parse(Object o) throws CclException{
		return (Object) o;
	}
	
	@Override
	public IExpression expr(Object o) throws CclException{
		if(o instanceof IExpression) return (IExpression) o;
		return new Expression(o);
	}

}
