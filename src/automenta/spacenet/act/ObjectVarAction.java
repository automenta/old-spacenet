package automenta.spacenet.act;

import automenta.spacenet.var.ObjectVar;

public interface ObjectVarAction<I,O> {
	
	public void run(I i, ObjectVar<O> o) throws Exception;
	
	public String getName(I i);
	
	/** Saliency / applicability; 0=doesn't apply, 1=completely applicable */
	public double getStrength(I i);
	
	//public String getDescription(I i);
}
