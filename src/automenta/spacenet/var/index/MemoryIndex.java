package automenta.spacenet.var.index;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocCollector;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;

import automenta.spacenet.Disposable;
import automenta.spacenet.UURI;
import automenta.spacenet.var.list.ListVar;

public class MemoryIndex<O> implements Disposable {
	private static final Logger logger = Logger.getLogger(MemoryIndex.class);

	private RAMDirectory dir;
	private StandardAnalyzer analyzer;

	private Map<String, O> docToObj = new HashMap();

	private int nextID = 0;

	public MemoryIndex() {
		super();

		analyzer = new StandardAnalyzer();
		dir = new RAMDirectory();

	}

	public boolean index(O o, String name) {
		return index(o, name, null, null, 1.0f);		
	}

	public boolean index(O o, String name, String tags, String description) {
		return index(o, name, tags, description, 1.0f);		
	}

	/** name=title, tags=keywords (separated by commas) */
	public boolean index(O o, String name, String tags, String description, float strength) {
		try {
			IndexWriter w = new IndexWriter(dir, analyzer, IndexWriter.MaxFieldLength.UNLIMITED);
			w.addDocument(newDocument(o, name, tags, description, strength));
			w.commit();
			w.close();
		} catch (CorruptIndexException e) {
			logger.error(e);
			return false;
		} catch (LockObtainFailedException e) {
			logger.error(e);
			return false;
		} catch (IOException e) {
			logger.error(e);
			return false;
		}

		return true;
	}

	private Document newDocument(O o, String name, String tags, String description, float boost) {
		Document d = new Document();

		String id = newID();
		d.add(new Field("id", id, Field.Store.YES, Field.Index.NOT_ANALYZED));
		d.add(new Field("name", name, Field.Store.YES, Field.Index.ANALYZED));
		if (tags!=null) {
			d.add(new Field("tags", tags, Field.Store.YES, Field.Index.ANALYZED));
		}
		if (description!=null) {
			d.add(new Field("desc", description, Field.Store.YES, Field.Index.ANALYZED));			
		}

		d.setBoost(boost);

		docToObj.put(id, o);

		return d;
	}

	private String newID() {
		int i = nextID++;
		return Integer.toString(i, 26);
	}

	public ListVar<Found> getResults(ListVar<Found> result, Query q, int numResults) throws CorruptIndexException, IOException {
		result.clear();

		IndexSearcher searcher = new IndexSearcher(dir);
		TopDocCollector collector =  new TopDocCollector(numResults);

		searcher.search(q, collector);

		ScoreDoc[] hits = collector.topDocs().scoreDocs;


		for (ScoreDoc s : hits) {				
			final Document doc = searcher.doc(s.doc);
			final float score = s.score;

			final UURI uuri = new UURI(doc.getField("id").stringValue());
			
			Found f = new Found() {

				public String getDescription() {
					try {
						return doc.getField("description").stringValue();
					}
					catch (NullPointerException n) {
						return null;
					}
				}

				public double getStrength() {
					return score;
				}		

				@Override
				public String toString() {
					return "[" + getObject() + ", " + getName() + ", " + getDescription() + ", " + getTags() + " : " + getStrength() + "]";
				}

				@Override public UURI getUURI() {
					return uuri;
				}
				
				public O getObject() {
					return MemoryIndex.this.getObject(getUURI().toString());
				}
				public String getName() {
					return doc.getField("name").stringValue();
				}
				public String getTags() {
					try {
						return doc.getField("tags").stringValue();
					}
					catch (NullPointerException n) {
						return null;
					}
				}




			};
			result.add(f);
		}			

		searcher.close();

		return result ;
	}

	public void getResults(ListVar<Found> result, String query, int numResults) {
		final String[] queryFields = { "name", "desc", "tags" };

		try {
			Query q = new MultiFieldQueryParser(queryFields, analyzer).parse(query);
			getResults(result, q, numResults);
		} catch (ParseException e) {
			logger.error(e);
		} catch (CorruptIndexException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

	}

	public ListVar<Found> getResults(String query, int numResults) {
		ListVar<Found> result = new ListVar<Found>();
		getResults(result, query, numResults);
		return result;
	}

	O getObject(String id) {
		return docToObj.get(id);
	}

	@Override public void dispose() {
		if (docToObj!=null) {
			docToObj.clear();
			docToObj = null;
		}
		if (dir!=null) {
			dir.close();
			dir = null;
		}
		if (analyzer!=null) {
			analyzer.close();
			analyzer = null;
		}

	}

	public int size() {
		return docToObj.size();
	}

	public void index(O o, String name, String tags) {
		index(o, name, tags, null);		
	}

}
