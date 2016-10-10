package ccl.vm.core;

import ccl.vm.func.ArrayGetFunction;
import ccl.vm.func.PushFunction;

public class ExpressionInit {

	public static void initBoolean(Expression e) {
		// TODO Auto-generated method stub
		
	}

	public static void initArray(Expression e) {
		e.setProperty("push", new PushFunction(e));
		e.setProperty("get", new ArrayGetFunction(e));
	}

	public static void initNumber(Expression e) {
		// TODO Auto-generated method stub
		
	}

	public static void initFunction(Expression e) {
		// TODO Auto-generated method stub
		
	}

	public static void initString(Expression e) {
		// TODO Auto-generated method stub
		
	}

}
