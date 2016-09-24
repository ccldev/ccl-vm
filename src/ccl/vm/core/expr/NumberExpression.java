package ccl.vm.core.expr;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;

public class NumberExpression<T> extends Expression<T> {
	
	protected NumberExpression(T t){
		super(t);
		setProperty("sqrt", new FunctionExpression(new IFunction<Object, Object>(){

			@Override
			public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters)
					throws CclException {
				return new FloatExpression(
						Math.sqrt((Double) (NumberExpression.this.getValue())));
			}
			
		}));
		setProperty("pow", new FunctionExpression(new IFunction<Object, Object>(){

			@Override
			public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters)
					throws CclException {
					return new FloatExpression(
						Math.pow((Double) (NumberExpression.this.getValue()), (Double) parameters[0].getValue())
					);
			}
			
		}));
		setProperty("add", new FunctionExpression(new IFunction<Object, Object>(){

			@Override
			public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters)
					throws CclException {
					return new FloatExpression(
						(Double) NumberExpression.this.getValue() + (Double) parameters[0].getValue());
			}
			
		}));
		setProperty("mul", new FunctionExpression(new IFunction<Object, Object>(){

			@Override
			public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters)
					throws CclException {
					return new FloatExpression(
						(Double) NumberExpression.this.getValue() * (Double) parameters[0].getValue());
			}
			
		}));
		setProperty("sub", new FunctionExpression(new IFunction<Object, Object>(){

			@Override
			public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters)
					throws CclException {
					return new FloatExpression(
						(Double) NumberExpression.this.getValue() - (Double) parameters[0].getValue());
			}
			
		}));
		setProperty("div", new FunctionExpression(new IFunction<Object, Object>(){

			@Override
			public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters)
					throws CclException {
					return new FloatExpression(
						(Double) NumberExpression.this.getValue() / (Double) parameters[0].getValue());
			}
			
		}));
		setProperty("lss", new FunctionExpression(new IFunction<Object, Object>(){

			@Override
			public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters)
					throws CclException {
					return new BooleanExpression(
						(Double) NumberExpression.this.getValue() < (Double) parameters[0].getValue());
			}
			
		}));
		setProperty("gtr", new FunctionExpression(new IFunction<Object, Object>(){

			@Override
			public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters)
					throws CclException {
					return new BooleanExpression(
						(Double) NumberExpression.this.getValue() > (Double) parameters[0].getValue());
			}
			
		}));
	}
	
}
