package automenta.spacenet.os;


import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.jme.video.JmeNode;
import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Spatial;
import com.jme.scene.Spatial.CullHint;
import com.jme.scene.shape.GeoSphere;
import com.jme.util.geom.BufferUtils;


public class SkyUtil {

//	@Override public void start(Scope c) {
//		super.start(c);
//		
//		Jme jme = getThe(Jme.class);
//		
//		
//		//bg.add(new Box().withSurface(Color.Blue).move(-0.25,0,0));
////		Quad q = new Quad("x", 25, 15);
////		q.getLocalTranslation().set(0,0,-10);
////		q.setRandomColors();
////		q.updateRenderState();
////		
////		bgNode.attachChild(q);
//		
////		jme.doLater(new Runnable() {
////
////			@Override public void run() {
////				SkyNode sky = new SkyNode(10, 10, 10);
////				
//////				for (Face f : Face.values()) {
//////					Quad q = sky.getFace(f);
//////					q.setRandomColors();
//////				}
////				
////				System.out.println( sky.getFace(Face.North).getColorBuffer(). );
////				
////				sky.updateRenderState();
////				
////				bgNode.attachChild(sky);				
////			}
////			
////		});
//
////		jme.doLater(new Runnable() {
////			@Override public void run() {
////				Sphere s = new Sphere("x", 3, 3, 5.0f);
////				s.setCullHint(CullHint.Never);
////				s.setRandomColors();
////				s.setLightCombineMode(LightCombineMode.Off);
////				bgNode.attachChild(s);
////				bgNode.updateGeometricState(0.0f, true);
////				bgNode.updateRenderState();
////			}			
////		});
////
//		
//		
//		Jme.doLater(new Runnable() {
//			@Override public void run() {
//				JmeNode bgNode = getJme().getSkyNode();
//				Scope bg = getJme().getSkyNode().getNode();
//
//				//Dome s = new Dome("x", 3, 3, 5.0f);
//				GeoSphere s = new GeoSphere("x", false, 2);
//				s.getLocalScale().set(2f,2f,2f);
//				s.setCullHint(CullHint.Never);
//
//				s.setSolidColor(ColorRGBA.black);
//				
//				//soccerBallPattern(s);
//				
//				shadeSphereByDistance(s, new Vector3f(0, -3f, 0), ColorRGBA.green);
//				shadeSphereByDistance(s, new Vector3f(0, 1f, 0), ColorRGBA.orange);
//
//				shadeSphereByDistance(s, new Vector3f(4, 0, 0), ColorRGBA.magenta);
//
//				shadeSphereByDistance(s, new Vector3f(-4, 0, 0), ColorRGBA.blue);
//				
//				//s.setLightCombineMode(LightCombineMode.Off);
//				s.setModelBound(new BoundingBox());
//				s.updateModelBound();
//				bgNode.attachChild(s);
//				bgNode.updateGeometricState(0.0f, true);
//				bgNode.setModelBound(new BoundingBox());
//				bgNode.updateModelBound();
//				bgNode.updateRenderState();
//			}			
//		});
//
//		
//	}

	public static Spatial newRainbowSkyBox() {
		GeoSphere s = newSkyBox();

		//soccerBallPattern(s);
		
		shadeSphereByDistance(s, new Vector3f(0, -3f, 0), ColorRGBA.green);
		shadeSphereByDistance(s, new Vector3f(0, 1f, 0), ColorRGBA.orange);

		shadeSphereByDistance(s, new Vector3f(4, 0, 0), ColorRGBA.magenta);

		shadeSphereByDistance(s, new Vector3f(-4, 0, 0), ColorRGBA.blue);

		return s;
	}
	
	public static GeoSphere newSkyBox() {

		//Dome s = new Dome("x", 3, 3, 5.0f);
		GeoSphere s = new GeoSphere("x", true, 3);
		s.getLocalScale().set(2f,2f,2f);
		s.setCullHint(CullHint.Never);

		s.setSolidColor(ColorRGBA.black);
				
		//s.setLightCombineMode(LightCombineMode.Off);
		s.setModelBound(new BoundingBox());
		s.updateModelBound();
		
		return s;
	}

	public static void removeSkyBox(Jme jme, Spatial skyBox) {
		JmeNode bgNode = jme.getSkyNode();
		bgNode.detachChild(skyBox);
		bgNode.updateGeometricState(0.0f, true);
		bgNode.updateModelBound();
		bgNode.updateRenderState();		
	}
	
	protected static void shadeSphereByDistance(GeoSphere s, Vector3f origin, ColorRGBA shade) {
		Vector3f[] va = BufferUtils.getVector3Array( s.getVertexBuffer() );
		
		ColorRGBA[] ca = BufferUtils.getColorArray( s.getColorBuffer() );
		for (int i = 0; i < ca.length; i++) {
			ColorRGBA c = ca[i];
			
			float dist = va[i].distance(origin);
			
			float intensity = 1.0f / (1.0f + dist);
			c.addLocal( new ColorRGBA( intensity * shade.r, intensity * shade.g, intensity * shade.b, intensity * shade.a) );
			BufferUtils.setInBuffer(c, s.getColorBuffer(), i);
		}
		
	}

	protected static void soccerBallPattern(GeoSphere s) {
		Vector3f[] va = BufferUtils.getVector3Array( s.getVertexBuffer() );
		
		ColorRGBA[] ca = BufferUtils.getColorArray( s.getColorBuffer() );
		float f = 0;
		int i = 0;
		for (ColorRGBA c : ca) {
			c.set(f, 0, 0, 0.5f);
			BufferUtils.setInBuffer(c, s.getColorBuffer(), i);
			f += 0.001f;
			i++;
		}
		
	}

	public static Spatial newGraySkyBox() {
		GeoSphere s = newSkyBox();

		shadeSphereByDistance(s, new Vector3f(0, -1f, 0), ColorRGBA.gray);
		shadeSphereByDistance(s, new Vector3f(0, 1f, 0), ColorRGBA.black);

//		shadeSphereByDistance(s, new Vector3f(0, -1f, 0), ColorRGBA.gray);
//		shadeSphereByDistance(s, new Vector3f(0, 1f, 0), ColorRGBA.white);

		return s;
	}

	
}
