package ccl.vm.bridge.javac;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaC {
	
	public static List<File> globalClasspath = new ArrayList<File>();
	
	private String path;
	private File out, in, classpath[];

	public JavaC(String path){
		this.path = path;
		this.classpath = globalClasspath.toArray(new File[0]);
	}
	
	public String getPath() {
		return path;
	}

	public File getOut() {
		return out;
	}

	public File getIn() {
		return in;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setOut(File out) {
		this.out = out;
	}

	public void setIn(File in) {
		this.in = in;
	}

	private ArrayList<String> createArguments(){
		ArrayList<String> args = new ArrayList<String>();
		args.add(path);
		if(in != null){
			args.add("-sourcepath");
			args.add(in.getAbsolutePath());
		}
		if(out != null){
			args.add("-d");
			args.add(out.getAbsolutePath());
		}
		if(classpath != null){
			if(classpath.length > 0){
				addClasspathArguments(args);
			}
		}
		return args;
	}
	
	private void addClasspathArguments(List<String> args) {
		StringBuilder sb = new StringBuilder(classpath[0].toString());
		for(int i = 1; i < classpath.length; i++){
			sb.append(";");
			sb.append(classpath[i].getAbsolutePath());
		}
		args.add("-cp");
		args.add(sb.toString());
	}

	public void run(File source) throws IOException{
		ArrayList<String> args = createArguments();
		args.add(source.getAbsolutePath());
		Process p = Runtime.getRuntime().exec(args.toArray(new String[0]));
		new OutputThread(p.getErrorStream(), System.err).start();
		new OutputThread(p.getInputStream(), System.out).start();
		while(p.isAlive());
	}
	
}
