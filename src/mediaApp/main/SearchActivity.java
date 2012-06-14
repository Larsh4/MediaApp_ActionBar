package mediaApp.main;

import java.util.List;

import mediaApp.HTTP.HTTPGetTaskAlt;
import mediaApp.HTTP.HTTPResponseListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchActivity extends BaseActivity implements 
		HTTPResponseListener, OnClickListener, OnItemClickListener, OnItemSelectedListener
{

	private static String	TAG	= "SearchAct";
	// UI
	private ListView		LV;
	private ProgressDialog	progressDialog;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		// search field part
		View searchField =  findViewById(R.id.searchFieldLayout);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		int show = sharedPreferences.getInt(SettingsActivity.SHOW_KEY, 0);
		if(show==1)	searchField.setVisibility(View.VISIBLE);
		else		searchField.setVisibility(View.GONE);		
		Spinner S = (Spinner) findViewById(R.id.searchFieldSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,
		R.array.searchFieldArray,
		android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		S.setAdapter(adapter);
		S.setOnItemSelectedListener(this);

		// databases part
		LV = (ListView) findViewById(R.id.databaseList);
		LV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		String[] databases = getResources().getStringArray(R.array.searchDatabaseArray);
		LV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, databases));
		LV.setOnItemClickListener(this);

		// button part
		Button button = (Button) findViewById(R.id.searchBut);
		button.setOnClickListener(this);
	}

	@Override
	protected void onDestroy()
	{
		if (progressDialog != null)
			progressDialog.dismiss();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.search, menu);
		return true;// super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.searchBut:
				progressDialog = new ProgressDialog(this);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setMessage(getString(R.string.searchLoading));
				progressDialog.setCancelable(false);
				progressDialog.show();
				new HTTPGetTaskAlt(this).execute(createURL());
				break;
		}
	}

	@Override
	public void onResponseReceived(String response)
	{
		Log.i(TAG, "response: "+response);
		progressDialog.dismiss();
		LucasParser lp = new LucasParser();
		List<LucasResult> list = lp.parse(response);

		((MediaApplication) getApplication()).setResults(list);

		Intent resultIntent = new Intent(this, SearchResultActivity.class);
		startActivity(resultIntent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		if (position == LV.getCount() - 1)
		{ // select all item
			boolean isChecked = LV.isItemChecked(position);
			for (int i = 0; i < LV.getCount() - 1; i++)
				LV.setItemChecked(i, isChecked);
		}
		else
			LV.setItemChecked(LV.getCount() - 1, false);
	}

	private String createURL()
	{								 //added: [serialssolutions.com].www.dbproxy.hu.nl[/sru]
		String URL = "http://yd3wb8fs2g.cs.xml.serialssolutions.com/sru?version=1.1&recordSchema=cs1.2&operation=searchRetrieve&x-cs-categories=";

		for (int i = 0; i < LV.getCount() - 2; i++) // two less because of 'Other Databases' and 'All Databases'
		{
			if (LV.isItemChecked(i))
			{
				URL += getResources().getIntArray(R.array.dbCodes)[i];
				URL += ",";
			}
		}
		URL = URL.substring(0, URL.length() - 1);

		EditText searchField = (EditText) findViewById(R.id.ETSearch);
		URL += "&query=" + searchField.getText().toString();

		return URL;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
}
