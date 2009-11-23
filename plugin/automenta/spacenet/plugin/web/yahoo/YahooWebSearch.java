package automenta.spacenet.plugin.web.yahoo;

import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;


import automenta.spacenet.UURI;
import automenta.spacenet.var.index.Found;
import automenta.spacenet.var.list.ListVar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Interface to Yahoo! search by parsing its JSON output format
 * 
 * @see http://developer.yahoo.com/java/samples/ParseYahooSearchResultsJSON.java
 * @see http://developer.yahoo.com/java
 */
public class YahooWebSearch {
	private static final Logger logger = Logger.getLogger(YahooWebSearch.class);
	
	private ListVar<Found> results = new ListVar();

	public YahooWebSearch() {
		super();
	}

	public ListVar<Found> update(String query, int numResults) {
		results.clear();

		String encodedQuery = URLEncoder.encode(query);
		
		String request = "http://api.search.yahoo.com/WebSearchService/V1/webSearch?appid=YahooDemo&query=" + encodedQuery + "&results=" + numResults + "&output=json";

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
				final double strength = currentStrength;
				
				final UURI uuri = new UURI(url);
				
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
		System.out.println( new YahooWebSearch().update("web", 15) );
	}
}
