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
import ccl.vm.expr.BooleanExpression;
import ccl.vm.expr.ErrorExpression;
import ccl.vm.expr.FloatExpression;
import ccl.vm.expr.FunctionExpression;
import ccl.vm.expr.IntegerExpression;
import ccl.vm.expr.StringExpression;
import ccl.vm.func.AddParamFunction;
import ccl.vm.func.ArrayFunction;
import ccl.vm.func.BindFunction;
import ccl.vm.func.ForFunction;
import ccl.vm.func.LinkFunction;
import ccl.vm.func.WhileFunction;
import ccl.vm.storage.VariableInfo;

public class Expression implements IExpression {

	protected Object value;
	private HashMap<String, Expression> properties;
	private List<String> propList = new ArrayList<String>();

	public Expression(Object value) {
		this.value = value;
		this.properties = new HashMap<>();
		init();
	}

	public Expression() {
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
		return "E:\"" + value + "\"";
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
			return new FunctionExpression(new ArrayFunction(this));
		case "while":
			return new FunctionExpression(new WhileFunction(this));
		case "for":
			return new FunctionExpression(new ForFunction(this));
		case "unbind":
			return new FunctionExpression(new AddParamFunction(this));
		case "link":
			return new FunctionExpression(new LinkFunction(this));
		case "bind":
			return new FunctionExpression(new BindFunction(this));
		}
		res = getProperty0(name);
		if (res != null) {
			setProperty(name, res);
			return res;
		}
		return new ErrorExpression(new CclException("No such property found!"));
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
		if (c == Expression.class) {
			if (getValue() instanceof ErrorMarker)
				return "error";
			return "base";
		}

		if (value instanceof Array)
			return "array";

		if (c == IntegerExpression.class)
			return "integer";
		if (c == FloatExpression.class)
			return "float";
		if (c == BooleanExpression.class)
			return "boolean";
		if (c == StringExpression.class)
			return "string";
		if (c == FunctionExpression.class)
			return "function";
		if (c == JProperty.class)
			return "native";
		if (c == JClassExpression.class)
			return "native";
		if (c == ErrorExpression.class)
			return "error";
		return "unknown";
	}

	@Override
	public IExpression invoke(IExpression... parameters) throws CclException {
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
