package mediaApp.main;

import java.util.List;
import mediaApp.HTTP.HTTPGetTask;
import mediaApp.HTTP.HTTPResponseListener;
import mediaApp.XML.LucasParser;
import mediaApp.XML.LucasResult;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultActivity extends BaseActivity implements OnItemClickListener, HTTPResponseListener
{

	private static final String	TAG	= "SearchResultAct";
	private List<LucasResult>	results;
	private View				footer;
	private static String		searchUrl;
	private int					page, recordsPerPage, curPos;
	private ListView			resultList;
	private ProgressDialog		searchingDialog;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchresult);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		recordsPerPage = Integer
				.parseInt(getResources().getStringArray(R.array.settingsAmountOfResultsArray)[PreferenceManager
						.getDefaultSharedPreferences(this).getInt(SettingsActivity.AMOUNT_KEY, 1)]);

		page = 1;

		resultList = (ListView) findViewById(R.id.LVResults);
		results = mediaApp.getResults();
		if (results != null && results.size() > 0)
		{
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (mediaApp.getCurNrOfRecords() >= page * recordsPerPage)
			{
				footer = inflater.inflate(R.layout.footer, null, false);
				resultList.addFooterView(footer);
			}

			SearchResultAdapter resultAdapter = new SearchResultAdapter(inflater, results);
			resultList.setAdapter(resultAdapter);
			resultList.setOnItemClickListener(this);
		}
		else
		{
			resultList.setVisibility(View.GONE);
			TextView noResultsText = (TextView) findViewById(R.id.TVNoResults);
			noResultsText.setVisibility(View.VISIBLE);
		}

		searchUrl = getIntent().getStringExtra("url");
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		if (view == footer)
		{
			int startRecord = page * recordsPerPage + 1;

			new HTTPGetTask(this, mediaApp).execute(searchUrl + "&startRecord=" + startRecord);

			searchingDialog = new ProgressDialog(this);
			searchingDialog.setMessage(getString(R.string.dialogSearching));
			searchingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			searchingDialog.setCancelable(false);
			searchingDialog.show();

			curPos = resultList.getFirstVisiblePosition();
		}
		else
		{
			Intent resultDetailIntent = new Intent(this, ResultDetailActivity.class);
			resultDetailIntent.putExtra("id", position);
			startActivity(resultDetailIntent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.searchresult, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}

	public void onResponseReceived(String response)
	{
		searchingDialog.dismiss();

		page++;

		results.addAll(new LucasParser(mediaApp).parse(response));

		resultList.setAdapter(new SearchResultAdapter(
				(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), results));

		if (results.size() < page * recordsPerPage)
			resultList.removeFooterView(footer);
		else
			footer.setEnabled(true);

		resultList.setSelection(curPos);
	}

	public void onNullResponseReceived()
	{
	}
}
