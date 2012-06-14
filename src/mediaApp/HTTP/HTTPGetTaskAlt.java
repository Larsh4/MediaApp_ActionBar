package mediaApp.HTTP;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class HTTPGetTaskAlt extends AsyncTask<String, String, String>
{

	private HTTPResponseListener	listener;
	private static String			TAG	= "HTTPGetAlt";

	public HTTPGetTaskAlt(HTTPResponseListener list)
	{
		listener = list;
	}

	@Override
	protected String doInBackground(String... args)
	{
		HttpResponse response = null;

		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(args[0]);//url
			response = httpclient.execute(httpGet);		
			Log.i(TAG, "Response: "+response.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return response.toString();
	}

	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
		// Do anything with response..
		if (result != null)
		{
			// Log.d(TAG, result);
			listener.onResponseReceived(result);
		}
	}
}