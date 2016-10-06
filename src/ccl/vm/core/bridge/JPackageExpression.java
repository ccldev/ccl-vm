package ccl.vm.core.bridge;

import java.util.Arrays;
import java.util.HashMap;

import ccl.iface.CclException;
import ccl.vm.core.ErrorMarker;
import ccl.vm.core.Expression;
import ccl.vm.core.Undefined;
import ccl.vm.core.expr.ErrorExpression;
import ccl.vm.core.storage.Storage;
import ccl.vm.err.NoSuchNativePropertyException;

public class JPackageExpression extends Expression<JPackage>{
	
	private JPackage pack;
	
	static HashMap<String, JPackage> allKnownPackages = new HashMap<String, JPackage>();
	
	public static void initBasePackages(){
		Package[] packages = Package.getPackages();
		for(int i = 0; i < packages.length; i++){
			allKnownPackages.put(packages[i].getName(), new JPackage(packages[i].getName()));
			addPackageParts(packages[i].getName());
		}
		for(int i = 0; i < allKnownPackages.size(); i++){
			System.out.println(allKnownPackages.get("javax"));
		}
	}

	private static void addPackageParts(String name) {
		String[] split = name.split("\\.");
		String start = split[0];
		allKnownPackages.put(start, new JPackage(start));
		for(int i = 1; i < split.length; i++){
			start += "." + split[i];
			allKnownPackages.put(start, new JPackage(start));
		}
	}

	public JPackageExpression(JPackage pack){
		this.pack = pack;
	}
	
	private JPackage getPackageChild(String child){
		return allKnownPackages.get(pack.getName() + "." + child);
	}
	
	public Expression<?> getProperty(String prop) {
		JPackage pack = getPackageChild(prop);
		if(pack != null) return new JPackageExpression(pack);
		try {
			return new JClassExpression(getClassChild(prop));
		} catch (CclException e) {
			return new ErrorExpression(e);
		}
	}

	private JClass getClassChild(String child) throws CclException {
		return new JClass(pack.getName() + "." + child);
	}
	
}
