package automenta.spacenet.plugin.web.yahoo;

import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import automenta.spacenet.UURI;
import automenta.spacenet.var.index.Found;
import automenta.spacenet.var.list.ListVar;

/**
 * 
 * @see http://developer.yahoo.com/search/image/V1/imageSearch.html
 *
 */
public class YahooImageSearch {

	private static final Logger logger = Logger.getLogger(YahooImageSearch.class);
	
	private ListVar<Found> results = new ListVar();

	public YahooImageSearch() {
		super();
	}

	public ListVar<Found> update(String query, int numResults) {
		results.clear();

		String encodedQuery = URLEncoder.encode(query);
		
		/*
		 * Sample Request Url: 
		 * 				  http://search.yahooapis.com/ImageSearchService/V1/imageSearch?appid=YahooDemo&query=Corvette&results=2&output=json
		 */
		String request = "http://search.yahooapis.com/ImageSearchService/V1/imageSearch?appid=YahooDemo&query=" + encodedQuery + "&results=" + numResults + "&output=json";

		HttpClient client = new DefaultHttpClient();
		HttpGet method = new HttpGet(request);

		// Send GET request
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseString;
		try {
			responseString = client.execute(method, responseHandler);
		} catch (Exception e) {
			logger.error(e);
			return results;
		}
		
		String jsonString = responseString;
		
		// Construct a JSONObject from a source JSON text string.
		// A JSONObject is an unordered collection of name/value pairs. Its external 
		// form is a string wrapped in curly braces with colons between the names 
		// and values, and commas between the values and names.
		JSONObject jo;
		try {
			jo = new JSONObject(jsonString);

			// A JSONArray is an ordered sequence of values. Its external form is a 
			// string wrapped in square brackets with commas between the values.
			JSONArray ja;

			// Get the JSONObject value associated with the search result key.
			jo = jo.getJSONObject("ResultSet");

			//System.out.println(jo.toString());

			// Get the JSONArray value associated with the Result key
			ja = jo.getJSONArray("Result");

			// Get the number of search results in this set
			int resultCount = ja.length();

			double currentStrength = 1.0;

			final String tags = "web";
			
			// Loop over each result and print the title, summary, and URL
			for (int i = 0; i < resultCount; i++)	{
				final JSONObject resultObject = ja.getJSONObject(i);
				
				final String name = resultObject.get("Title").toString(); 
				final String summary = resultObject.get("Summary").toString();
				final String url = resultObject.get("Url").toString();

				final UURI uuri = new UURI(url);
				
				/* Other fields:
				 * 	FileSize
				 *  FileFormat
				 *  Height
				 *  Width
				 *  Thumbnail
				 */
				final double strength = currentStrength;
				currentStrength -= (1.0 / (resultCount));
				
				results.add(new Found() {

					@Override public String getDescription() {
						return summary;
					}

					@Override
					public UURI getUURI() {
						return uuri;
					}
					
					@Override
					public String getName() {
						return name;
					}

					@Override
					public Object getObject() {
						return this;
					}

					@Override
					public double getStrength() {
						return strength;
					}

					@Override
					public String getTags() {
						return tags;
					}
			
					@Override
					public String toString() {
						return getName() + "(" + getUURI() + ") " + " - " + getDescription() + "[" + getStrength() + "]";						
					}
					
				});
			}

		} catch (JSONException e) {
			logger.error(e);
			return results;
		}

		return results;


	}

	public ListVar<Found> get() {
		return results;
	}

	/** (for testing) */
	public static void main(String[] args) {
		System.out.println( new YahooImageSearch().update("web", 15) );
	}

}
