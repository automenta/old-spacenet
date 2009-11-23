/**
 * 
 */
package automenta.spacenet.var.index;

import automenta.spacenet.ID;


/** a found item */
public interface Found<O> extends ID {
	
//	/** ID code, or URI */
//	public String getID();

	/** referent object */
	public O getObject();

	/** name */
	public String getName();

	/** string of tags, separated by commas */
	public String getTags();

	/** description or summary */
	public String getDescription();

	/** strength, mass, weight, saliency */
	public double getStrength();

	
}