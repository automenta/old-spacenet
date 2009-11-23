package automenta.spacenet.plugin.mvel.action;

import automenta.spacenet.plugin.mvel.MVELExpression;



//@IncompleteFeature("watch an arbitrary number of (input) values involved in the expression")
abstract public class DynamicExpression extends MVELExpression {

//	private Object previousValue = null;
//	private IfChanges firstObjectWatch;
//	@Deprecated private Object firstObject;
//
//	public DynamicExpression(Object context, String expression, String firstObjectName, Object firstObject) {
//		super(context, expression, firstObjectName, firstObject);
//		this.firstObject = firstObject;
//
//	}
//
//	private void reevaluate() {
//		Object currentValue = evaluate();
//		if (previousValue == null) {
//			afterExpressionChanges(null, currentValue);
//		}
//		else if (!previousValue.equals(currentValue)){
//			afterExpressionChanges(previousValue, currentValue);
//		}
////		else {
////			System.out.println("same: " + currentValue);
////		}
//
//		previousValue = currentValue;
//	}
//
////
////	@Override
////	public void whenStarted(AbstractNode superNode) {
////
////		if (firstObject instanceof ObjectVar) {
////			firstObjectWatch = new IfChanges((ObjectVar)firstObject) {
////				@Override public void afterValueChanged(ObjectVar o, Object previous, Object next) {
////					reevaluate();
////				}
////			};
////			superNode.put(firstObjectWatch, firstObjectWatch);
////		}
////
////		reevaluate();
////
////	}
////
////	@Override public void whenStopped(AbstractNode context) {
////		if (firstObjectWatch!=null) {
////			firstObjectWatch.whenStopped(this);
////			firstObjectWatch = null;
////		}
////	}
//
//	abstract public void afterExpressionChanges(Object previousValue, Object currentValue);
}
