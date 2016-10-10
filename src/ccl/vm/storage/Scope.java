package ccl.vm.storage;

import ccl.iface.IExpression;

public class Scope {
	
	IExpression[] temp;
	IExpression[] variables;
	String[] names;
	boolean[] used;
	
	int getSize(){
		return names.length;
	}

	private Scope child;
	Scope parent;
	
	public Scope(int size){
		temp = new IExpression[size];
		variables = new IExpression[size];
		names = new String[size];
		used = new boolean[size];
	}

	public Scope chain() {
		this.child = new Scope(temp.length);
		this.child.setParent(this);
		return this.child;
	}

	private void setParent(Scope scope) {
		this.parent = scope;
	}

	public Scope close() {
		clean();
		if(this.parent == null) throw new IllegalStateException("Unable to close this Scope!");
		return this.parent;
	}

	private void clean() {
		temp = null;
		variables = null;
		names = null;
		used = null;
		
		this.child = null;
	}

	public boolean hasParent() {
		return parent != null;
	}
	
}
