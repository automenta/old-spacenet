package automenta.spacenet.space.jme.video.filter;


import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.jme.video.JmeNode;
import com.jme.math.Plane;
import com.jme.math.Vector3f;
import com.jme.renderer.pass.Pass;
import com.jme.scene.Node;
import com.jme.scene.Spatial.CullHint;
import com.jme.scene.shape.Quad;
import com.jmex.effects.water.WaterRenderPass;


public class WaterFilter extends PassAction {

	private WaterRenderPass waterEffectRenderPass;
	private Quad waterQuad;
	private Node rootNode;
	private Node reflectedNode;
	private boolean freezeMovement = false;
	private JmeNode skyNode;

	public WaterFilter(Node rootNode, Node reflectedNode, JmeNode skyNode) {
		this.rootNode = rootNode;
		this.reflectedNode = reflectedNode;
		this.skyNode = skyNode;
	}
	
	@Override
	public void start(Jme jme) {
		super.start(jme);
		
        waterEffectRenderPass = new WaterRenderPass(getCamera(), 3, true, true);
        // set equations to use z axis as up
        waterEffectRenderPass.setWaterPlane(new Plane(new Vector3f(0.0f, 0.0f,
                1.0f), 0.0f));
        waterEffectRenderPass.setTangent(new Vector3f(1.0f, 0.0f, 0.0f));
        waterEffectRenderPass.setBinormal(new Vector3f(0.0f, 1.0f, 0.0f));

        waterQuad = new Quad("waterQuad", 100, 100);
        waterQuad.setCullHint(CullHint.Never);

        waterEffectRenderPass.setWaterEffectOnSpatial(waterQuad);
        rootNode.attachChild(waterQuad);


//        waterEffectRenderPass.setWaterColorStart(new ColorRGBA(1,0,0,1));
//        waterEffectRenderPass.setWaterColorEnd(new ColorRGBA(1,1,0,1));
        
        waterEffectRenderPass.setReflectedScene(reflectedNode);
        //waterEffectRenderPass.setSkybox(skyNode);
        
        waterQuad.updateRenderState();
        

	}
	
	float t = 0;
	Vector3f tmpVec = new Vector3f();
	
	@Override
	public void forward(float tpf) {
        if (!freezeMovement ) {
//            waterQuad.getLocalTranslation().set(0.0f, 0.0f,
//                    FastMath.sin(t * 0.2f) * 50.0f);
//            waterQuad.getLocalRotation().fromAngles(
//                    FastMath.sin(t * 0.5f) * 1.0f,
//                    FastMath.sin(t * 0.5f) * 0.8f, 0.0f);
//            waterQuad.getLocalRotation().fromAngles(t,0,0);

//            tmpVec.set(0, 0, 1);
//            waterQuad.getLocalRotation().multLocal(tmpVec);
//            waterEffectRenderPass.getNormal().set(tmpVec);

            float dist = waterEffectRenderPass.getNormal().dot(
                    waterQuad.getLocalTranslation());// not needed, allways
                                                        // length 1 - "/
                                                        // waterEffectRenderPass.getNormal().length();"
            waterEffectRenderPass.setWaterHeight(dist*5.0f);
            
            waterQuad.updateGeometricState(0.0f, true);
        }

        t += tpf;
		super.forward(tpf);
	}
	
	@Override
	public Pass getPass() {
		return waterEffectRenderPass;
	}

    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    
}
