package automenta.spacenet.var.map;

import automenta.spacenet.Maths;
import java.util.Iterator;



/** maps a set of objects to scalar (double) values.  with methods for sorting and finding smallest, biggest */
public class ScalarMap<O> extends MapVar<O,Double> {

    double defaultValue = 0;
	double min = 0, mean = 0, max = 0, total = 0;

    public O biggest = null, smallest = null;
    
	public Iterator<O> iterateDescending() {
		return null;
	}
	
	public O getBiggest() { return biggest; }
	public O getSmallest() { return smallest; }

    public double getMin() { return min; }
    public double getMax() { return max; }
    public double getMean() { return mean; }
    public double getTotal() { return total; }

    protected void update() {
		//calculate min, max, mean, total

		min = Float.MAX_VALUE;
		max = Float.MIN_VALUE;

		total = 0;

		for (O o : keySet()) {
            double d = get(o);
            
			if (d < min) {
				min = d;
                smallest = o;
            }
			if (d > max) {
				max = d;
                biggest = o;
            }

			total += d;
		}

		if (size() > 0)
			mean = total / size();

    }

    @Override public Double put(O key, Double value) {
        Double result = super.put(key, value);
        update();
        return result;
    }

    @Override
    public Double remove(Object key) {
        Double result = super.remove(key);
        if (result!=null) {
            update();
        }
        return result;
    }

    @Override
    public Double get(Object key) {
        Double d = super.get(key);
        if (d == null) {
            return getDefaultValue();
        }
        return d;
    }






//
//	public FloatMap(AtomNet net) {
//		super(net);
//	}
//
//	@Override protected Float getDefaultValue() {
//		return defaultValue;
//	}
//
//	@Override public Float set(String id, Float newValue) {
//		Float r = super.set(id, newValue);
//		updateNumbers();
//		return r;
//	}
//
//	public void add(String id, float delta) {
//		set(id, get(id) + delta);
//	}
//
//	public void diffuse(boolean in, boolean out, double rate) {
//		for (String node: getAtoms()) {
//			double sti = get(node);
//			double spread = sti * rate;
//
//
//			List<String> targets = new LinkedList();
//			if (in) {
//				Collection<String> incoming = getNet().getIncoming(node);
//				targets.addAll(incoming);
//			}
//			if (out) {
//				Collection<String> outgoing = getNet().getOutgoing(node);
//				targets.addAll(outgoing);
//			}
//
//			if (targets.size() > 0) {
//				double spreadPerTarget = spread / targets.size();
//
//				set(node, (float)(get(node) - spread));
//
//				for (String t : targets) {
//					set(t, (float)(get(t) + spreadPerTarget));
//				}
//			}
//
//		}
//
//		updateNumbers();
//	}
//
	public void randomize(float min, float max) {
		for (O o : keySet()) {
			put(o, Maths.random(min, max));
		}
	}

//
	public double getNormalized(O o) {
		return (get(o) - getMin()) / (getMax() - getMin());
	}

    public double getDefaultValue() {
        return defaultValue;
    }


	public void setDefaultValue(float defaultValue) {
		this.defaultValue = defaultValue;
	}

//
//	public void setRandom(String n, float min, float max) {
//		set(n, (float)Maths.random(min, max));
//	}
//

}
