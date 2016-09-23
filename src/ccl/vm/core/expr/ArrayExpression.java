package ccl.vm.core.expr;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IType;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;
import ccl.vm.core.TypeEnum;
import ccl.vm.core.func.GetIndexFunction;
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
	}

	public IType<Array> getType(){
		return TypeEnum.ARRAY.get();
	}
	
	public String toString(){
		return "A:" + array;
	}
	
	public Expression<?> getProperty(String p) throws CclException{
		try{
			return (Expression<?>) super.getProperty(p);
		}catch(NoSuchNativePropertyException e){
			try{
				return array.getExpression(Integer.parseInt(p));
			}catch(RuntimeException e2){
				throw new NoSuchPropertyException();
			}
		}
	}

}
