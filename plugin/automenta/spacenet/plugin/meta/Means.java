package automenta.spacenet.plugin.meta;

/** refers to one or more universal "semantic" meaning URI's. analogous to open-rdf's @rdf annotation */
public @interface Means {
	public String[] value();
}
