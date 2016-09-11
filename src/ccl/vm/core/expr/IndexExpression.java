package ccl.vm.core.expr;

import ccl.vm.core.Expression;
import ccl.vm.core.Index;

public class IndexExpression extends Expression<Index> {

	public IndexExpression(Index o) {
		super(o);
	}
	
	public String toString(){
		return "D:[" + value.getIndex() + "," + value.getValue() + "]";
	}

}
