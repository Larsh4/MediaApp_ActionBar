package mediaApp.HTTP;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

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
		catch (SocketTimeoutException e)
		{
			Log.e(TAG, "doInBackground socket timed out", e);
		}
		catch (Exception e)
		{
			Log.e(TAG, "Error in doInbackground: ", e);
		}
		Log.v(TAG, "done");
		if (response != null){
			try {
				Log.v(TAG,"response received");
				return EntityUtils.toString(response.getEntity());
			} catch (ParseException e) {
				return null;
			} catch (IOException e) {
				return null;
			}
		}
		return null;
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