package ccl.vm.core.types;

import ccl.iface.IExpression;
import ccl.vm.core.Index;
import ccl.vm.core.Undefined;
import ccl.vm.core.expr.IndexExpression;

public class IndexType extends ExpressionType<Index>{

	public static IndexType INSTANCE = new IndexType();

	@Override
	public boolean check(IExpression<?> e) {
		if(e.getValue() instanceof Undefined) return true;
		return e.getType() == this;
	}

	@Override
	public Index parse(Object o) {
		return (Index) o;
	}

	@Override
	public IExpression<Index> expr(Object o) {
		return new IndexExpression((Index) o);
	}

}
