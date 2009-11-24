package automenta.spacenet.run.graph.depr;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.plugin.file.VirtualFile;
import automenta.spacenet.var.number.IntegerVar;
import automenta.spacenet.var.string.StringVar;


public class RandomDemoNet /*extends OldMemoryNet<Object, Object> implements StartsIn<Scope>*/ {
	
//	private static Logger logger = Logger.getLogger(RandomDemoNet.class);
//
//	VirtualFile vf;
//
//	private int numNodes;
//
//	private int numLinks;
//
//	private double generationRate;
//
//	public RandomDemoNet(int numNodes, int numLinks, double generationRate) {
//		super();
//
//		this.numNodes = numNodes;
//		this.numLinks = numLinks;
//
//		try {
//			vf = new VirtualFile("file:/", 1);
//		} catch (Exception e1) {
//			logger.error(e1);
//		}
//
//		this.generationRate = generationRate/2.0;
//
//
//	}
//
//
//	@Override public void start(Scope node) {
//		node.add(new Repeat() {
//
//			@Override public double repeat(double t, double dt) {
//				List nodeList = new LinkedList(getNodes());
//
//				addNode(newNode());
//
//				if (nodeList.size() > 3) {
//					Object src = getRandomObject(nodeList);
//					Object dst = src;
//					while (dst==src) {
//						dst = getRandomObject(nodeList);
//					}
//
//					try {
//						addLink(src, newNode(), dst);
//					}
//					catch (Exception e) { }
//				}
//
//				return 1.0 / generationRate;
//			}
//		});
//
//
//	}
//
//	private Object getRandomObject(List c) {
//		return c.get((int)(Math.random() * c.size()) );
//	}
//
//	//TODO abstract this
//	private Object newNode() {
//
//		double d = Math.random();
//		if (d < 0.2) {
//			return new IntegerVar((int)(d*100));
//		}
//		else if (d < 0.4) {
//			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//			StackTraceElement e = stackTrace[(int)(d * stackTrace.length)];
//			return new StringVar(e.toString());
//		}
//		else if (d < 0.6) {
//			ListVar l = new ListVar();
////			for (int i = 0; i < listElements; i++) {
////				//l.add(newNode());
////			}
//			return l;
//		}
//		else if (d < 0.8) {
//			MapVar l = new MapVar();
////			for (int i = 0; i < listElements; i++) {
////				//l.put(newNode(), newNode());
////			}
//			return l;
//		}
//		else {
//			if (vf!=null) {
//				return getRandomObject(vf);
//			}
//			else {
//				return new StringVar("=)");
//			}
//		}
//	}

}
