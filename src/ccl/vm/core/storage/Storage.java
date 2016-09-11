package ccl.vm.core.storage;

import ccl.iface.IExpression;
import ccl.iface.IType;
import ccl.vm.core.Expression;
import ccl.vm.core.TypeEnum;
import ccl.vm.core.Undefined;
import ccl.vm.err.FullTempStorageException;
import ccl.vm.err.FullVariableStorageException;
import ccl.vm.err.TypeCheckException;
import ccl.vm.err.VariableDuplicationException;
import ccl.vm.err.VariableInitException;

public class Storage {
	
	private static final String PLACEHOLDER = "_";
	
	Scope scope;
	
	public Storage(int tempSize, int varSize){
		scope = new Scope(varSize, tempSize);
	}
	
	public VariableInfo reserve(String name) throws FullVariableStorageException, VariableDuplicationException, VariableInitException{
		if(findIndex(scope, name) >= 0) throw new VariableDuplicationException();
		VariableInfo var = init(detectFreeVar());
		var.setName(name);
		return var;
	}

	public void load(String name) throws FullTempStorageException{
		VariableInfo i = createVariable(scope, name);
		put(new Expression<>(i));
	}
	
	private void put(Expression<?> e) throws FullTempStorageException {
		for(int i = 0; i < scope.getTempSize(); i++){
			if(scope.temp[i] != null) continue;
			scope.temp[i] = e;
			return;
		}
		throw new FullTempStorageException();
	}

	public VariableInfo createVariable(Scope s, String name){
		int index = findIndex(s, name);
		if(index < 0 && s.hasParent()) return createVariable(s.parent, name);
		return new VariableInfo(s, findIndex(s, name));
	}

	private VariableInfo init(int index) throws VariableInitException {
		checkEmpty(index);
		fill(index);
		return new VariableInfo(scope, index);
	}
	
	private void fill(int index) {
		scope.variables[index] = new Expression<>(new Undefined());
		scope.names[index] = PLACEHOLDER;
		scope.used[index] = true;
		scope.type[index] = TypeEnum.EXPRESSION.get();
	}

	private void checkEmpty(int index) throws VariableInitException {
		if(scope.used[index]) initError();
		if(scope.names[index] != null) initError();
		if(scope.variables[index] != null) initError();
		if(scope.type[index] != null) initError();
	}

	private void initError() throws VariableInitException{
		throw new VariableInitException();
	}

	public static int findIndex(Scope scope, String name) {
		for(int i = 0; i < scope.getVarSize(); i++){
			if(!scope.used[i]) continue;
			if(scope.names[i].equals(name)) return i;
		}
		return -1;
	}

	private int detectFreeVar() throws FullVariableStorageException {
		for(int i = 0; i < scope.getVarSize(); i++){
			if(!scope.used[i]) return i;
		}
		throw new FullVariableStorageException();
	}

	public String toString() {
		return "Storage [temp="
				+ (scope.temp != null ? arrayToString(scope.temp, scope.temp.length) : null)
				+ ", variables="
				+ (scope.variables != null ? arrayToString(scope.variables,
						scope.variables.length) : null) + ", names="
				+ (scope.names != null ? arrayToString(scope.names, scope.names.length) : null)
				+ ", used="
				+ (scope.used != null ? arrayToString(scope.used, scope.used.length) : null)
				+ ", tempSize=" + scope.getTempSize() + ", varSize=" + scope.getVarSize() + "]";
	}

	private String arrayToString(Object array, int len) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (int i = 0; i < len; i++) {
			if (i > 0)
				buffer.append(", ");
			if (array instanceof boolean[])
				buffer.append(((boolean[]) array)[i]);
			if (array instanceof Object[])
				buffer.append(((Object[]) array)[i]);
		}
		buffer.append("]");
		return buffer.toString();
	}

	public String tempAsString() {
		if(scope.temp[0] == null) return "()";
		StringBuilder b = new StringBuilder("(");
		b.append(scope.temp[0]);
		for(int i = 1; i < scope.getTempSize(); i++){
			if(scope.temp[i] == null) break;
			b.append(", ");
			b.append(scope.temp[i]);
		}
		b.append(")");
		return b.toString();
	}
	
	private int getLastFullTempIndex(){
		for(int i = 0; i < scope.getTempSize(); i++){
			if(scope.temp[i] == null) return i - 1;
		}
		return scope.getTempSize() - 1;
	}
	
	public IExpression<?> pop(){
		int i = getLastFullTempIndex();
		IExpression<?> e = scope.temp[i];
		scope.temp[i] = null;
		return e;
	}
	
	public IExpression<?> peek(){
		return scope.temp[getLastFullTempIndex()];
	}
	
	public void push(IExpression<?> e){
		scope.temp[getLastFullTempIndex() + 1] = e;
	}
	
	public void setType(int index, IType<?> t) throws TypeCheckException{
		if(!t.check(scope.variables[index])) throw new TypeCheckException();
		scope.type[index] = t;
	}

	public void delete(String name) throws VariableInitException {
		createVariable(scope, name).delete();		
	}

	@SuppressWarnings("unchecked")
	public IExpression<Object>[] getTemp() {
		return (IExpression<Object>[]) scope.temp;
	}

	public void openScope() {
		scope = scope.chain();
	}
	
	public void closeScope() {
		scope = scope.close();
	}

	public boolean isCloseable() {
		return this.scope.parent != null;
	}
	
}
