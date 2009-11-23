package automenta.spacenet.plugin.file;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.collections15.IteratorUtils;
import org.apache.commons.collections15.iterators.IteratorChain;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.VFS;
import org.apache.log4j.Logger;

import automenta.spacenet.Disposable;
import automenta.spacenet.ID;
import automenta.spacenet.UURI;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.string.StringVar;


public class VirtualFile extends ListVar<ID> implements ID, Disposable {

	final FileSystemManager fsManager = VFS.getManager();

	public final static Logger logger = Logger.getLogger(VirtualFile.class);
	
	//private static final TagSet tags = new TagSet(Tag.File);
	
	private UURI pathURI;
	
	private org.apache.commons.vfs.FileObject fileObject;	//Apache VFS FileObject

	private StringVar name;

	private boolean hasData;

	public VirtualFile(URL url) throws Exception {
		this(url.toExternalForm(), 1);
	}

	public VirtualFile(UURI uri) throws Exception {
		this(uri.toString(), 1);
	}
	
	public VirtualFile(String urlOrPath, int cachedDepth) throws Exception {
		super();
		
		init(urlOrPath, cachedDepth);
	}


	public VirtualFile(org.apache.commons.vfs.FileObject fo, int cachedDepth) throws Exception {
		super();
		
		init(fo, cachedDepth);
	}

	@Override public UURI getUURI() { return pathURI; }
	
	private void init(org.apache.commons.vfs.FileObject fo, int maxDepth) throws Exception {
		
		//TODO check that this is never called twice

		hasData = ((fo.getType() == FileType.FILE_OR_FOLDER) || (fo.getType() == FileType.FILE));

		this.fileObject = fo;
		this.pathURI = new UURI(fileObject.getURL());

		name = new StringVar(fileObject.getName().getBaseName());

		updateDepth(maxDepth);
		
		
	}

	private void init(String pathURI, int maxDepth) throws Exception {
		init(resolvePath( pathURI ), maxDepth);
	}

	//@Override public TagSet getTags() {	return tags;	}
	
	//@Override public TextValue getName() {	return name;	}
	
	@Override
	public String toString() {
		return getUURI().toString();
	}
		
//	@Override public String toString() {
//		return name.asString(); //"File[" + getURI() + "]";
//	}

	public void updateDepth(int maxDepth) {
		clear();

		if (maxDepth > 0) {
			try {
				if (fileObject.getChildren()!=null) {
					org.apache.commons.vfs.FileObject[] ch = fileObject.getChildren();
					for (org.apache.commons.vfs.FileObject f : ch) {
						try {
							add( new VirtualFile(f, 0) );
						} catch (Exception e) {
							logger.error(e);
						}

						//						if (maxDepth > 1) {
//							add( new VirtualFile(f, maxDepth - 1) );
//						}
//						else {
//							add( new UURI(f.getURL()) );
//						}
					}
				}
			}
			catch (FileSystemException e) {
				//this means the file is not a directory, and has no children to add
			}
		}

	}
	
	/** gets a FileObject for given path, or null if non-existant */
	public static org.apache.commons.vfs.FileObject resolvePath(String p) throws Exception {
		System.out.println("opening: " + p);
		return VFS.getManager().resolveFile(p);
	}

	public File getFile() {
		return new File(pathURI.toString());
	}

	/** distinguishes files from directories */
	public boolean hasData() {
		return hasData;
	}

	/* TODO fix */
	public Iterator<VirtualFile> iterateDescendents(int i) {

		if (i!=2) {
			System.out.println(this + " is incomplete and does not accept recurse depth of anything except 2");
		}
		
		Iterator<VirtualFile> contentsIterator = this.contentIterator();
		Iterator<VirtualFile> descendentIterators = new IteratorChain<VirtualFile>(this.getDirectoryIterators());
		
		return new IteratorChain<VirtualFile>(contentsIterator, descendentIterators);
	}

	private Collection<Iterator<? extends VirtualFile>> getDirectoryIterators() {
		Collection<Iterator<? extends VirtualFile>> l = new LinkedList();
		for (ID h : this) {
			if (h instanceof VirtualFile) {
				VirtualFile o = (VirtualFile) h;
				if (!o.hasData()) {
					l.add(o.contentIterator());
					System.out.println("+++ " + o + IteratorUtils.toList(o.contentIterator()));
				}
				else {
					System.out.println("--- " + o);				
				}
			}
		}
		System.out.println("*** " + l.size());
		return l;
	}

	private Iterator<VirtualFile> contentIterator() {
		LinkedList<VirtualFile> l = new LinkedList();
		for (ID h : this) {
			if (h instanceof VirtualFile) {
				VirtualFile o = (VirtualFile) h;
				if (o.hasData())
					l.add(o);
			}
		}
		return l.iterator();
	}

	public org.apache.commons.vfs.FileObject getVFSObject() {
		return fileObject;
	}

	@Override
	public void dispose() {
		if (fileObject!=null) {
			try {
				fileObject.close();
			} catch (FileSystemException e) {
				logger.error(e);
			}
			fileObject = null;
		}
		
	}

	public boolean isDirectory() {
		try {
			return (fileObject.getChildren()!=null);
		} catch (FileSystemException e) {
			logger.error(e);
		}
		return false;
	}

	
}
