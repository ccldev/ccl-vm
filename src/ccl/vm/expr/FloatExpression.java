package ccl.vm.expr;

import ccl.iface.IType;
import ccl.vm.core.TypeEnum;

public class FloatExpression extends NumberExpression {

	public FloatExpression(double o) {
		super(o);
	}
	
	public IType getType(){
		return TypeEnum.FLOAT.get();
	}
	
	public String toString(){
		return "F:" + getValue();
	}

}
