package automenta.spacenet.plugin.java;




/** interface to java code introspection. a list of javamethodcalls would constitute a stacktrace snapshot */
public class JavaInstanceMethodCall<O>  {

//	public JavaInstanceMethodCall(O instance, Method m) {
//		this(instance, m, instance);
//	}
//
//	public JavaInstanceMethodCall(O instance, Method m, Object targetContext) {
//		super(targetContext);
//	}
//
//	@Override
//	public Action newAction() {
//		return new Action<AbstractNode>() {
//			@Override public void whenStarted(AbstractNode context) {
//				Object[] params = getParameterValuesAsObjectArray();
//
//				try {
//					method.invoke(params);
//				} catch (IllegalArgumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		};
//	}
//
//	protected Object[] getParameterValuesAsObjectArray() {
//		List<Object> parameters = new LinkedList();
//		for (Object ap : getParameters()) {
//			if (ap instanceof ActionParameter) {
//				parameters.add(((ActionParameter)ap).getValue());
//			}
//			else {
//				parameters.add(null);
//			}
//		}
//		return parameters.toArray();
//	}
//
//	@Override
//	public Action newPreviewAction() {
//		return null;
//	}
//
//	@Override
//	public Action newReverseAction() {
//		return null;
//	}
//
}
