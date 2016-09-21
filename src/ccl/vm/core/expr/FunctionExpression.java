package ccl.vm.core.expr;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;
import ccl.vm.core.func.BindFunction;

public class FunctionExpression extends Expression<IFunction<Object, Object>> {
	
	private IFunction<Object, Object> func;

	public FunctionExpression(IFunction<Object, Object> func){
		super(func);
		this.func = func;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IExpression<? extends Object> invoke(IExpression<Object>... parameters)
			throws CclException {
		return func.invoke(parameters);
	}
	
	public IExpression<?> getProperty(String prop) throws CclException{
		if(prop.equals("bind")) return new FunctionExpression(new BindFunction((IFunction<Object, Object>) this));
		return super.getProperty(prop);
	}
	
}
