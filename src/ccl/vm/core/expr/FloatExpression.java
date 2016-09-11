package ccl.vm.core.expr;

import ccl.iface.IType;
import ccl.vm.core.Expression;
import ccl.vm.core.TypeEnum;

public class FloatExpression extends Expression<Double> {

	public FloatExpression(double o) {
		super(o);
	}
	
	public IType<Double> getType(){
		return TypeEnum.FLOAT.get();
	}
	
	public String toString(){
		return "F:" + getValue();
	}

}
