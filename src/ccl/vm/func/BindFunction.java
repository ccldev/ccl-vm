package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.Tools;
import ccl.vm.expr.FunctionExpression;

public class BindFunction implements IFunction{

	private IFunction func;

	public BindFunction(IFunction func) {
		this.func = func;
	}
	
	@Override
	public IExpression invoke(
			final IExpression... pa) throws CclException {
		return new FunctionExpression(new IFunction() {
			@Override
			public IExpression invoke(IExpression... pb)
					throws CclException {
				return invokeBound(pa, pb);
			}
		});
	}

	protected IExpression invokeBound(IExpression[] pa,
			IExpression[] pb) throws CclException {
		return func.invoke(Tools.link(pa,pb));
	}

}
