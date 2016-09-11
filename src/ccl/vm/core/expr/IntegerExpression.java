package ccl.vm.core.expr;

import ccl.iface.IType;
import ccl.vm.core.Expression;
import ccl.vm.core.TypeEnum;

public class IntegerExpression extends Expression<Long> {

	public IntegerExpression(long o) {
		super(o);
	}
	
	public IType<Long> getType(){
		return TypeEnum.INTEGER.get();
	}
	
	public String toString(){
		return "I:" + getValue();
	}

}
