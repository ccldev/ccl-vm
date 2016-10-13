package ccl.vm;

import java.io.IOException;
import java.util.ArrayList;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.iface.IVMActions;
import ccl.iface.exec.IExecuterFactory;
import ccl.vm.bridge.JClass;
import ccl.vm.bridge.JClassExpression;
import ccl.vm.core.Expression;
import ccl.vm.core.Parse;
import ccl.vm.err.FullTempStorageException;
import ccl.vm.err.FullVariableStorageException;
import ccl.vm.err.TypeCheckException;
import ccl.vm.err.VariableDuplicationException;
import ccl.vm.err.VariableInitException;
import ccl.vm.storage.Storage;

public class VMActions implements IVMActions{
	
	@Override
	public void close() throws IOException {
		if(storage.isCloseable()){
			System.out.println("CCL VM WARNING: Not all scopes closed!");
		}
	}
	
	public Storage storage;
	private IExpression value;

	public VMActions(Storage storage) throws CclException{
		this.storage = storage;
		
		//init
		VM.init(this);
	}
	
	public void reserve(String name) throws FullVariableStorageException, VariableDuplicationException, VariableInitException{
		storage.reserve(name);
	}
	public void load(String name) throws FullTempStorageException{
		storage.load(name);
	}
	public IExpression pop(){
		return storage.pop();
	}
	
	public void duplicate(){
		IExpression e = pop();
		storage.push(e);
		storage.push(e);
	}
	public void put(String val){
		storage.push(new Expression(val));
	}
	public void parse(char t) throws CclException {
		Object o = pop().getValue();
		switch(t){
		case 'B': o = Parse.parseBoolean(o); break;
		case 'A': o = Parse.parseArray(o); break;
		case 'F': o = Parse.parseDouble(o); break;
		case 'M': o = Parse.parseFunction(o); break;
		case 'S': o = Parse.parseString(o); break;
		case 'I': o = Parse.parseInteger(o); break;
		case 'N': o = Parse.parseNative(o); break;
		default: throw new RuntimeException("Unexpected type char: " + t);
		}
		if(o instanceof Exception){
			storage.push(Expression.err(o));
		}else if(o instanceof JClass){
			storage.push(new JClassExpression((JClass) o));
		}else{
			storage.push(new Expression(o));
		}
	}
	public void store() throws TypeCheckException {
		IExpression val = pop();
		IExpression var = pop();
		var.asVariable().setValue(val);
	}
	public void readval(){
		IExpression e = storage.peek().asVariable().getValue();
		pop();
		storage.push(e);
	}
	public void swap() {
		IExpression a = pop();
		IExpression b = pop();
		storage.push(a);
		storage.push(b);
	}
	public void get(String s) throws CclException {
		storage.push(pop().getProperty(s));
	}
	public void invoke(String s) throws CclException {
		int l = Integer.parseInt(s);
		IFunction func = pop();
		
		IExpression[] array = new IExpression[l];
		for(int i = l - 1; i >= 0; i--){
			array[i] = (IExpression) pop();
		}
		storage.openScope();
		IExpression ret; 
		
		try{
			ret = func.invoke((IExpression[]) array);
		}catch(RuntimeException e){
			ret = Expression.err(e);
		}
		
		storage.closeScope();
		storage.push(ret);
	}

	@Override
	public void setExecuterFactory(IExecuterFactory factory) {
		Parse.factory = factory;
	}
	
	@Override
	public IExpression[] getTemp() {
		ArrayList<IExpression> list = new ArrayList<>();
		for(int i = 1; i < storage.getTemp().length; i++){
			if(storage.getTemp()[i] == null) break;
			list.add(storage.getTemp()[i]);
		}
		return list.toArray(new IExpression[0]);
	}

	@Override
	public void ret() {
		setValue(pop());
	}

	private void setValue(IExpression v) {
		this.value = v;
	}
	
	public IExpression getValue(){
		return value;
	}

	@Override
	public void newscope() {
		storage.openScope();
	}

	@Override
	public void oldscope() {
		storage.closeScope();
	}
	
}
