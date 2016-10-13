package ccl.vm;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.core.Expression;
import ccl.vm.core.Parse;
import ccl.vm.core.Undefined;
import ccl.vm.std.CharParser;
import ccl.vm.std.FloatParser;
import ccl.vm.std.IntegerParser;

public class VM {

	private static final String BOOL_TRUE = "true";
	private static final String BOOL_FALSE = "false";
	private static final String PARSE_INTEGER = "integer";
	private static final String PARSE_FLOAT = "float";
	private static final String PARSE_CHAR = "char";
	
	public static void init(VMActions vm) throws CclException {
		//undefined
		vm.reserve("undefined");
		vm.load("undefined");
		vm.storage.push(new Expression(new Undefined()));
		vm.store();
		
		//bool
		vm.reserve(BOOL_TRUE);
		vm.load(BOOL_TRUE);
		vm.put(BOOL_TRUE);
		vm.parse('B');
		vm.store();
		
		vm.reserve(BOOL_FALSE);
		vm.load(BOOL_FALSE);
		vm.put(BOOL_FALSE);
		vm.parse('B');
		vm.store();
		
		vm.reserve("boolean");
		vm.load("boolean");
		vm.storage.push(new Expression(new IFunction(){
			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
				return new Expression(Parse.parseBoolean(parameters[0].getValue()));
			}
		}));
		vm.store();
		
		//num
		vm.reserve(PARSE_INTEGER);
		vm.load(PARSE_INTEGER);
		vm.storage.push(new Expression(new IntegerParser()));
		vm.store();
		
		vm.reserve(PARSE_FLOAT);
		vm.load(PARSE_FLOAT);
		vm.storage.push(new Expression(new FloatParser()));
		vm.store();
		
		//char
		vm.reserve(PARSE_CHAR);
		vm.load(PARSE_CHAR);
		vm.storage.push(new Expression(new CharParser()));
		vm.store();
		
		//err
		vm.reserve("error");
		vm.load("error");
		vm.storage.push(new Expression(new IFunction(){
			@Override
			public IExpression invoke(IExpression... parameters)
					throws CclException {
				return Expression.err(parameters[0].getValue());
			}
		}));
		vm.store();
	}

}
