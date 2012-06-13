package mediaApp.HTTP;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class ProxyLoginTaskAlt extends AsyncTask<String, Void, String>
{

	static final String					TAG	= "ProxyLoginTaskAlt";
	private final HTTPResponseListener	listener;

	public ProxyLoginTaskAlt(HTTPResponseListener list)
	{
		listener = list;
	}

	/**
	 * Logs into the proxy server
	 * 
	 * @param args
	 *            Needs an array of strings that is 3 strings in size. The first string should be the URL of
	 *            the proxy where we can log in. The second string should be the username (email) and the
	 *            third string should be the password.
	 * @return Returns the response from the proxy to the {@link onResponseReceived} function of the listener
	 *         specified in the constructor.
	 */
	@Override
	protected String doInBackground(String... args)
	{
		HttpResponse response = null;

		try
		{
			URL url = new URL(args[0]);

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(args[0]);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("user", args[1]));
			nameValuePairs.add(new BasicNameValuePair("pass", args[2]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			response = httpclient.execute(httppost);

			//Log.v(TAG, EntityUtils.toString(response.getEntity()));
		}
		catch (SocketTimeoutException e)
		{
			Log.e(TAG, "doInBackground socket timed out", e);
		}
		catch (Exception e)
		{
			Log.e(TAG, "Error in doInbackground: ", e);
		}
		Log.i("proxy", "done");
		if (response != null)
			try {
				return EntityUtils.toString(response.getEntity());
			} catch (ParseException e) {
				return null;
			} catch (IOException e) {
				return null;
			}
		return null;
	}

	@Override
	protected void onPostExecute(String result)
	{
		if (listener != null)
		{
			listener.onResponseReceived(result);
		}
	}

}
