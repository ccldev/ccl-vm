package ccl.vm.bridge.codefactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ccl.iface.CclException;
import ccl.iface.codefactory.ICompiler;

public class JCompiler {
	
	private static ICompiler compiler;

	public static File compile(File location, String code, String name, String pack) throws IOException, CclException {
		if(compiler == null) throw new RuntimeException("java compile not enabled! (use " + JCompiler.class.getName() + ".setCompiler to enable)");
		File f = new File(location.getAbsolutePath() + "/" + asPath(pack));
		f.mkdirs();
		File jsource = new File(f.getAbsolutePath() + "/" + name + ".java");
		FileWriter out = new FileWriter(jsource);
		out.write(code);
		out.close();
		return compiler.compile(location, jsource, pack + "." + name);
	}

	private static String asPath(String pack) {
		return pack.replaceAll("\\.", "/");
	}
	
	public static void setCompiler(ICompiler compiler){
		JCompiler.compiler = compiler;
	}

}
