package ccl.vm.expr;

import ccl.iface.IType;
import ccl.vm.core.Expression;
import ccl.vm.core.TypeEnum;

public class BooleanExpression extends Expression {

	public BooleanExpression(boolean o) {
		super(o);
	}
	
	public IType getType(){
		return TypeEnum.BOOLEAN.get();
	}
	
	public String toString(){
		return "B:" + getValue();
	}

}
