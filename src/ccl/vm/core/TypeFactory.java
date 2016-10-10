package ccl.vm.core;

import ccl.vm.types.ArrayType;
import ccl.vm.types.BooleanType;
import ccl.vm.types.ExpressionType;
import ccl.vm.types.FloatType;
import ccl.vm.types.FunctionType;
import ccl.vm.types.IntegerType;
import ccl.vm.types.NativeType;
import ccl.vm.types.StringType;

public class TypeFactory {
	
	public static StringType s(){
		return StringType.INSTANCE;
	}
	public static IntegerType i(){
		return IntegerType.INSTANCE;
	}
	public static FloatType f(){
		return FloatType.INSTANCE;
	}
	public static ArrayType a(){
		return ArrayType.INSTANCE;
	}
	public static ExpressionType e(){
		return ExpressionType.INSTANCE;
	}
	public static BooleanType b(){
		return BooleanType.INSTANCE;
	}
	public static NativeType n(){
		return NativeType.INSTANCE;
	}
	public static FunctionType m(){
		return FunctionType.INSTANCE;
	}
	
}
