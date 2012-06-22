package mediaApp.HTTP;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import mediaApp.main.MediaApplication;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.os.AsyncTask;

public class ProxyLoginTask extends AsyncTask<String, Void, String>
{
	private final HTTPResponseListener	listener;
	private MediaApplication			application;

	public ProxyLoginTask(HTTPResponseListener list, MediaApplication app)
	{
		listener = list;
		application = app;
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
			HttpClient httpclient = application.getHttpClient();
			HttpPost httppost = new HttpPost(args[0]);//url		
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("user", args[1]));
			nameValuePairs.add(new BasicNameValuePair("pass", args[2]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(httppost, application.getHttpContext());
		}
		catch (SocketTimeoutException e)
		{
		}
		catch (Exception e)
		{
		}
		if (response != null){
			try {
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
		if (listener != null)
			listener.onResponseReceived(result);
	}
}
