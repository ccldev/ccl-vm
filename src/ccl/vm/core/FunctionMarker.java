package ccl.vm.core;

import ccl.iface.IFunction;
import ccl.vm.core.expr.FunctionExpression;

public class FunctionMarker<P, R> extends FunctionExpression{

	@SuppressWarnings("unchecked")
	public FunctionMarker(IFunction<P, R> func) {
		super((IFunction<Object, Object>) func);
	}
	
	public String toString(){
		return "function";
	}

}
