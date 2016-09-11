package ccl.vm.core.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;
import ccl.vm.core.FunctionMarker;
import ccl.vm.core.Index;
import ccl.vm.core.expr.ArrayExpression;
import ccl.vm.core.expr.IndexExpression;

public class GetIndexFunction extends Expression<FunctionMarker<?,?>> implements IFunction<Number, Index> {

	private ArrayExpression arrayExpr;

	public GetIndexFunction(ArrayExpression arrayExpr) {
		this.arrayExpr = arrayExpr;
		this.value = new FunctionMarker<>(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IExpression<Index> invoke(IExpression<Number>... parameters)
			throws CclException {
		return new IndexExpression(new Index(arrayExpr, parameters[0].getValue()));
	}

}
