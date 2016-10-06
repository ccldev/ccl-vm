package ccl.vm.core;

public class ErrorMarker {

	public Object data;

	public ErrorMarker(Object err) {
		this.data = err;
	}
	
	public String toString(){
		return "!!errorMarker!!";
	}

}
