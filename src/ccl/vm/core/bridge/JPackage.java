package ccl.vm.core.bridge;

public class JPackage {

	private String pack;

	public JPackage(String pack) {
		this.pack = pack;
	}

	public String getName() {
		return pack;
	}
	
	public static JPackage find(String pack){
		return JPackageExpression.allKnownPackages.get(pack);
	}

}
