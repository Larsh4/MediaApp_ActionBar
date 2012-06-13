package mediaApp.HTTP;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

import android.os.AsyncTask;
import android.util.Log;

public class ProxyLoginTask extends AsyncTask<String, Void, String>
{
	static final String TAG = "ProxyLoginTask";
	private final HTTPResponseListener	listener;

	public ProxyLoginTask(HTTPResponseListener list)
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
		String response = "";

		try
		{
			URL url = new URL(args[0]);

			String postParams = "user=" + args[1] + "&pass=" + args[2];

			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			httpConn.setFixedLengthStreamingMode(postParams.getBytes().length);

			// This is where we actually add the POST parameters to the connection
			Log.v(TAG,"getOutputStream");
			PrintWriter out = new PrintWriter(httpConn.getOutputStream());
			out.print(postParams);
			out.close();
			
			Log.v(TAG,"getInputStream");	
			Scanner inStream = new Scanner(httpConn.getInputStream());
			Log.v(TAG,"while hasNextLine");
			while (inStream.hasNextLine())
			{
				if (isCancelled())
				{
					inStream.close();
					httpConn.disconnect();
					return null;
				}
				Log.v(TAG,"getNextLine");
				response += (inStream.nextLine());
			}
			Log.i(TAG,"Closing/Disconnecting");
			inStream.close();
			httpConn.disconnect();
		}
		catch (SocketTimeoutException e)
		{
			 Log.e(TAG, "doInBackground socket timed out", e);
		}
		catch (Exception e)
		{
			 Log.e(TAG, "Error in doInbackground: ", e);
		}
		Log.v(TAG,"done");
		return response;
	}

	@Override
	protected void onPostExecute(String result)
	{
		Log.v(TAG,"onPostExecute");
		if (listener != null && result != null)
		{
			listener.onResponseReceived(result);
		}
	}

}
