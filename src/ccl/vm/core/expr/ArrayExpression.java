package ccl.vm.core.expr;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IType;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;
import ccl.vm.core.TypeEnum;
import ccl.vm.core.func.ArrayGetFunction;
import ccl.vm.core.func.GetIndexFunction;
import ccl.vm.core.func.PushFunction;
import ccl.vm.err.NoSuchNativePropertyException;
import ccl.vm.err.NoSuchPropertyException;

public class ArrayExpression extends Expression<Array> {
	
	private Array array;

	public ArrayExpression(Array o) {
		super(o);
		this.array = o;
		initProperties();
	}
	
	private void initProperties() {
		setProperty("getIndex", new GetIndexFunction(this));
		setProperty("push", new PushFunction(this));
		setProperty("get", new ArrayGetFunction(this));
	}

	public IType<Array> getType(){
		return TypeEnum.ARRAY.get();
	}
	
	public String toString(){
		return "A:" + array;
	}

}
