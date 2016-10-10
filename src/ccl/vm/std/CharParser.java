package ccl.vm.std;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;

public class CharParser implements IFunction{

	@Override
	public IExpression invoke(
			IExpression... parameters) throws CclException {
		Object param = parameters[0].getValue();
		if(param instanceof Number){
			return new Expression((char) ((Number) param).intValue());
		}
		if(param instanceof String){
			String s = (String) param;
			if(s.length() == 1){
				return new Expression(s.charAt(0));
			}
			return Expression.err("Unable to parse char. Length != 1\nInput: " + s);
		}
		if(param instanceof Character){
			return new Expression(param);
		}
		return Expression.err("Unable to parse char. Unknown object type!\nInput: " + param);
	}

}