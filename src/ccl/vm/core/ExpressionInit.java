package ccl.vm.core;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.func.ArrayGetFunction;
import ccl.vm.func.PushFunction;

public class ExpressionInit {

	public static void initBoolean(Expression e) {
		
	}

	public static void initArray(Expression e) {
		e.setProperty("push", new PushFunction(e));
		e.setProperty("get", new ArrayGetFunction(e));
	}

	public static void initNumber(final Expression e) {
		e.setProperty("sqrt", new Expression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
				return new Expression(
						Math.sqrt(raw(e.getValue())));
			}
			
		}));
		e.setProperty("pow", new Expression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new Expression(
						Math.pow(raw(e.getValue()), raw(parameters[0].getValue()))
					);
			}
			
		}));
		e.setProperty("add", new Expression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new Expression(
						raw(e.getValue()) + raw(parameters[0].getValue()));
			}
			
		}));
		e.setProperty("mul", new Expression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new Expression(
						raw(e.getValue()) * raw(parameters[0].getValue()));
			}
			
		}));
		e.setProperty("sub", new Expression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new Expression(
						raw(e.getValue()) - raw(parameters[0].getValue()));
			}
			
		}));
		e.setProperty("div", new Expression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new Expression(
						raw(e.getValue()) / raw(parameters[0].getValue()));
			}
			
		}));
		e.setProperty("lss", new Expression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new Expression(
						raw(e.getValue()) < raw(parameters[0].getValue()));
			}
			
		}));
		e.setProperty("gtr", new Expression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new Expression(
						raw(e.getValue()) > raw(parameters[0].getValue()));
			}
			
		}));
	}

	public static void initFunction(Expression e) {
		// TODO Auto-generated method stub
		
	}

	public static void initString(Expression e) {
		// TODO Auto-generated method stub
		
	}
	
	private static double raw(Object value) {
		return Double.parseDouble(String.valueOf(value));
	}

}
