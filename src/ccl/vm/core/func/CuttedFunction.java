package ccl.vm.core.func;

import java.util.Arrays;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;

public class CuttedFunction implements IFunction<Object, Object> {

	private Double added;
	private IFunction<Object, Object> func;

	public CuttedFunction(Double added, IFunction<Object, Object> expression) {
		this.added = added;
		this.func = expression;
	}

	@Override
	public IExpression<? extends Object> invoke(
			IExpression<? extends Object>... parameters) throws CclException {
		IExpression<? extends Object>[] arr = new IExpression[(int) (parameters.length - added)];
		for(int i = 0; i < arr.length; i++){
			arr[i] = parameters[(int) (i + added)];
		}
		return func.invoke(arr);
	}

}
