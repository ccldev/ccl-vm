package ccl.vm.core.expr;

import ccl.iface.IExpression;
import ccl.vm.core.Expression;
import ccl.vm.core.Index;

public class IndexExpression extends Expression<Index> {

	public IndexExpression(Index o) {
		super(o);
	}
	
	public String toString(){
		return "D:[" + value.getIndex() + "," + value.getValue() + "]";
	}
	
	public IExpression<? extends Object> invoke(IExpression<? extends Object>... params){
		return new Expression<Object>(value.getValue());
	}

}
