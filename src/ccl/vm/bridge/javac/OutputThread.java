package ccl.vm.bridge.javac;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class OutputThread extends Thread {
	
	private PrintStream out;
	private InputStream in;

	public OutputThread(InputStream in, PrintStream out) {
		this.in = in;
		this.out = out;
	}

	public void run(){
		Scanner s = new Scanner(in);
		while(s.hasNextLine()){
			out.println(s.nextLine());
		}
		s.close();
	}
	
}
