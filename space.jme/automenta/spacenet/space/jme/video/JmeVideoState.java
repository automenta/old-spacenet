/**
 * 
 */
package automenta.spacenet.space.jme.video;

import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IntegerVar;
import automenta.spacenet.var.string.StringVar;

public class JmeVideoState {
	
	IntegerVar alphaBits = new IntegerVar(16);
	IntegerVar depthBits = new IntegerVar(16);
	IntegerVar stencilBits = new IntegerVar(16);
	BooleanVar fullScreen = new BooleanVar(false);
	BooleanVar lightsEnabled = new BooleanVar(true);
	DoubleVar ambientLight = new DoubleVar(0.6);
	double textLineVisibleProp = 0.0025;
	double charNodeVisibleProp = 0.001;
	IntegerVar subsamples = new IntegerVar(1);
	DoubleVar updatePeriod = new DoubleVar(0.016);
	StringVar windowTitle = new StringVar("");
	
	public IntegerVar getAlphaBits() {	return alphaBits;		}
	public IntegerVar getDepthBits() { 	return depthBits;		}
	public IntegerVar getStencilBits() { 	return stencilBits;			}
	public BooleanVar getFullScreen() {		return fullScreen;	}
	public BooleanVar getLightsEnabled() { 		return lightsEnabled;			}
	public DoubleVar getAmbientLightLevel() {		return ambientLight;		}
	public double getTextLineVisibleProportion() {	return textLineVisibleProp;		}
	public double getCharNodeVisibleProportion() {	return charNodeVisibleProp;		}
	public IntegerVar getSubsamples() {		return subsamples;			}
	public DoubleVar getUpdatePeriod() {		return updatePeriod;		}
	public StringVar getWindowTitle() {		return windowTitle; }
}