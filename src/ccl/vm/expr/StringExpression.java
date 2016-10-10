package ccl.vm.expr;

import ccl.iface.IType;
import ccl.vm.core.Expression;
import ccl.vm.core.TypeEnum;

public class StringExpression extends Expression{
	
	public StringExpression(String val) {
		super(val);
	}

	public IType getType(){
		return TypeEnum.STRING.get();
	}
	
	public String toString(){
		return "S:\"" + getValue() + "\"";
	}
	
}
