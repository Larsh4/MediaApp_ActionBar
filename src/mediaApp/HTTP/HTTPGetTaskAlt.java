package mediaApp.HTTP;

import java.io.IOException;
import java.net.SocketTimeoutException;

import mediaApp.main.MediaApplication;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class HTTPGetTaskAlt extends AsyncTask<String, String, String>
{

	private HTTPResponseListener	listener;
	private MediaApplication		application;
	private static String			TAG	= "HTTPGetAlt";

	public HTTPGetTaskAlt(HTTPResponseListener list, MediaApplication app)
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
			HttpGet httpGet = new HttpGet(args[0]);//url
			CookieStore cookieStore = new BasicCookieStore();
			HttpContext httpContext = new BasicHttpContext();
			httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			response = httpclient.execute(httpGet, httpContext);		
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