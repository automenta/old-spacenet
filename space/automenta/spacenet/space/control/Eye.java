package automenta.spacenet.space.control;

import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;


public class Eye  {

//
//	public static void rotate(Vector3 position, Vector3 target, double dxAngle, double dyAngle) {
//		Vector3 dir = new Vector3(target);
//		dir.subtract(position);
//		dir.normalize();
//
//		dir.cartesianToSphere();
//		{
//			dir.setY(dir.y() + dxAngle);
//			dir.setZ(dir.z() + dyAngle);
//		}
//		dir.sphereToCartesian();
//
//		target.set(position.x() + dir.x(),
//				position.y() + dir.y(),
//				position.z() + dir.z()
//		);
//	}
//
//	public static void move(Vector3 position, Vector3 target, double forward) {
//		Vector3 dir = new Vector3(target);
//
//		dir.subtract(position);
//		dir.normalize();
//
//		dir.multiply(forward);
//
//		position.add(dir);
//		target.add(dir);
//	}
//
//	public static void rotateAndMove(Vector3 position, Vector3 target, DoubleVar tilt, double davX, double davY, double davZ, double dmv) {
//		Vector3 dir = new Vector3(target);
//		dir.subtract(position);
//		dir.normalize();
//
//		Vector3 dir2 = new Vector3(dir);
//
//		dir.cartesianToSphere();
//		{
//			dir.setY(dir.y() + davX);
//			dir.setZ(dir.z() + davY);
//		}
//		dir.sphereToCartesian();
//
//		dir2.multiply(dmv);
//
//		position.add(dir2);
//
//		target.set(position.x() + dir.x() + dir2.x(),
//				position.y() + dir.y() + dir2.y(),
//				position.z() + dir.z() + dir2.z()
//		);
//
//		tilt.add(davZ);
//	}
    
}
