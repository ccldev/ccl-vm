package ccl.vm.core.storage;

import java.util.ArrayList;

import ccl.vm.core.Expression;
import ccl.vm.core.expr.StringExpression;

public class StringConstantPool {
	
	private static ArrayList<String> strings = new ArrayList<String>();
	private static ArrayList<Expression<String>> expressions = new ArrayList<>();
	
	public static Expression<String> make(String val){
		for(int i = 0; i < strings.size(); i++){
			if(strings.get(i).equals(val)) return expressions.get(i);
		}
		strings.add(val);
		Expression<String> e = new StringExpression(val);
		expressions.add(e);
		return e;
	}
	
}
