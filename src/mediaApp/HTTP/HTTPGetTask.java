package mediaApp.HTTP;

import java.io.IOException;
import java.net.SocketTimeoutException;
import mediaApp.main.MediaApplication;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import android.os.AsyncTask;

public class HTTPGetTask extends AsyncTask<String, String, String>
{
	private HTTPResponseListener	listener;
	private MediaApplication		application;

	public HTTPGetTask(HTTPResponseListener list, MediaApplication app)
	{
		listener = list;
		application = app;
	}

	@Override
	protected String doInBackground(String... args)
	{
		HttpResponse response = null;

		try
		{
			HttpClient httpclient = application.getHttpClient();
			HttpGet httpGet = new HttpGet(args[0]);// url
			response = httpclient.execute(httpGet, application.getHttpContext());
		}
		catch (SocketTimeoutException e)
		{
		}
		catch (Exception e)
		{
		}
		if (response != null)
		{
			try
			{
				return EntityUtils.toString(response.getEntity());
			}
			catch (ParseException e)
			{
				return null;
			}
			catch (IOException e)
			{
				return null;
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
		if (result != null)
			listener.onResponseReceived(result);
		else
			listener.onNullResponseReceived();
	}
}