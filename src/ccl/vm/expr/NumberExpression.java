package ccl.vm.expr;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;

public class NumberExpression extends Expression {
	
	protected NumberExpression(Number t){
		super(t);
		setProperty("sqrt", new FunctionExpression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
				return new FloatExpression(
						Math.sqrt(raw(NumberExpression.this.getValue())));
			}
			
		}));
		setProperty("pow", new FunctionExpression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new FloatExpression(
						Math.pow(raw(NumberExpression.this.getValue()), raw(parameters[0].getValue()))
					);
			}
			
		}));
		setProperty("add", new FunctionExpression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new FloatExpression(
						raw(NumberExpression.this.getValue()) + raw(parameters[0].getValue()));
			}
			
		}));
		setProperty("mul", new FunctionExpression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new FloatExpression(
						raw(NumberExpression.this.getValue()) * raw(parameters[0].getValue()));
			}
			
		}));
		setProperty("sub", new FunctionExpression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new FloatExpression(
						raw(NumberExpression.this.getValue()) - raw(parameters[0].getValue()));
			}
			
		}));
		setProperty("div", new FunctionExpression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new FloatExpression(
						raw(NumberExpression.this.getValue()) / raw(parameters[0].getValue()));
			}
			
		}));
		setProperty("lss", new FunctionExpression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new BooleanExpression(
						raw(NumberExpression.this.getValue()) < raw(parameters[0].getValue()));
			}
			
		}));
		setProperty("gtr", new FunctionExpression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new BooleanExpression(
						raw(NumberExpression.this.getValue()) > raw(parameters[0].getValue()));
			}
			
		}));
	}

	protected double raw(Object value) {
		return Double.parseDouble(String.valueOf(value));
	}
	
}
