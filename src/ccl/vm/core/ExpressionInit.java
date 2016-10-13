package ccl.vm.core;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.func.ArrayGetFunction;
import ccl.vm.func.PushFunction;

import static ccl.vm.Tools.raw;

public class ExpressionInit {

	public static void initBoolean(final Expression e) {
		e.setProperty("and", new Expression(new IFunction(){
			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
				return new Expression(((boolean) e.getValue()) && (boolean) parameters[0].getValue());
			}
		}));
		e.setProperty("or", new Expression(new IFunction(){
			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
				return new Expression(((boolean) e.getValue()) || (boolean) parameters[0].getValue());
			}
		}));
	}

	public static void initArray(final Expression e) {
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
		e.setProperty("range", new Expression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					
					Array ret = new Array(0);
				
					double start = ((Number) e.getValue()).doubleValue();
					double end = ((Number) parameters[0].getValue()).doubleValue();
					for(double i = start; i < end; i++){
						ret.pushExpression(new Expression(i));
					}
					
					return new Expression(ret);
			}
			
		}));
		e.setProperty("mod", new Expression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new Expression(
						raw(e.getValue()) % raw(parameters[0].getValue()));
			}
			
		}));
		e.setProperty("equals", new Expression(new IFunction(){

			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
					return new Expression(
						raw(e.getValue()) == raw(parameters[0].getValue()));
			}
			
		}));
	}

	public static void initFunction(Expression e) {
		// TODO Auto-generated method stub
		
	}

	public static void initString(Expression e) {
		// TODO Auto-generated method stub
		
	}

}
