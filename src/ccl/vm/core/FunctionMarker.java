package ccl.vm.core;

import ccl.iface.IFunction;
import ccl.vm.expr.FunctionExpression;

public class FunctionMarker extends FunctionExpression{
	
	public FunctionMarker(IFunction func) {
		super((IFunction) func);
	}
	
	public String toString(){
		return "function";
	}

}
