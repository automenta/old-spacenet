package automenta.spacenet.plugin.meta;

/** tag = unique name.  usually camel-cased to avoid whitespace. can refer to any java code element. */
public @interface Tag {
	String[] value();
}
