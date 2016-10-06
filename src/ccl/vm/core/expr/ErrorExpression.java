package ccl.vm.core.expr;

import ccl.iface.IExpression;
import ccl.vm.core.ErrorMarker;
import ccl.vm.core.Expression;

public class ErrorExpression extends Expression<ErrorMarker> {
	
	public ErrorExpression(Object err){
		super(new ErrorMarker(err));
	}
	
}
