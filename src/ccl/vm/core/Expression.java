package ccl.vm.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.vm.bridge.JClassExpression;
import ccl.vm.bridge.JProperty;
import ccl.vm.bridge.Property;
import ccl.vm.func.AddParamFunction;
import ccl.vm.func.ArrayFunction;
import ccl.vm.func.BindFunction;
import ccl.vm.func.ForFunction;
import ccl.vm.func.FunctionImpl;
import ccl.vm.func.LinkFunction;
import ccl.vm.func.PropertyFunction;
import ccl.vm.func.WhileFunction;
import ccl.vm.storage.VariableInfo;

public class Expression implements IExpression {

	protected Object value;
	private HashMap<String, Expression> properties;
	private List<String> propList = new ArrayList<String>();

	private boolean error;
	
	public static Expression err(Object err){
		Expression e = new Expression(err);
		e.error = true;
		return e;
	}
	
	public Expression(Object value) {
		this.value = value;
		this.properties = new HashMap<>();
		init();
	}

	public Expression() {
		this.properties = new HashMap<>();
		init();
	}

	public boolean isError(){
		return error;
	}
	
	private void init() {
		propList.add("array");
		propList.add("while");
		propList.add("for");
		propList.add("unbind");
		propList.add("link");
		propList.add("bind");

		if (value instanceof Boolean) {
			ExpressionInit.initBoolean(this);
		}
		if (value instanceof Array) {
			ExpressionInit.initArray(this);
		}
		if (value instanceof Number) {
			ExpressionInit.initNumber(this);
		}
		if (value instanceof IFunction) {
			ExpressionInit.initFunction(this);
		}
		if (value instanceof String) {
			ExpressionInit.initString(this);
		}
	}

	public String toString() {
		if(value instanceof IFunction){
			if(value instanceof FunctionImpl){
				return "<function>";
			}
			return "<native function>";
		}
		return "<expr: " + value + ">";
	}

	public VariableInfo asVariable() {
		return (VariableInfo) value;
	}

	public Object getValue() {
		return value;
	}

	public final void setProperty(String name, Expression property) {
		if (!propList.contains(name)) {
			propList.add(name);
		}
		properties.remove(name);
		properties.put(name, property);
	}

	@Override
	public final IExpression getProperty(String name) {
		Expression res = null;
		switch (name) {
		case "array":
			return new Expression(new ArrayFunction(this));
		case "while":
			return new Expression(new WhileFunction(this));
		case "for":
			return new Expression(new ForFunction(this));
		case "unbind":
			return new Expression(new AddParamFunction(this));
		case "link":
			return new Expression(new LinkFunction(this));
		case "bind":
			return new Expression(new BindFunction(this));
		case "property":
			return new Expression(new PropertyFunction(this));
		}
		res = getProperty0(name);
		if (res != null) {
			setProperty(name, res);
			return res;
		}
		return err(new CclException("No such property found!"));
	}

	private Expression getProperty0(String name) {
		Expression property = properties.get(name);
		switch (name) {
		case "intern":
			return new Expression(this);
		case "type":
			return new Expression(computeUseType());
		case "properties":
			return new Expression(Array.clone(propList.toArray(new String[0])));
		}
		if (property != null)
			return property;
		else
			return Property.getNative(name, value);
	}

	public String[] getPropList() {
		return propList.toArray(new String[0]);
	}

	public String computeUseType() {
		Class<?> c = getClass();

		if (error)
			return "error";
		
		if (value instanceof Array)
			return "array";
		if (value instanceof Boolean)
			return "boolean";
		if (value instanceof Number)
			return "number";
		if (value instanceof String)
			return "string";
		if (value instanceof IFunction)
			return "function";
		
		if (c == JProperty.class)
			return "native";
		if (c == JClassExpression.class)
			return "native";
		return "unknown";
	}

	@Override
	public IExpression invoke(IExpression... parameters) throws CclException {
		if (value instanceof IFunction){
			return ((IFunction) value).invoke(parameters);
		}
		if (parameters.length == 0)
			return this;
		else if (parameters.length == 1) {
			return getProperty(parameters[0].getValue().toString());
		} else {
			throw new RuntimeException("Unsupported Parameter count!"
					+ parameters.length);
		}
	}

}
