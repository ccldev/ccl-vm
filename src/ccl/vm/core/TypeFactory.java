package ccl.vm.core;

import ccl.vm.core.types.ArrayType;
import ccl.vm.core.types.BooleanType;
import ccl.vm.core.types.ExpressionType;
import ccl.vm.core.types.FloatType;
import ccl.vm.core.types.FunctionType;
import ccl.vm.core.types.IndexType;
import ccl.vm.core.types.IntegerType;
import ccl.vm.core.types.NativeType;
import ccl.vm.core.types.StringType;

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
	public static ExpressionType<?> e(){
		return ExpressionType.INSTANCE;
	}
	public static IndexType d(){
		return IndexType.INSTANCE;
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
