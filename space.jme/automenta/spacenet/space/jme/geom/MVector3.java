package automenta.spacenet.space.jme.geom;

import automenta.spacenet.var.vector.IVector3;
import automenta.spacenet.var.vector.Vector3;

import com.jme.math.Vector3f;


/** Vector3 adapter to JMonkeyEngine's Vector3f */
public class MVector3 extends Vector3f implements IVector3 {

	private Vector3 v3;
	
	public MVector3() {
		super();
		v3 = new Vector3();
	}
	public MVector3(Vector3 v) {
		this();
		set(v.z(), v.y(), v.z());
	}
	public MVector3(Vector3f v) {
		this();
		set(v);
	}

//	@Override
//	public void addWatch(Watch w) {
//		v3.addWatch(w);
//	}
//	
//	@Override
//	public void dispose() {
//		v3.dispose();
//	}
//
//	@Override
//	public ValueType getType() {
//		return v3.getType();
//	}

//	@Override
//	public UUID getUUID() {
//		return v3.getUUID();
//	}
//	@Override
//	public void removeWatch(Watch w) {
//		v3.removeWatch(w);
//	}
	
	@Override
	public void set(double x, double y, double z) {
		set((float)x, (float)y, (float)z);
	}

	@Override
	public Vector3f set(float x, float y, float z) {
		setAndNotify(x,y,z);
		return this;
	}

	@Override
	public void set(int index, float value) {
		switch (index) {
		case 0: setX(value);break;
		case 1: setY(value);break;
		case 2: setZ(value); break;
		}
	}

	public void set(Vector3 v) {
		set(v.x(), v.y(), v.z());		
	}

	@Override
	public Vector3f set(Vector3f v) {
		return set(v.getX(), v.getY(), v.getZ());
	}

	private void setAndNotify(float x, float y, float z) {
		super.set(x,y,z);
		v3.set(x,y,z);
	}

	@Override	public void setX(float x) {	set(x, getY(), getZ());	}

	@Override	public void setY(float y) {	set(getX(), y, getZ());	}
	
	@Override	public void setZ(float z) {	set(getX(), getY(), z);	}
	
	@Override public double x() {	return v3.x();	}
	@Override public double y() {	return v3.y();	}	
	@Override public double z() {	return v3.z();	}

}
