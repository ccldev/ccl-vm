package ccl.vm.core.bridge.codefactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.vm.Tools;
import ccl.vm.core.Expression;

public class JCodeFactory {
	
	public static String createClassSource(Class<?> c, String name){
		StringBuilder b = new StringBuilder();
		b.append("package ");
		b.append(createPackageName(c));
		b.append(";");
		b.append("public class ");
		b.append(name);
		b.append(" extends ");
		b.append(c.getName());
		b.append("{private ");
		b.append(IExpression.class.getName());
		b.append(" _method_container_;");
		
		Method[] methods = c.getDeclaredMethods();
		for(int i = 0; i < methods.length; i++){
			b.append(createMethodString(methods[i]));
		}
		
		b.append(createConstructorString(name));
		
		b.append("}");
		return b.toString();
	}

	private static String createConstructorString(String name) {
		StringBuilder b = new StringBuilder("public ");
		b.append(name);
		b.append("(");
		b.append(IExpression.class.getName());
		b.append(" expr){this._method_container_ = expr;}");
		return b.toString();
	}

	public static String createPackageName(Class<?> c) {
		return JCodeFactory.class.getName() + "_out." + c.getName() + ".impl";
	}

	private static String createMethodString(Method m) {
		StringBuilder b = new StringBuilder();
		if(Modifier.isAbstract(m.getModifiers())){
			b.append("public ");
			b.append(m.getReturnType().getName());
			b.append(" ");
			b.append(m.getName());
			b.append("(");
			b.append(createParameterString(m.getParameters()));
			b.append(")");
			
			b.append("{try{");
			b.append(Tools.class.getName());
			b.append(".asFunction(");
			
			b.append("_method_container_.getProperty(\"");
			b.append(m.getName());
			b.append("\")).invoke(");
			b.append(createInvokeParamString(m.getParameters()));
			b.append(");");
			
			b.append("}catch(");
			b.append(CclException.class.getName());
			b.append(" e){throw new RuntimeException(e);}}");
			
			return b.toString() + "";
		}
		return "";
	}

	private static Object createInvokeParamString(Parameter[] params) {
		if(params.length == 0) return "";
		StringBuilder b = new StringBuilder();
		b.append("new ");
		b.append(Expression.class.getName());
		b.append("(");
		b.append(params[0].getName());
		b.append(")");
		for(int i = 1; i < params.length; i++){
			b.append(",");
			b.append("new ");
			b.append(Expression.class.getName());
			b.append("(");
			b.append(params[i].getName());
			b.append(")");
		}
		return b.toString();
	}

	private static String createParameterString(Parameter[] params) {
		if(params.length == 0) return "";
		StringBuilder b = new StringBuilder(params[0].getType().getName());
		b.append(" ");
		b.append(params[0].getName());
		for(int i = 1; i < params.length; i++){
			b.append(",");
			b.append(params[i].getType().getName());
			b.append(" ");
			b.append(params[i].getName());
		}
		return b.toString();
	}
	
}
