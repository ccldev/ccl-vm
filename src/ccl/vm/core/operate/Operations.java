package ccl.vm.core.operate;

import ccl.iface.IExpression;
import ccl.vm.core.Expression;

public class Operations {
	
	public static IExpression<?> lss(IExpression<?> a, IExpression<?> b) {
		Number na = (Number) a.getValue();
		Number nb = (Number) b.getValue();
		return new Expression<Boolean>(na.doubleValue() < nb.doubleValue());
	}
	public static IExpression<?> gtr(IExpression<?> a, IExpression<?> b) {
		Number na = (Number) a.getValue();
		Number nb = (Number) b.getValue();
		return new Expression<Boolean>(na.doubleValue() > nb.doubleValue());
	}
	public static IExpression<?> equ(IExpression<?> a, IExpression<?> b) {
		return new Expression<Boolean>(a.getValue() == b.getValue());
	}
	public static IExpression<?> or(IExpression<?> a, IExpression<?> b) {
		Boolean na = (Boolean) a.getValue();
		Boolean nb = (Boolean) b.getValue();
		return new Expression<Boolean>(na || nb);
	}
	public static IExpression<?> and(IExpression<?> a, IExpression<?> b) {
		Boolean na = (Boolean) a.getValue();
		Boolean nb = (Boolean) b.getValue();
		return new Expression<Boolean>(na && nb);
	}
	public static IExpression<?> not(IExpression<?> a) {
		Boolean na = (Boolean) a.getValue();
		return new Expression<Boolean>(!na);
	}
	public static Expression<?> add(IExpression<?> a, IExpression<?> b){
		Number na = (Number) a.getValue();
		Number nb = (Number) b.getValue();
		return new Expression<Number>(na.doubleValue() + nb.doubleValue());
	}
	public static IExpression<?> sub(IExpression<?> a, IExpression<?> b) {
		Number na = (Number) a.getValue();
		Number nb = (Number) b.getValue();
		return new Expression<Number>(na.doubleValue() - nb.doubleValue());
	}
	public static IExpression<?> mul(IExpression<?> a, IExpression<?> b) {
		Number na = (Number) a.getValue();
		Number nb = (Number) b.getValue();
		return new Expression<Number>(na.doubleValue() * nb.doubleValue());
	}
	public static IExpression<?> div(IExpression<?> a, IExpression<?> b) {
		Number na = (Number) a.getValue();
		Number nb = (Number) b.getValue();
		return new Expression<Number>(na.doubleValue() / nb.doubleValue());
	}
	
}
