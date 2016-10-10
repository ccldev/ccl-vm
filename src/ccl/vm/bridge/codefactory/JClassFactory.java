package ccl.vm.bridge.codefactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import ccl.iface.CclException;

public class JClassFactory {
	
	public static boolean enabled = false;
	
	private static ArrayList<Class<?>> classes = new ArrayList<>();
	private static ArrayList<Integer> counters = new ArrayList<>();
	private static String lastName;
	
	public static String getLastName() {
		return lastName;
	}

	public static File implement(File location, Class<?> toImpl) throws IOException, CclException{
		if(!enabled){
			throw new RuntimeException("abstract class implementation not enabled!");
		}
		if(Modifier.isAbstract(toImpl.getModifiers())){
			String name = createName(toImpl);
			String code = JCodeFactory.createClassSource(toImpl, name);
			return JCompiler.compile(location, code, name, JCodeFactory.createPackageName(toImpl));
		}
		throw new RuntimeException(toImpl.toString() + " is not abstract!");
	}

	private static String createName(Class<?> toImpl) {
		if(!classes.contains(toImpl)){
			classes.add(toImpl);
			counters.add(0);
			if(!classes.contains(toImpl)) throw new RuntimeException("DOES NOT CONTAIN AFTER ADDING!");
		}
		int index = classes.indexOf(toImpl);
		int count = counters.get(index);
		counters.set(index, count);
		return lastName = toImpl.getSimpleName() + count;
	}
	
}
