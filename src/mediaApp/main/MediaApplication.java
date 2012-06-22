package mediaApp.main;

import java.util.List;
import mediaApp.HTTP.HTTPGetTask;
import mediaApp.HTTP.HTTPResponseListener;
import mediaApp.XML.Category;
import mediaApp.XML.CategoryParser;
import mediaApp.XML.Database;
import mediaApp.XML.DatabaseParser;
import mediaApp.XML.LucasResult;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import android.app.Application;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class MediaApplication extends Application implements HTTPResponseListener
{

	private static final String		TAG	= "MediaApp";
	private List<LucasResult>		results;
	private List<Database>			databases;
	private List<Category>			categories;
	private HttpClient				httpClient;
	private HttpContext				httpContext;
	private CategoryListener		catListener;
	private GoogleAnalyticsTracker	tracker;
	private int						curNrOfRecords;

	
	public void onCreate()
	{
		super.onCreate();
		refreshHttp();
		new HTTPGetTask(this, this).execute("http://dev.mediatheek.hu.nl/apps/android/Lucas_cat.xml");

		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(getString(R.string.googleAnalyticsCode), this);
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
	public List<Category> getCategories()
	{
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(List<Category> categories)
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
	public List<Database> getDatabases()
	{
		return databases;
	}

	/**
	 * @param databases
	 *            the databases to set
	 */
	public void setDatabases(List<Database> databases)
	{
		this.databases = databases;
	}

	
	public void onNullResponseReceived()
	{
		if (categories == null)
			new HTTPGetTask(this, this).execute("http://dev.mediatheek.hu.nl/apps/android/Lucas_cat.xml");
		else
			new HTTPGetTask(this, this).execute("http://dev.mediatheek.hu.nl/apps/android/Lucas_dbs.xml");

	}

	/**
	 * @return the tracker
	 */
	public GoogleAnalyticsTracker getTracker()
	{
		return tracker;
	}

	/**
	 * @return the curNrOfRecords
	 */
	public int getCurNrOfRecords()
	{
		return curNrOfRecords;
	}

	/**
	 * @param curNrOfRecords
	 *            the curNrOfRecords to set
	 */
	public void setCurNrOfRecords(int curNrOfRecords)
	{
		this.curNrOfRecords = curNrOfRecords;
	}
}
