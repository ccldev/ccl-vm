package ccl.vm.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;

public class CuttedFunction implements IFunction {

	private Double added;
	private IFunction func;

	public CuttedFunction(Double added, IFunction expression) {
		this.added = added;
		this.func = expression;
	}
	
	@Override
	public IExpression invoke(
			IExpression... parameters) throws CclException {
		IExpression[] arr = new IExpression[(int) (parameters.length - added)];
		for(int i = 0; i < arr.length; i++){
			arr[i] = parameters[(int) (i + added)];
		}
		return func.invoke(arr);
	}

}
