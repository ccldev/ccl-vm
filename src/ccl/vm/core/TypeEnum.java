package ccl.vm.core;

import ccl.iface.IType;

import static ccl.vm.core.TypeFactory.*;

public enum TypeEnum {
	
	STRING(s()), INTEGER(i()), FLOAT(f()), 
	ARRAY(a()), INDEX(d()), EXPRESSION(e()),
	BOOLEAN(b()), NATIVE(n()), METHOD(m());
	
	private IType<?> type;

	private TypeEnum(IType<?> t){
		this.type = t;
	}
	
	@SuppressWarnings("unchecked")
	public <T> IType<T> get(){
		return (IType<T>) type;
	}
	
}
