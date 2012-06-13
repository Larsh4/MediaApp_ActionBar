package mediaApp.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ResultDetailActivity extends BaseActivity
{

	static final String			TAG	= "ResultDetailAct";
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
		progressDialog.setMessage(getString(R.string.resultDetailLoading));
		progressDialog.setCancelable(false);
		progressDialog.show();

		result = ((MediaApplication) getApplication()).getResults().get(getIntent().getIntExtra("id", 1));

		WebView webView = (WebView) findViewById(R.id.WVResultDetail);
		webView.loadUrl(result.getUrl().toString());
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
				Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
				Intent serverIntent = new Intent(this, SearchActivity.class);
				serverIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(serverIntent);
				return true;
			case R.id.menu_mail:
				Intent mailIntent = new Intent(Intent.ACTION_SEND);
				mailIntent.setType("message/rfc822");
				mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "recipient@example.com" });
				mailIntent.putExtra(Intent.EXTRA_SUBJECT, result.getTitle());
				mailIntent.putExtra(Intent.EXTRA_TEXT, result.getUrl().toString());
				startActivity(Intent.createChooser(mailIntent, "Send mail..."));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
