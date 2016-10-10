package ccl.vm.storage;

import ccl.iface.IExpression;
import ccl.iface.IType;
import ccl.vm.core.Expression;

public class Scope {
	
	IExpression[] temp;
	IExpression[] variables;
	String[] names;
	boolean[] used;
	IType[] type;
	private int tempSize;
	private int varSize;
	
	public int getTempSize() {
		return temp.length;
	}

	public int getVarSize() {
		return variables.length;
	}

	private Scope child;
	Scope parent;
	
	public Scope(int varSize, int tempSize){
		temp = new Expression[tempSize];
		variables = new Expression[varSize];
		names = new String[varSize];
		used = new boolean[varSize];
		type = new IType[varSize];
		this.tempSize = tempSize;
		this.varSize = varSize;
	}

	public Scope chain() {
		this.child = new Scope(varSize, tempSize);
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
		type = null;
		tempSize = 0;
		varSize = 0;
		
		this.child = null;
	}

	public boolean hasParent() {
		return parent != null;
	}
	
}
