package automenta.spacenet;


/** an interface to container functions.  (metalinguistic abstraction of picocontainer functionality.) */
public interface Builder {

	/** specifies that ONE instance of a component (or component class) may be built and returned by getThe (Picocontainer SINGLE) */
	public <X> X setThe(Object componentKey, X component);

	/** specifies that ONE instance of a component (or component class) may be built and returned by getThe (Picocontainer SINGLE) */
	public <X> X setThe(X component);
		
	/** specifies that MULTIPLE instances of a component class can be built and each returned by getThe */
	public <X> X setSome(Object componentKey, X component);

	/** specifies that MULTIPLE instances of a component class can be built and each returned by getThe */
	public <X> X setSome(X component);

	/** gets an instance of a particular component class.  (in picocontainer terms, part=component which is shorter by two syllables and 5 letters) */
	public <T> T getThe(Class<T> cl);


}
