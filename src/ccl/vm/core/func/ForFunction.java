package ccl.vm.core.func;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Array;
import ccl.vm.core.Expression;
import ccl.vm.core.expr.ArrayExpression;
import ccl.vm.core.expr.FloatExpression;

public class ForFunction implements IFunction<Object, Object> {

	private IFunction<Object, Object> expression;

	public ForFunction(IFunction<Object, Object> expression) {
		this.expression = expression;
	}

	@Override
	public IExpression<? extends Object> invoke(
			IExpression<? extends Object>... parameters) throws CclException {
		if(parameters.length == 2){
			Array a = new Array(0);
			double start = (Double) parameters[0].getValue();
			double to = (Double) parameters[1].getValue();
			for(double i = start; i <= to; i++){
				a.pushExpression((Expression<?>) expression.invoke(new FloatExpression(i)));
			}
			return new ArrayExpression(a);
		}
		if(parameters.length == 1){
			Object p1 = parameters[0].getValue();
			Array in;
			if(p1 instanceof Array){
				in = (Array) p1;
			}else{
				in = Array.clone((Object[]) p1);
			}
			Array out = new Array(0);
			for(int i = 0; i < in.length(); i++){
				Object val = in.get(i);
				out.pushExpression((Expression<?>) expression.invoke(val instanceof Expression ? (Expression) val : new Expression(val)));
			}
			return new ArrayExpression(out);
		}
		throw new RuntimeException("Unexpected Parameter count!" + parameters.length);
	}

}
