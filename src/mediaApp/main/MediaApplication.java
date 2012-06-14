package mediaApp.main;

import java.util.List;
import mediaApp.HTTP.HTTPGetTask;
import mediaApp.HTTP.HTTPResponseListener;
import org.apache.http.NameValuePair;
import android.app.Application;

public class MediaApplication extends Application implements HTTPResponseListener
{

	private static final String	TAG	= "MediaApp";
	private List<LucasResult>	results;
	private List<NameValuePair>	categories;

	@Override
	public void onCreate()
	{
		super.onCreate();

		new HTTPGetTask(this).execute("http://dev.mediatheek.hu.nl/apps/android/Lucas_cat.xml");
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
