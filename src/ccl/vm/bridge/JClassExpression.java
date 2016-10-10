package ccl.vm.bridge;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.vm.bridge.codefactory.JClassFactory;
import ccl.vm.bridge.codefactory.JCodeFactory;
import ccl.vm.core.Expression;

public class JClassExpression extends Expression{

	private ArrayList<String> nameList;
	
	private static File outputDirectory;
	
	private static ArrayList<String> classList = new ArrayList<String>();
	
	public static File getOutputDirectory() {
		return outputDirectory;
	}

	public static void setOutputDirectory(File outputDirectory) {
		JClassExpression.outputDirectory = outputDirectory;
	}

	public JClassExpression(JClass c) {
		super(c);
		nameList = new ArrayList<String>();
		initMethods(c.getMethods());
		initFields(c.getFields());
	}

	private void initMethods(Method[] methods){
		for(int i = 0; i < methods.length; i++){
			Method m = methods[i];
			String name = m.getName();
			if(!nameList.contains(name)){
				register(name);
			}
		}
	}

	private void register(String name){
		Field f = ((JClass) getValue()).getField(name);
		Method[] methods = ((JClass) getValue()).getMethods(name);
		try {
			setProperty(name, new JProperty(name, null, methods, f));
		} catch (CclException e) {}
		nameList.add(name);
	}

	private void initFields(Field[] fields) {
		for(int i = 0; i < fields.length; i++){
			Field f = fields[i];
			String name = f.getName();
			if(!nameList.contains(name)){
				register(name);
			}
		}
	}
	
	@Override
	public IExpression invoke(IExpression... parameters)
			throws CclException {
		if(((JClass) getValue()).getNormal().isInterface())
			try {
				return JBridgeTool.createInterfaceInstance(((JClass) getValue()).getNormal(), parameters[0]);
			} catch (NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				throw new CclException("Unable to create interface instance!", e);
			}
		if(Modifier.isAbstract(((JClass) getValue()).getNormal().getModifiers())){
			try {
				return abstractImpl(parameters[0]);
			} catch (Exception e) {
				throw new CclException("Unable to implement abstract class", e);
			}
		}
		return JBridgeTool.invoke(JBridgeTool.filter(((JClass) getValue()).getConstructors(), parameters.length), parameters);
	}

	private IExpression abstractImpl(IExpression parameters) throws Exception {
		if(outputDirectory == null) throw new NullPointerException("output directory is null! set using setOutputDirectory");
		if(!classList.contains(((JClass) getValue()).getNormal())){
			buildClass(((JClass) getValue()).getNormal());
		}
		URLClassLoader cl = new URLClassLoader(new URL[]{outputDirectory.toURI().toURL()});
		Class<?> clazz = cl.loadClass(JCodeFactory.createPackageName(((JClass) getValue()).getNormal()) + "." + JClassFactory.getLastName());
		cl.close();
		Constructor<?> cr = clazz.getConstructor(IExpression.class);
		Object instance = cr.newInstance(parameters);
		return new Expression(instance);
	}

	private void buildClass(Class<?> toImpl) throws IOException, CclException {
		JClassFactory.implement(outputDirectory, toImpl);
	}

}
