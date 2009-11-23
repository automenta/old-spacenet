package automenta.spacenet.act;

public interface Action<I,O> {


	public O run(I i) throws Exception;
	
	public String getName(I i);
	
	/** Saliency / applicability; 0=doesn't apply, 1=completely applicable */
	public double getStrength(I i);
	
	//public String getDescription(I i);
    
}
