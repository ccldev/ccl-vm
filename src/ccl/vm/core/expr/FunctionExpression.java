package ccl.vm.core.expr;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;
import ccl.vm.core.func.BindFunction;

public class FunctionExpression<A,B> extends Expression<IFunction<A, B>> implements IFunction<A, B> {
	
	private IFunction<A, B> func;

	public FunctionExpression(IFunction<A, B> func){
		super(func);
		this.func = func;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IExpression<B> invoke(IExpression<A>... parameters)
			throws CclException {
		return func.invoke(parameters);
	}
	
	public IExpression<?> getProperty(String prop) throws CclException{
		if(prop.equals("bind")) return new FunctionExpression<>(new BindFunction((IFunction<Object, Object>) this));
		return super.getProperty(prop);
	}
	
}
