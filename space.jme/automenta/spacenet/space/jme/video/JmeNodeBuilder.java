package automenta.spacenet.space.jme.video;

/** adapts objects to JME spatials */
public interface JmeNodeBuilder {

	Object getAddedSpatial(JmeNode jmeNode, Object v);
	Object getRemovedSpatial(JmeNode jmeNode, Object v);

}
