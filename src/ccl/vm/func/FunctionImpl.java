package ccl.vm.func;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import ccl.iface.CclException;
import ccl.iface.IExpression;
import ccl.iface.IFunction;
import ccl.iface.exec.CclError;
import ccl.iface.exec.IExecuter;
import ccl.iface.exec.IExecuterFactory;
import ccl.vm.core.Expression;
import ccl.vm.core.Undefined;

public class FunctionImpl implements IFunction {

	private IExecuterFactory factory;
	private URL url;

	public FunctionImpl(IExecuterFactory factory, String relpath) throws CclException {
		this.factory = factory;
		try {
			this.url = new URL(relpath);
		} catch (MalformedURLException e) {
			try {
				this.url = new File(relpath).toURI().toURL();
			} catch (MalformedURLException e1) {
				throw new CclException("UNABLE TO CREATE FunctionImpl INSTANCE!", e1);
			}
		}
	}
	
	@Override
	public IExpression invoke(IExpression... parameters)
			throws CclException {
		InputStream s;
		try {
			s = url.openStream();
		} catch (IOException e) {
			return Expression.err(e);
		}
		try {
			IExecuter exec = factory.create();
			exec.putParameters(parameters);
			exec.act(s);
			IExpression ret = exec.getReturn();
			if(ret == null) return new Expression(new Undefined());
			else return ret;
		} catch (CclError e) {
			throw new CclException(e);
		}
	}

}
