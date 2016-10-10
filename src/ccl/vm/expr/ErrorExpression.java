package ccl.vm.expr;

import ccl.vm.core.ErrorMarker;
import ccl.vm.core.Expression;

public class ErrorExpression extends Expression {
	
	public ErrorExpression(Object err){
		super(new ErrorMarker(err));
	}
	
}
