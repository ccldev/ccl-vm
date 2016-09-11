package ccl.vm.core.bridge.codefactory;

import java.io.File;
import java.io.IOException;

import ccl.iface.CclException;
import ccl.iface.codefactory.ICompiler;
import ccl.vm.core.bridge.javac.JavaC;

public class DesktopJavaCompiler implements ICompiler {

	private JavaC javac;

	public DesktopJavaCompiler(String path) {
		this.javac = new JavaC(path);
	}

	@Override
	public File compile(File dir, File src, String classname) throws CclException{
		javac.setIn(dir);
		javac.setOut(dir);
		try {
			javac.run(src);
		} catch (IOException e) {
			throw new CclException("Javac run error!", e);
		}
		File ret = new File(src.getPath().substring(0, src.getPath().length() - 4) + "class");
		if(!ret.exists()) throw new CclException("CLASS COMPILE EXCEPTION! " + ret + " NOT FOUND!");
		return ret;
	}

}
