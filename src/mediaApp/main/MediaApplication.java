package mediaApp.main;

import java.util.List;
import android.app.Application;

public class MediaApplication extends Application
{

	private List<LucasResult>	results;

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

}
