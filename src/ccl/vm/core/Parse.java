package ccl.vm.core;

import ccl.iface.CclException;
import ccl.iface.IFunction;
import ccl.iface.exec.IExecuterFactory;
import ccl.vm.bridge.JClass;
import ccl.vm.func.FunctionImpl;

public class Parse {
	
	public static IExecuterFactory factory;
	
	public static Array parseArray(Object o) {
		if(o instanceof Object[]) return Array.clone((Object[]) o);
		if(o instanceof boolean[]) return Array.clone((boolean[]) o);
		if(o instanceof char[]) return Array.clone((char[]) o);
		if(o instanceof byte[]) return Array.clone((byte[]) o);
		if(o instanceof short[]) return Array.clone((short[]) o);
		if(o instanceof int[]) return Array.clone((int[]) o);
		if(o instanceof long[]) return Array.clone((long[]) o);
		if(o instanceof float[]) return Array.clone((float[]) o);
		if(o instanceof double[]) return Array.clone((double[]) o);
		if(o instanceof Array) return (Array) o;
		return new Array(Integer.parseInt(o + ""));
	}
	
	public static boolean parseBoolean(Object o) {
		return Boolean.parseBoolean("" + o);
	}
	
	public static Double parseDouble(Object o) {
		if(o instanceof Number) return ((Number) o).doubleValue();
		return Double.parseDouble(o + "");
	}
	
	public static IFunction parseFunction(Object o) throws CclException{
		if(o instanceof IFunction) return (IFunction) o;
		return new FunctionImpl(factory, o + "");
	}
	
	public static Long parseInteger(Object o) {
		if(o instanceof Number) return ((Number) o).longValue();
		return (long) Double.parseDouble(o + "");
	}
	
	public static Object parseNative(Object o) throws CclException{
		try {
			return new JClass(o + "");
		} catch (Exception e) {
			return e;
		}
	}
	
	public static String parseString(Object o) {
		return o + "";
	}
	
}
