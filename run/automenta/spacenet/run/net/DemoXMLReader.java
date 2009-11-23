package automenta.spacenet.run.net;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import automenta.spacenet.act.Simultaneous;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.dynamic.SpaceFifo;
import automenta.spacenet.space.geom2.BitmapRect;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.text.TextRect;



public class DemoXMLReader  {

//    private static final Logger logger = Logger.getLogger(DemoXMLReader.class);
//
//	public void runXML(final String url) {
//		Space x = add(new Box());
//		final SpaceFifo f = new SpaceFifo(x, 700);
//
//		add(new Simultaneous() {
//			@Override public Object run() throws Exception {
//
//				SAXParser p = SAXParserFactory.newInstance().newSAXParser();
//
//				try {
//					p.parse(url, new DefaultHandler() {
//						@Override
//						public void startElement(String uri, String localName,
//								String name, Attributes attributes)
//								throws SAXException {
//							super.startElement(uri, localName, name, attributes);
//
//							System.out.println("element: " + uri + " , " + localName + " , " + name + " , " + attributes);
//
//							newSpatial(name, attributes);
//						}
//
//						double x = 0;
//						double y = 0;
//
//						private void newSpatial(String name, Attributes attributes) {
//							Rect t;
//							if (name == "img") {
//								String imgUri = attributes.getValue("src");
//								System.out.println("image: " + imgUri);
//								t = new BitmapRect(imgUri);
//								f.push(t);
//								t.move(x, y, 0);
//								x += 1.0;
//							}
//							else {
//								t = new TextRect(name);
//								f.push(t);
//								t.move(x, y, 0);
//								x += 1.0;
//							}
//							if (x > 10) {
//								y-=1.00;
//								x = 0;
//							}
//						}
//					});
//				}
//			catch (Exception e) {
//				logger.error(e);
//			}
//			return null;
//			}
//		});
//	}
//
//	@Override public void run() {
//		runXML("http://www.w3c.org");
//	}
//
//	public static void main(String[] args) {
//		new StartJmeWindowOld(new DemoXMLReader());
//	}
}
