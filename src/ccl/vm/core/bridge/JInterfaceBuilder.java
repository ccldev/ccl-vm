package ccl.vm.core.bridge;

import java.lang.reflect.Proxy;

public class JInterfaceBuilder {
	
	public static Class<?> makeNormalClass(Class<?> iface){
		return Proxy.getProxyClass(iface.getClassLoader(), new Class[]{iface});
	}
	
}
