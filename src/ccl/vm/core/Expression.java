package ccl.vm.core;

import java.lang.reflect.Method;
import java.util.HashMap;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.iface.IType;
import ccl.vm.core.bridge.JBridgeTool;
import ccl.vm.core.bridge.Property;
import ccl.vm.core.expr.ArrayExpression;
import ccl.vm.core.expr.FunctionExpression;
import ccl.vm.core.func.AddParamFunction;
import ccl.vm.core.func.ForFunction;
import ccl.vm.core.func.LinkFunction;
import ccl.vm.core.storage.StringConstantPool;
import ccl.vm.core.storage.VariableInfo;

public class Expression<T> implements IExpression<T>, IFunction<Object, Object>{

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
		switch(name){
		case "for": return new FunctionExpression(new ForFunction(this));
		case "unbind": return new FunctionExpression(new AddParamFunction(this));
		case "link": return new FunctionExpression(new LinkFunction(this));
		case "intern": return new Expression(this);
		}
		if(property != null) return property;
		else return Property.getNative(name, value);
	}
	@Override
	public boolean bool() {
		return (Boolean) value;
	}
	@Override
	public IExpression<? extends Object> invoke(IExpression<? extends Object>... parameters)
			throws CclException {
		if(parameters.length == 0) return this;
		else if(parameters.length == 1){
			return getProperty(parameters[0].getValue().toString());
		}else{
			throw new RuntimeException("Unsupported Parameter count!" + parameters.length);
		}
	}

}
