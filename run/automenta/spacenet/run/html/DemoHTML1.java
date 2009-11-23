package automenta.spacenet.run.html;


/** uses LoboBrowser to parse HTML */
public class DemoHTML1 /*extends ProcessBox*/ {

//	private static final Logger logger = Logger.getLogger(DemoHTML1.class);
//
//	String TEST_URI = "http://www.automenta.com";
//
//	public static class Image {
//		protected UURI uri;
//		protected int width, height;
//		protected String alt;
//
//		public Image(Node node) {
//			super();
//
//			this.uri = new UURI(node.getAttributes().getNamedItem("src").getNodeValue());
//
//			try {
//				this.width = Integer.parseInt(node.getAttributes().getNamedItem("width").getNodeValue());
//			}
//			catch (Exception e) {
//				this.width = 1;
//			}
//
//			try {
//				this.height = Integer.parseInt(node.getAttributes().getNamedItem("height").getNodeValue());
//			}
//			catch (Exception e) {
//				this.height = 1;
//			}
//
//			try {
//				this.alt = node.getAttributes().getNamedItem("alt").getNodeValue();
//			}
//			catch (Exception e) {
//				this.alt = "";
//			}
//
//		}
//
//		public Image(UURI uri, int width, int height, String alt) {
//			super();
//			this.uri = uri;
//			this.width = width;
//			this.height = height;
//			this.alt = alt;
//		}
//
//		public String getAlt() {
//			return alt;
//		}
//		public int getHeight() {
//			return height;
//		}
//		public int getWidth() {
//			return width;
//		}
//
//		public UURI getUri() {
//			return uri;
//		}
//
//	}
//
//	public class ImageRect extends Rect {
//
//		public ImageRect(Image i) {
//			super();
//
//			add(new Rect(new BitmapSurface(i.getUri())));
//
//			double a = ((double)i.getHeight()) / ((double)i.getWidth());
//
//			getAspectXY().set(a);
//		}
//	}
//
//	@Override public void run() {
//		try {
//			UserAgentContext uacontext = new SimpleUserAgentContext() {
//				@Override
//				public String getUserAgent() {
//					//return super.getUserAgent();
//					//return "Mozilla/5.0 (X11; U; Linux i686; pl-PL; rv:1.9.0.2) Gecko/20121223 Ubuntu/9.25 (jaunty) Firefox/3.8";
//					return "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.1b3) Gecko/20090327 GNU/Linux/x86_64 Firefox/3.1";
//				}
//			};
//			System.out.println(uacontext.getUserAgent());
//
//			DocumentBuilderImpl builder = new DocumentBuilderImpl(uacontext);
//			URL url = new URL(TEST_URI);
//			InputStream in = url.openConnection().getInputStream();
//
//			double x = 0;
//			double y = 0;
//			try {
//				Reader reader = new InputStreamReader(in, "ISO-8859-1");
//				InputSourceImpl inputSource = new InputSourceImpl(reader, TEST_URI);
//				Document d = builder.parse(inputSource);
//				HTMLDocumentImpl document = (HTMLDocumentImpl) d;
//				HTMLCollection images = document.getImages();
//				int length = images.getLength();
//				for(int i = 0; i < length; i++) {
//					Node imgNode = images.item(i);
//					Image img = new Image(imgNode);
//
//					System.out.println(img.getUri());
//
//					Box w = add(new Window().move(x, y));
//					w.add(new ImageRect(img));
//
//					x += 1;
//					if (x > 10) {
//						x = 0;
//						y++;
//					}
//				}
//			} finally {
//				in.close();
//			}
//		}
//		catch (Exception e) {
//			logger.error(e);
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) {
//		new DefaultJmeWindow(new DemoHTML1());
//	}
}
