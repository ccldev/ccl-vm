package ccl.vm.expr;

public class IntegerExpression extends NumberExpression {

	public IntegerExpression(long o) {
		super(o);
	}
	
	public String toString(){
		return "I:" + getValue();
	}

}
