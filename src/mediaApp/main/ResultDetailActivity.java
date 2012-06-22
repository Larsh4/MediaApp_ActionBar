package mediaApp.main;

import mediaApp.XML.Database;
import mediaApp.XML.LucasResult;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ResultDetailActivity extends BaseActivity
{
	private static LucasResult	result;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resultdetail);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(getString(R.string.dialogLoadingResultDetail));
		progressDialog.setCancelable(false);
		progressDialog.show();

		result = mediaApp.getResults().get(getIntent().getIntExtra("id", 1));
		String dbCode = result.getSourceId();
		boolean needsProxy = false;
		for (Database d : mediaApp.getDatabases())
		{	
			if (d.getId().equalsIgnoreCase(dbCode))
			{
				needsProxy = d.isProxy();
				break;
			}
		}

		String url = result.getUrl().toString();
		if (needsProxy)
			url = "http://www.dbproxy.hu.nl/login?url=" + url;
		WebView webView = (WebView) findViewById(R.id.WVResultDetail);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDatabaseEnabled(true);
		webView.getSettings().setSavePassword(true);
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url)
			{
				if (progressDialog.isShowing())
					progressDialog.dismiss();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.resultdetail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				Intent serverIntent = new Intent(this, SearchResultActivity.class);
				serverIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(serverIntent);
				return true;
			case R.id.menu_mail:
				prepareMail();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void prepareMail()
	{
		Intent mailIntent = new Intent(Intent.ACTION_SEND);
		mailIntent.setType("message/rfc822");

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String[] email = { prefs.getString("user", "") };

		mailIntent.putExtra(Intent.EXTRA_EMAIL, email);
		mailIntent.putExtra(Intent.EXTRA_SUBJECT, result.getTitle());

		String emailBody = "";
		emailBody += "Title of the document: " + result.getTitle();
		if (result.getAuthor() != null)
			emailBody += "\n\nAuthor: " + result.getAuthor();
		if (result.getDate() != null)
			emailBody += "\n\nDate published: " + result.getDate();
		if (result.getIssn() != null)
			emailBody += "\n\nISSN: " + result.getIssn();
		if (result.getIsbn() != null)
			emailBody += "\n\nISBN: " + result.getIsbn();
		if (result.getSource() != null)
			emailBody += "\n\nPublisher: " + result.getSource();
		emailBody += "\n\nURL: " + result.getUrl().toString();
		mailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);

		startActivity(Intent.createChooser(mailIntent, getString(R.string.contactEmailIntent)));
	}
}
