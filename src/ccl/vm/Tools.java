package ccl.vm;

import java.util.ArrayList;

import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.bridge.JBridgeTool;
import ccl.vm.core.bridge.JFunctionWrapper;

public class Tools {
	
	@SuppressWarnings("unchecked")
	public static IFunction<Object, Object> asFunction(IExpression<Object> expr, Object o){
		if(expr instanceof IFunction){
			return (IFunction<Object, Object>) expr;
		}else if(expr.getValue() instanceof IFunction){
			return (IFunction<Object, Object>) expr.getValue();
		}else{
			return asJavaMethod(expr, o);
		}
	}
	
	private static IFunction<Object, Object> asJavaMethod(
			IExpression<Object> expr, Object o) {
		return new JFunctionWrapper(expr, o);
	}

	public static <T> T[] link(T[]... arrays){
		ArrayList<T> list = new ArrayList<T>();
		for(int i = 0; i < arrays.length; i++){
			T[] arr = arrays[i];
			for(int k = 0; k < arr.length; k++){
				list.add(arr[k]);
			}
		}
		return list.toArray(arrays[0]);
	}
	
}
