package ccl.vm.core.expr;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;

public class NumberExpression<T> extends Expression<T> {
	
	protected NumberExpression(T t){
		super(t);
		setProperty("sqrt", new FunctionExpression<Object, Double>(new IFunction<Object, Double>(){

			@Override
			public IExpression<Double> invoke(IExpression<Object>... parameters)
					throws CclException {
				return new FloatExpression(
						Math.sqrt((Double) (NumberExpression.this.getValue())));
			}
			
		}));
		setProperty("pow", new FunctionExpression<Object, Double>(new IFunction<Object, Double>(){

			@Override
			public IExpression<Double> invoke(IExpression<Object>... parameters)
					throws CclException {
					return new FloatExpression(
						Math.pow((Double) (NumberExpression.this.getValue()), (double) parameters[0].getValue())
					);
			}
			
		}));
	}
	
}
