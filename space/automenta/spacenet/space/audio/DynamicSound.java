package automenta.spacenet.space.audio;

import java.nio.FloatBuffer;

import automenta.spacenet.var.vector.Vector3;

/** sound with samples generated dynamically */
abstract public class DynamicSound extends Sound {
	
	private Vector3 worldPosition = new Vector3();

	abstract public int forward(double t, double secondsPerSample, FloatBuffer db);

	public Vector3 getWorldPosition() {
		return worldPosition;
	}
	
}
