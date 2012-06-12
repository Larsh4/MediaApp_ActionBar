package mediaApp.HTTP;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import android.os.AsyncTask;

public class HTTPGetTask extends AsyncTask<String, String, String>
{

	private HTTPResponseListener	listener;
	private static String			TAG	= "HTTPGet";

	public HTTPGetTask(HTTPResponseListener list)
	{
		listener = list;
	}

	@Override
	protected String doInBackground(String... uri)
	{
		String response = "";

		try
		{
			URL url = new URL(uri[0]);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true);

			Scanner inStream = new Scanner(httpConn.getInputStream());

			int i = 0;
			while (inStream.hasNextLine())
			{
				response += inStream.nextLine();
			}

			inStream.close();
			httpConn.disconnect();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return response;
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