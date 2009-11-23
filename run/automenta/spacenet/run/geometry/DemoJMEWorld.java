package automenta.spacenet.run.geometry;


import automenta.spacenet.space.jme.video.JmeNode;


public class DemoJMEWorld /*extends JmeNode*/ {

//    public DemoJMEWorld() {
//        super()
//    }
//
//
//
//
//	@Override
//	protected void startJme(JmeNode parent) {
//		super.startJme(parent);
//
//		Torus torus = new Torus("Torus", 50, 50, 10, 20);
//		torus.setLocalTranslation(new Vector3f(50, -5, 20));
//		TextureState ts = getJme().getDisplaySystem().getRenderer().createTextureState();
//		Texture t0 = TextureManager.loadTexture(
//				TestBloom.class.getClassLoader().getResource(
//						"jmetest/data/images/Monkey.jpg"),
//						Texture.MinificationFilter.Trilinear,
//						Texture.MagnificationFilter.Bilinear);
//		Texture t1 = TextureManager.loadTexture(
//				TestBloom.class.getClassLoader().getResource(
//						"jmetest/data/texture/north.jpg"),
//						Texture.MinificationFilter.Trilinear,
//						Texture.MagnificationFilter.Bilinear);
//		t1.setEnvironmentalMapMode(Texture.EnvironmentalMapMode.SphereMap);
//		ts.setTexture(t0, 0);
//		ts.setTexture(t1, 1);
//		ts.setEnabled(true);
//		torus.setRenderState(ts);
//		attachSpatials(torus);
//
//		ts = getJme().getDisplaySystem().getRenderer().createTextureState();
//		t0 = TextureManager.loadTexture(
//				TestBloom.class.getClassLoader().getResource(
//						"jmetest/data/texture/wall.jpg"),
//						Texture.MinificationFilter.Trilinear,
//						Texture.MagnificationFilter.Bilinear);
//		t0.setWrap(Texture.WrapMode.Repeat);
//		ts.setTexture(t0);
//
//		Box box = new Box("box1", new Vector3f(-10, -10, -10), new Vector3f(10, 10, 10));
//		box.setLocalTranslation(new Vector3f(0, -7, 0));
//		box.setRenderState(ts);
//		attachSpatials(box);
//
//		box = new Box("box2", new Vector3f(-5, -5, -5), new Vector3f(5, 5, 5));
//		box.setLocalTranslation(new Vector3f(15, 10, 0));
//		box.setRenderState(ts);
//		attachSpatials(box);
//
//		box = new Box("box3", new Vector3f(-5, -5, -5), new Vector3f(5, 5, 5));
//		box.setLocalTranslation(new Vector3f(0, -10, 15));
//		box.setRenderState(ts);
//		attachSpatials(box);
//
//		box = new Box("box4", new Vector3f(-5, -5, -5), new Vector3f(5, 5, 5));
//		box.setLocalTranslation(new Vector3f(20, 0, 0));
//		box.setRenderState(ts);
//		attachSpatials(box);
//
//		box = new Box("box5", new Vector3f(-50, -2, -50), new Vector3f(50, 2, 50));
//		box.setLocalTranslation(new Vector3f(0, -15, 0));
//		box.setRenderState(ts);
//		box.setModelBound(new BoundingBox());
//		box.updateModelBound();
//		attachSpatials(box);
//
//		ts = getJme().getDisplaySystem().getRenderer().createTextureState();
//		t0 = TextureManager.loadTexture(
//				TestBloom.class.getClassLoader().getResource(
//						"jmetest/data/texture/cloud_land.jpg"),
//						Texture.MinificationFilter.Trilinear,
//						Texture.MagnificationFilter.Bilinear);
//		t0.setWrap(Texture.WrapMode.Repeat);
//		ts.setTexture(t0);
//
//		box = new Box("floor", new Vector3f(-1000, -10, -1000), new Vector3f(1000, 10, 1000));
//		box.setLocalTranslation(new Vector3f(0, -100, 0));
//		box.setRenderState(ts);
//		box.setModelBound(new BoundingBox());
//		box.updateModelBound();
//		attachSpatials(box);
//
//	}
//
//	public static void main(String[] args) {
//		new DefaultJmeWindow(new DemoJMEWorld());
//	}
}
