package automenta.spacenet.var.time;

import java.util.TreeMap;

import org.apache.commons.collections15.map.FixedSizeSortedMap;


public class TimeMap<X> extends FixedSizeSortedMap<TimePoint, X> {

	private int maxSize;

	public TimeMap(int size) {
		super(new TreeMap<TimePoint,X>());
		this.maxSize = size;
	}
	
	@Override
	public int maxSize() {
		return maxSize;
	}
	
}
