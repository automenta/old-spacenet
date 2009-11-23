package automenta.spacenet.var.vector;

import automenta.spacenet.Variable;

public interface IVector3 extends Variable {
	
	void set(double x, double y, double z);

	double x();
	double y();
	double z();


}
