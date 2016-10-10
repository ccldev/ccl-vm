package ccl.vm;

import ccl.iface.CclException;
import ccl.vm.core.Expression;
import ccl.vm.core.TypeEnum;
import ccl.vm.core.Undefined;
import ccl.vm.expr.FunctionExpression;
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
		vm.parse(TypeEnum.BOOLEAN.get());
		vm.store();
		
		vm.reserve(BOOL_FALSE);
		vm.load(BOOL_FALSE);
		vm.put(BOOL_FALSE);
		vm.parse(TypeEnum.BOOLEAN.get());
		vm.store();
		
		//num
		vm.reserve(PARSE_INTEGER);
		vm.load(PARSE_INTEGER);
		vm.storage.push(new FunctionExpression(new IntegerParser()));
		vm.store();
		
		vm.reserve(PARSE_FLOAT);
		vm.load(PARSE_FLOAT);
		vm.storage.push(new FunctionExpression(new FloatParser()));
		vm.store();
		
		//char
		vm.reserve(PARSE_CHAR);
		vm.load(PARSE_CHAR);
		vm.storage.push(new FunctionExpression(new CharParser()));
		vm.store();
	}

}
