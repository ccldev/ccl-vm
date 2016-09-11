package ccl.vm.core.expr;

import ccl.iface.IType;
import ccl.vm.core.Expression;
import ccl.vm.core.TypeEnum;

public class BooleanExpression extends Expression<Boolean> {

	public BooleanExpression(boolean o) {
		super(o);
	}
	
	public IType<Boolean> getType(){
		return TypeEnum.BOOLEAN.get();
	}
	
	public String toString(){
		return "B:" + getValue();
	}

}
