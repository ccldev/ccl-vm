package ccl.vm.core;

public class Undefined {

	public Undefined() {
		// TODO Auto-generated constructor stub
	}
	
	public String toString(){
		return "undefined";
	}
	
	public boolean equals(Object other){
		return other.getClass() == this.getClass();
	}

}
