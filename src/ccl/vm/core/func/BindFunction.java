package ccl.vm.core.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.Tools;
import ccl.vm.core.expr.FunctionExpression;

public class BindFunction implements IFunction<Object, Object>{

	private IFunction<Object, Object> func;

	public BindFunction(IFunction<Object, Object> func) {
		this.func = func;
	}

	@Override
	public IExpression<? extends Object> invoke(
			final IExpression<Object>... pa) throws CclException {
		return new FunctionExpression(new IFunction<Object, Object>() {
			@Override
			public IExpression<? extends Object> invoke(IExpression<Object>... pb)
					throws CclException {
				return invokeBound(pa, pb);
			}
		});
	}

	protected IExpression<? extends Object> invokeBound(IExpression<Object>[] pa,
			IExpression<Object>[] pb) throws CclException {
		return func.invoke(Tools.link(pa,pb));
	}

}
