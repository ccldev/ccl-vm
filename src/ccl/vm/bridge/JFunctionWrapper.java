package ccl.vm.bridge;

import java.lang.reflect.Method;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;

public class JFunctionWrapper implements IFunction {

	private IExpression expr;
	private Object object;

	public JFunctionWrapper(IExpression expr, Object o) {
		this.expr = expr;
		this.object = o;
	}

	@Override
	public IExpression invoke(IExpression... params)
			throws CclException {
		return JBridgeTool.invoke(JBridgeTool.filter((Method[]) expr.getValue(), params.length), object, params);
	}

}
