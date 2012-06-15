package mediaApp.main;

import java.util.List;

import mediaApp.HTTP.HTTPGetTask;
import mediaApp.HTTP.HTTPResponseListener;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Application;

public class MediaApplication extends Application implements HTTPResponseListener
{

	private static final String	TAG	= "MediaApp";
	private List<LucasResult>	results;
	private List<NameValuePair>	categories;
	private HttpClient httpClient;
	private HttpContext httpContext;

	@Override
	public void onCreate()
	{
		super.onCreate();
		httpClient = new DefaultHttpClient();
		CookieStore cookieStore = new BasicCookieStore();
		httpContext = new BasicHttpContext();
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		new HTTPGetTask(this).execute("http://dev.mediatheek.hu.nl/apps/android/Lucas_cat.xml");
	}
	
	public HttpClient getHttpClient(){
		return httpClient;		
	}
	
	public HttpContext getHttpContext(){
		return httpContext;		
	}

	@Override
	public void onResponseReceived(String response)
	{
		setCategories(new CategoryParser().parse(response));
	}

	/**
	 * @return the results
	 */
	public List<LucasResult> getResults()
	{
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(List<LucasResult> results)
	{
		this.results = results;
	}

	/**
	 * @return the categories
	 */
	public List<NameValuePair> getCategories()
	{
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(List<NameValuePair> categories)
	{
		this.categories = categories;
	}
}
