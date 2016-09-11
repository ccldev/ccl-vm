package ccl.vm.err;

import ccl.iface.CclException;

public class InvokeException extends CclException {
	
	public InvokeException(Exception e) {
		super(e);
	}

	public InvokeException() {
		// TODO Auto-generated constructor stub
	}

	public InvokeException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 4103826517084486243L;

}
