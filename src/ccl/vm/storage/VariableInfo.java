package ccl.vm.storage;

import ccl.iface.IExpression;
import ccl.iface.IVariableInfo;
import ccl.vm.err.VariableDuplicationException;

public class VariableInfo implements IVariableInfo{
	
	private int index;
	private Scope scope;

	public VariableInfo(Scope scope, int index) {
		this.scope = scope;
		this.index = index;
		if(index < 0){
			throw new IllegalStateException("Variable index is invalid!");
		}
	}
	
	public void setName(String name) throws VariableDuplicationException{
		if(Storage.findIndex(scope, name) >= 0) throw new VariableDuplicationException();
		scope.names[index] = name;
	}

	public String toString() {
		return "var:" + scope.names[index];
	}
	
	public String getName(){
		return scope.names[index];
	}
	
	public void setValue(IExpression e){
		scope.variables[index] = e;
	}
	
	public IExpression getValue(){
		return scope.variables[index];
	}

	public int getIndex() {
		return index;
	}

	public void delete() {
		scope.names[index] = "";
		scope.variables[index] = null;
	}

}
