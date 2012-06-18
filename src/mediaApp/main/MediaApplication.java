package mediaApp.main;

import java.util.List;
import mediaApp.HTTP.HTTPGetTask;
import mediaApp.HTTP.HTTPResponseListener;
import mediaApp.XML.CategoryParser;
import mediaApp.XML.DatabaseParser;
import mediaApp.XML.LucasResult;
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
	private List<NameValuePair>	categories, databases;
	private HttpClient			httpClient;
	private HttpContext			httpContext;
	private CategoryListener	catListener;

	@Override
	public void onCreate()
	{
		super.onCreate();
		refreshHttp();
		new HTTPGetTask(this, this).execute("http://dev.mediatheek.hu.nl/apps/android/Lucas_cat.xml");
	}

	public HttpClient getHttpClient()
	{
		return httpClient;
	}

	public HttpContext getHttpContext()
	{
		return httpContext;
	}

	public void refreshHttp()
	{
		httpClient = new DefaultHttpClient();
		CookieStore cookieStore = new BasicCookieStore();
		httpContext = new BasicHttpContext();
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	}

	@Override
	public void onResponseReceived(String response)
	{
		if (categories == null)
		{
			setCategories(new CategoryParser().parse(response));
			new HTTPGetTask(this, this).execute("http://dev.mediatheek.hu.nl/apps/android/Lucas_dbs.xml");
		}
		else
			setDatabases(new DatabaseParser().parse(response));
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
		if (catListener != null)
		{
			catListener.categoriesLoaded();
			catListener = null;
		}
	}

	public void setCategoryListener(CategoryListener cList)
	{
		if (categories == null)
			catListener = cList;
		else
			cList.categoriesLoaded();
	}

	/**
	 * @return the databases
	 */
	public List<NameValuePair> getDatabases()
	{
		return databases;
	}

	/**
	 * @param databases
	 *            the databases to set
	 */
	public void setDatabases(List<NameValuePair> databases)
	{
		this.databases = databases;
	}
}
