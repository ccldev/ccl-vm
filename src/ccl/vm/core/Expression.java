package ccl.vm.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.iface.IType;
import ccl.vm.core.bridge.JBridgeTool;
import ccl.vm.core.bridge.JClassExpression;
import ccl.vm.core.bridge.JProperty;
import ccl.vm.core.bridge.Property;
import ccl.vm.core.expr.ArrayExpression;
import ccl.vm.core.expr.BooleanExpression;
import ccl.vm.core.expr.ErrorExpression;
import ccl.vm.core.expr.FloatExpression;
import ccl.vm.core.expr.FunctionExpression;
import ccl.vm.core.expr.IndexExpression;
import ccl.vm.core.expr.IntegerExpression;
import ccl.vm.core.expr.StringExpression;
import ccl.vm.core.func.AddParamFunction;
import ccl.vm.core.func.ArrayFunction;
import ccl.vm.core.func.BindFunction;
import ccl.vm.core.func.ForFunction;
import ccl.vm.core.func.LinkFunction;
import ccl.vm.core.storage.StringConstantPool;
import ccl.vm.core.storage.VariableInfo;
import ccl.vm.err.NoSuchNativePropertyException;
import ccl.vm.func.WhileFunction;

public class Expression<T> implements IExpression<T>, IFunction<Object, Object>{

	protected T value;
	private boolean error;
	private HashMap<String, Expression<?>> properties;
	private List<String> propList = new ArrayList<String>();
	
	public Expression(T value) {
		this.value = value;
		this.properties = new HashMap<>();
		init();
	}
	public Expression(){
		this.properties = new HashMap<>();
		init();
	}
	
	private void init() {
		propList.add("array");
		propList.add("while");
		propList.add("for");
		propList.add("unbind");
		propList.add("link");
		propList.add("bind");
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
	
	public final void setProperty(String name, Expression<?> property){
		if(!propList.contains(name)){
			propList.add(name);
		}
		properties.remove(name);
		properties.put(name, property);
	}
	
	@Override
	public final IExpression<?> getProperty(String name) {
		Expression<?> res = null;
		
		try {
			res = getProperty0(name);
		} catch (NoSuchNativePropertyException e) {}
		if(res != null){
			setProperty(name, res);
			return res;
		}
		return new ErrorExpression(new CclException("No such property found!"));
	}
	
	private Expression<?> getProperty0(String name) throws NoSuchNativePropertyException{
		Expression<?> property = properties.get(name);
		switch(name){
		case "array": return new FunctionExpression(new ArrayFunction(this));
		case "while": return new FunctionExpression(new WhileFunction(this));
		case "for": return new FunctionExpression(new ForFunction(this));
		case "unbind": return new FunctionExpression(new AddParamFunction(this));
		case "link": return new FunctionExpression(new LinkFunction(this));
		case "bind": return new FunctionExpression(new BindFunction(this));
		case "intern": return new Expression<>(this);
		case "type": return new Expression<>(computeUseType());
		case "properties": return new ArrayExpression(Array.clone(propList.toArray(new String[0])));
		}
		if(property != null) return property;
		else return Property.getNative(name, value);
	}
	
	public String[] getPropList() {
		return propList.toArray(new String[0]);
	}
	public String computeUseType(){
		Class<?> c = getClass();
		if(c == Expression.class){
			if(getValue() instanceof ErrorMarker) return "error";
			return "base";
		}
		if(c == IntegerExpression.class) return "integer";
		if(c == FloatExpression.class) return "float";
		if(c == ArrayExpression.class) return "array";
		if(c == BooleanExpression.class) return "boolean";
		if(c == StringExpression.class) return "string";
		if(c == FunctionExpression.class) return "function";
		if(c == IndexExpression.class) return "index";
		if(c == JProperty.class) return "native";
		if(c == JClassExpression.class) return "native";
		if(c == ErrorExpression.class) return "error";
		return "unknown";
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
