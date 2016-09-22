package ccl.vm.core.bridge;

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
import ccl.iface.IFunction;
import ccl.iface.IType;
import ccl.vm.core.Expression;
import ccl.vm.core.bridge.codefactory.JClassFactory;
import ccl.vm.core.bridge.codefactory.JCodeFactory;
import ccl.vm.core.types.NativeType;
import ccl.vm.err.NoSuchNativePropertyException;

public class JClassExpression extends Expression<JClass> implements IFunction<Object, Object>{

	private ArrayList<String> nameList;
	
	private static File outputDirectory;
	
	private static ArrayList<String> classList = new ArrayList<String>();
	
	public static File getOutputDirectory() {
		return outputDirectory;
	}

	public static void setOutputDirectory(File outputDirectory) {
		JClassExpression.outputDirectory = outputDirectory;
	}

	public JClassExpression(JClass c) throws NoSuchNativePropertyException {
		super(c);
		nameList = new ArrayList<String>();
		initMethods(c.getMethods());
		initFields(c.getFields());
	}

	private void initMethods(Method[] methods) throws NoSuchNativePropertyException {
		for(int i = 0; i < methods.length; i++){
			Method m = methods[i];
			String name = m.getName();
			if(!nameList.contains(name)){
				register(name);
			}
		}
	}

	private void register(String name) throws NoSuchNativePropertyException {
		Field f = getValue().getField(name);
		Method[] methods = getValue().getMethods(name);
		setProperty(name, new JProperty(null, methods, f));
		nameList.add(name);
	}

	private void initFields(Field[] fields) throws NoSuchNativePropertyException {
		for(int i = 0; i < fields.length; i++){
			Field f = fields[i];
			String name = f.getName();
			if(!nameList.contains(name)){
				register(name);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public IExpression<Object> invoke(IExpression<? extends Object>... parameters)
			throws CclException {
		if(getValue().getNormal().isInterface())
			try {
				return JBridgeTool.createInterfaceInstance(getValue().getNormal(), parameters[0]);
			} catch (NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				throw new CclException("Unable to create interface instance!", e);
			}
		if(Modifier.isAbstract(getValue().getNormal().getModifiers())){
			try {
				return abstractImpl(parameters[0]);
			} catch (Exception e) {
				throw new CclException("Unable to implement abstract class", e);
			}
		}
		return JBridgeTool.invoke(JBridgeTool.filter(getValue().getConstructors(), parameters.length), parameters);
	}

	private IExpression<Object> abstractImpl(IExpression<? extends Object> parameters) throws Exception {
		if(outputDirectory == null) throw new NullPointerException("output directory is null! set using setOutputDirectory");
		if(!classList.contains(getValue().getNormal())){
			buildClass(getValue().getNormal());
		}
		ClassLoader cl = new URLClassLoader(new URL[]{outputDirectory.toURI().toURL()});
		Class<?> clazz = cl.loadClass(JCodeFactory.createPackageName(getValue().getNormal()) + "." + JClassFactory.getLastName());
		Constructor<?> cr = clazz.getConstructor(IExpression.class);
		Object instance = cr.newInstance(parameters);
		return new Expression<Object>(instance);
	}

	private void buildClass(Class<?> toImpl) throws IOException, CclException {
		JClassFactory.implement(outputDirectory, toImpl);
	}

}
