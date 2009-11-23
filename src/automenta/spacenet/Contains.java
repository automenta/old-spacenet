package automenta.spacenet;

import java.util.Collection;

public interface Contains {

	<X> void addAll(Collection<X> value);

	<X> X add(Object key, X value);

	void removeAll(Collection keys);

	Object remove(Object key);

}
