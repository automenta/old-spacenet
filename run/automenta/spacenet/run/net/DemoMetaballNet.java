package automenta.spacenet.run.net;

import automenta.spacenet.space.geom3.MetaballBox;
import automenta.spacenet.space.geom3.Sphere;
import automenta.spacenet.space.object.net.NetBox;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.net.Link;
import automenta.spacenet.var.net.Net;
import automenta.spacenet.var.net.Node;

public class DemoMetaballNet {

//	public static class NodeSphere extends Sphere {
//
//		public NodeSphere(Node node) {
//			super();
//		}
//
//	}
//	public static class LinkSphere extends Sphere {
//
//		public LinkSphere(Link link) {
//			super();
//		}
//
//	}
//
//	public static class MetaballNet<N,L> extends NetBox<N,L> {
//
//		private ListVar<Sphere> points;
//
//		@Override
//		public void start() {
//			super.start();
//
//			points = new ListVar<Sphere>();
//
//			add(new MetaballBox(points));
//		}
//
//		@Override
//		protected void whenLinkAdded(Net<N, L> net, Link<N, L> link) {
//			points.add(new LinkSphere(link));
//		}
//
//		@Override
//		protected void whenLinkRemoved(Net<N, L> net, Link<N, L> link) {
//
//		}
//
//		@Override
//		protected void whenNodeAdded(Net<N, L> net, Node<N, L> node) {
//			points.add(new NodeSphere(node));
//		}
//
//		@Override
//		protected void whenNodeRemoved(Net<N, L> net, Node<N, L> node) {
//
//		}
//
//	}
	
}
