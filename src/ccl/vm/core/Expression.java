package ccl.vm.core;

import java.util.HashMap;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IType;
import ccl.vm.core.bridge.Property;
import ccl.vm.core.storage.StringConstantPool;
import ccl.vm.core.storage.VariableInfo;

public class Expression<T> implements IExpression<T>{

	protected T value;
	private boolean error;
	private HashMap<String, Expression<?>> properties;
	
	public Expression(T value) {
		this.value = value;
		this.properties = new HashMap<>();
	}
	public Expression(){
		this.properties = new HashMap<>();
	}
	
	public static Expression<Undefined> empty(){
		return new Expression<>(new Undefined());
	}
	
	public String toString(){
		return "E:\"" + value + "\"";
	}
	public VariableInfo asVariable(){
		return (VariableInfo) value;
	}
	
	public IType<T> getType(){
		return TypeEnum.EXPRESSION.get();
	}
	
	public T getValue(){
		return value;
	}
	
	public static Expression<String> make(String s){
		return StringConstantPool.make(s);
	}
	
	public void setProperty(String name, Expression<?> property){
		properties.remove(name);
		properties.put(name, property);
	}
	
	@Override
	public IExpression<?> getProperty(String name) throws CclException {
		Expression<?> property = properties.get(name);
		if(property != null) return property;
		else return Property.getNative(name, value);
	}
	@Override
	public boolean bool() {
		return (Boolean) value;
	}

}
