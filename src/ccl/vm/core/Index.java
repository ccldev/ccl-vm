package ccl.vm.core;

import ccl.vm.core.expr.ArrayExpression;
import ccl.vm.core.expr.IndexExpression;

public class Index {

	private Number value;
	private ArrayExpression arrayExpr;

	public Index(ArrayExpression arrayExpr, Number value) {
		this.arrayExpr = arrayExpr;
		this.value = value;
		validate(arrayExpr.getValue(), value);
	}

	private void validate(Array a, Number n) {
		a.getExpression(n.intValue());
	}
	
	public void setName(String name){
		arrayExpr.setProperty(name, new IndexExpression(this));
	}
	
	public Object getValue(){
		return arrayExpr.getValue().getExpression(value.intValue()).getValue();
	}
	
	public int getIndex(){
		return value.intValue();
	}

}
