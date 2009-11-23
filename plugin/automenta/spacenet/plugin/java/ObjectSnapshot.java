package automenta.spacenet.plugin.java;

import java.lang.reflect.Method;

import automenta.spacenet.var.map.MapVar;

public class ObjectSnapshot extends MapVar<String,Object> {

	public ObjectSnapshot(Object o) {
		this(o, "");
	}
	
	public ObjectSnapshot(Object o, String prefix) {
		super();
		put(o, prefix, true);		
	}

	public ObjectSnapshot() {
		super();
	}

	public void put(Object o, String prefix, boolean replace) {
		for (Method m : o.getClass().getMethods()) {
			if (m.getName().startsWith("get")) {
				if (m.getTypeParameters().length == 0) {
					if (!m.getReturnType().equals( Void.class )) {
						String property = prefix + m.getName().substring(3);
						if (!((!replace) && (get(property)!=null))) {
							Object value;
							try {
								value = m.invoke(o);
							} catch (Exception e) {
								value = e.toString();
							}
							put(property, value);
						}
					}
				}
			}
		}
	}


	public void put(Object o, boolean replace) {
		put(o, "", replace);
	}
	
}
