package automenta.spacenet.space.dynamic.vector;

import automenta.spacenet.Scope;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.dynamic.DynamicDouble;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;

public class DynamicVectorSet extends Repeat<Scope> {

	//ListVar<ConstantVelocityVector3> vector3List = ListVar.newLinkedList(ConstantVelocityVector3.class);
	ListVar<ExponentialVelocityVector2> vector2List = new ListVar();
	ListVar<ConstantVelocityVector3> vector3List = new ListVar();
	ListVar<DynamicDouble> doubleList = new ListVar();
	private double updatePeriod;
	
	public DynamicVectorSet(double updatePeriod) {
		this.updatePeriod = updatePeriod;
	}
	
	public ConstantVelocityVector3 newVector3(double x, double y, double z, double speed) {
		return newVector3(x,y,z, new DoubleVar(speed));
	}
	
	public ConstantVelocityVector3 newVector3(double x, double y, double z, DoubleVar speed) {
		ConstantVelocityVector3 v = new ExponentialVelocityVector3(x, y, z, speed) {
			@Override public void dispose() {
				vector3List.remove(this);
				
				super.dispose();
			}
		};
		vector3List.add(v);
		return v;
	}

	public Vector2 newVector2(double x, double y, double speed) {
		ExponentialVelocityVector2 v = new ExponentialVelocityVector2(x, y, speed) {
			@Override public void dispose() {
				vector2List.remove(this);
				
				super.dispose();
			}
		};
		vector2List.add(v);
		return v;
	}

	public DynamicDouble newDouble(double v, double speed) {
		DynamicDouble d = new DynamicDouble(v, speed) {
			@Override public void dispose() {
				doubleList.remove(this);
				
				super.dispose();
			}
			
		};
		doubleList.add(d);
		return d;
	}
	
	
	@Override public double repeat(double t, double dt) {
		for (ConstantVelocityVector3 v : vector3List) {
			v.forward(dt);
		}
		for (ExponentialVelocityVector2 v : vector2List) {
			v.forward(dt);
		}
		for (DynamicDouble d : doubleList) {
			d.forward(dt);
		}
		return updatePeriod;
	}



	
}
