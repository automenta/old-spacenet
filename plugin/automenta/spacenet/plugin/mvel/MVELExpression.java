package automenta.spacenet.plugin.mvel;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.Logger;


/** 
 * expressions executed relative to its getObject()
 * 
 * @see MVEL : Expression Language - http://mvel.codehaus.org/Home
 * 
 *  */
public class MVELExpression<C,O> {
//	private static final Logger logger = Logger.getLogger(MVELExpression.class);
//
//
//	public VariableResolverFactory resolver;
//
//	//TODO: cache  CompiledExpression's  http://mvel.codehaus.org/Getting+Started+Guide
//
//	C context;
//	String expressionString;
//
//	private Serializable compiledExpression;
//
//	private HashMap defaultMapping;
//
////	static {
////		DataConversion.addConversionHandler(Double.class, new ConversionHandler() {
////			@Override public boolean canConvertFrom(Class c) {
////				return c.isAssignableFrom(RealVal.class);
////			}
////			@Override public Object convertFrom(Object o) {
////				return (((RealVal)o).get());
////			}
////		});
////		DataConversion.addConversionHandler(Integer.class, new ConversionHandler() {
////			@Override public boolean canConvertFrom(Class c) {
////				return c.equals(RealVal.class);
////			}
////			@Override public Object convertFrom(Object o) {
////				return  new Integer( (((RealVal)o).get()).intValue());
////			}
////		});
////		System.out.println( DataConversion.canConvert(Double.class, RealVal.class) );
////		System.out.println( DataConversion.convert(new RealVal(3.0), Double.class) );
////
////
////	}
//
//	public MVELExpression(C context, String expression) {
//		super();
//
//		setContext(context);
//		setExpression(expression);
//	}
//
//	public MVELExpression(C context, String expression, String firstObjectName, Object firstObject) {
//		this(context, expression);
//
//		defaultMapping = new HashMap(1);
//		defaultMapping.put(firstObjectName, firstObject);
//	}
//	public MVELExpression(C context, String expression, String firstObjectName, Object firstObject, String secondObjectName, Object secondObject) {
//		this(context, expression);
//
//		defaultMapping = new HashMap(2);
//		defaultMapping.put(firstObjectName, firstObject);
//		defaultMapping.put(secondObjectName, secondObject);
//	}
//
//	public C getContext() {
//		return context;
//	}
//
//	public void setContext(C context) {
//		this.context = context;
//	}
//
//	public void setExpression(String s) {
//		this.expressionString = s;
//		this.compiledExpression = MVEL.compileExpression(expressionString);
//	}
//
//	public O evaluate() {
//
//		if (resolver == null) {
//			if (defaultMapping!=null) {
//				resolver = new DefaultLocalVariableResolverFactory(defaultMapping);
//			}
//			else {
//				resolver = new DefaultLocalVariableResolverFactory();
//			}
//
//		}
//
//		return (O)MVEL.executeExpression(compiledExpression, getContext(), resolver);
//	}
//
////	public O evaluate(Map<String, Object> variables) {
////		return (O)MVEL.executeExpression(compiledExpression, getContext(), new DefaultLocalVariableResolverFactory(variables));
////	}
////
////	public O evaluate(String varName, Object varValue) {
////		Map vars = new HashMap(1);
////		vars.put(varName, varValue);
////		return (O)MVEL.executeExpression(compiledExpression, getContext(), new DefaultLocalVariableResolverFactory(vars));
////	}
//
//	//public void start() { evaluate(); }
//
//	public String getExpression() {
//		return expressionString;
//	}
//
//

}
