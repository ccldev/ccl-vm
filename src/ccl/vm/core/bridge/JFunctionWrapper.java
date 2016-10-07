package ccl.vm.core.bridge;

import java.lang.reflect.Method;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;

public class JFunctionWrapper implements IFunction<Object, Object> {

	private IExpression<Object> expr;
	private Object object;

	public JFunctionWrapper(IExpression<Object> expr, Object o) {
		this.expr = expr;
		this.object = o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IExpression<? extends Object> invoke(IExpression<? extends Object>... params)
			throws CclException {
		return JBridgeTool.invoke(JBridgeTool.filter((Method[]) expr.getValue(), params.length), object, params);
	}

}
