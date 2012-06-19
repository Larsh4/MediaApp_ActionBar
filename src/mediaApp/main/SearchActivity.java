package mediaApp.main;

import java.util.ArrayList;
import java.util.List;
import mediaApp.HTTP.HTTPGetTask;
import mediaApp.HTTP.HTTPResponseListener;
import mediaApp.XML.Category;
import mediaApp.XML.Database;
import mediaApp.XML.LucasParser;
import mediaApp.XML.LucasResult;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class SearchActivity extends BaseActivity implements
		HTTPResponseListener,
		OnClickListener,
		OnItemClickListener,
		OnItemSelectedListener,
		android.content.DialogInterface.OnClickListener,
		CategoryListener,
		OnCheckedChangeListener
{

	private static String			TAG						= "SearchAct";
	// UI
	private ListView				LV;
	private EditText				ETSearchField;
	private RadioGroup				RGSearchBy;
	// Dialogs
	static final int				SEARCHING_DIALOG		= 1;
	static final int				NO_SEARCH_TERM_DIALOG	= 2;
	static final int				NO_DB_SELECTED_DIALOG	= 3;
	AlertDialog						alertDialog;
	ProgressDialog					progressDialog;
	private static List<Database>	databases;
	private static List<Category>	categories;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
			int StartUpSelection = sharedPreferences.getInt(SettingsActivity.SELECTION_KEY, R.id.RBNews);
			if (StartUpSelection != R.id.RBSearch)
				this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		// search field part
		View searchField = findViewById(R.id.searchFieldLayout);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		int show = sharedPreferences.getInt(SettingsActivity.SHOW_KEY, 0);
		if (show == 1)
			searchField.setVisibility(View.VISIBLE);
		else
			searchField.setVisibility(View.GONE);
		Spinner S = (Spinner) findViewById(R.id.searchFieldSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(	this, R.array.searchFieldArray,
																				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		S.setAdapter(adapter);
		S.setOnItemSelectedListener(this);

		// subject/databases part
		categories = mediaApp.getCategories();
		if (categories != null)
		{
			LV = (ListView) findViewById(R.id.LSearchBy);
			LV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			List<String> dbs = new ArrayList<String>();
			for (Category c : categories)
				dbs.add(c.getName());
			// databases.add("All databases");
			LV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dbs));
			LV.setOnItemClickListener(this);

			databases = mediaApp.getDatabases();
		}
		else
		{
			mediaApp.setCategoryListener(this);
			LV = (ListView) findViewById(R.id.LSearchBy);
			LV.setVisibility(View.GONE);

			ProgressBar pbLoading = (ProgressBar) findViewById(R.id.PBLoading);
			pbLoading.setVisibility(View.VISIBLE);
		}

		ETSearchField = (EditText) findViewById(R.id.ETSearch);
		// button part
		Button button = (Button) findViewById(R.id.searchBut);
		button.setOnClickListener(this);

		RGSearchBy = (RadioGroup) findViewById(R.id.RGSearchBy);
		RGSearchBy.setOnCheckedChangeListener(this);
	}

	@Override
	protected void onResume()
	{
		View searchField = findViewById(R.id.searchFieldLayout);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		int show = sharedPreferences.getInt(SettingsActivity.SHOW_KEY, 0);
		if (show == 1)
			searchField.setVisibility(View.VISIBLE);
		else
			searchField.setVisibility(View.GONE);
		super.onResume();
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

	protected Dialog onCreateDialog(int id)
	{
		switch (id)
		{
			case SEARCHING_DIALOG:
				progressDialog = new ProgressDialog(SearchActivity.this);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setMessage(getString(R.string.searchLoading));
				progressDialog.setCancelable(false);
				return progressDialog;
			case NO_SEARCH_TERM_DIALOG:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.appName);
				builder.setMessage("No search term was provided");
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
					builder.setIconAttribute(android.R.attr.alertDialogIcon);
				else
					builder.setIcon(R.drawable.ic_dialog_alert_holo_light);
				builder.setNeutralButton("OK", this);
				alertDialog = builder.create();
				return alertDialog;
			case NO_DB_SELECTED_DIALOG:
				AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
				builder2.setTitle(R.string.appName);
				builder2.setMessage("No Category was selected");
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
					builder2.setIconAttribute(android.R.attr.alertDialogIcon);
				else
					builder2.setIcon(R.drawable.ic_dialog_alert_holo_light);
				builder2.setNeutralButton("OK", this);
				alertDialog = builder2.create();
				return alertDialog;
			default:
				return null;
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.searchBut:
				if (ETSearchField.length() == 0)
				{
					showDialog(NO_SEARCH_TERM_DIALOG);
					return;
				}
				else if (LV.getCheckItemIds() == null || LV.getCheckItemIds().length == 0)
				{
					showDialog(NO_DB_SELECTED_DIALOG);
					return;
				}
				else
				{
					showDialog(SEARCHING_DIALOG);
					new HTTPGetTask(this, (MediaApplication) getApplication()).execute(createURL(0));
				}
				break;
		}
	}

	@Override
	public void onResponseReceived(String response)
	{
		Log.i(TAG, "response: " + response);
		removeDialog(SEARCHING_DIALOG);
		LucasParser lp = new LucasParser();
		List<LucasResult> list = lp.parse(response);

		((MediaApplication) getApplication()).setResults(list);

		Intent resultIntent = new Intent(this, SearchResultActivity.class);
		startActivity(resultIntent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		/*
		 * if (position == LV.getCount() - 1) { // select all item boolean isChecked =
		 * LV.isItemChecked(position); for (int i = 0; i < LV.getCount() - 1; i++) LV.setItemChecked(i,
		 * isChecked); } else LV.setItemChecked(LV.getCount() - 1, false);
		 */
	}

	private String createURL(int startRecord)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String URL = "http://yd3wb8fs2g.cs.xml.serialssolutions.com.www.dbproxy.hu.nl/sru?version=1.1&recordSchema=cs1.2&operation=searchRetrieve";

		if (startRecord > 0)
		{
			URL += "&startRecord=";
			URL += startRecord;
		}

		URL += "&maximumRecords=";
		int AmountOfResultPos = sharedPreferences.getInt(SettingsActivity.AMOUNT_KEY, 0);
		URL += (getResources().getStringArray(R.array.settingsAmountOfResultsArray))[AmountOfResultPos];

		switch (RGSearchBy.getCheckedRadioButtonId())
		{
			case R.id.RBSearchBySubject:
				URL += "&x-cs-categories=";
				for (int i = 0; i < LV.getCount(); i++)
				{
					if (LV.isItemChecked(i))
					{
						URL += categories.get(i).getURL();
						URL += ",";
					}
				}
				URL = URL.substring(0, URL.length() - 1);
				break;

			case R.id.RBSearchByDatabase:
				URL += "&x-cs-databases=";
				for (int i = 0; i < LV.getCount(); i++)
				{
					if (LV.isItemChecked(i))
					{
						URL += databases.get(i).getId();
						URL += ",";
					}
				}
				URL = URL.substring(0, URL.length() - 1);
				break;
		}

		URL += "&query=";
		URL += ETSearchField.getText().toString();
		Log.i(TAG, URL);
		return URL;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0)
	{

	}

	@Override
	public void onClick(DialogInterface dialog, int arg1)
	{
		if (dialog.equals(alertDialog))
		{
			removeDialog(NO_DB_SELECTED_DIALOG);
			removeDialog(NO_SEARCH_TERM_DIALOG);
		}
		if (dialog.equals(progressDialog))
		{
			removeDialog(SEARCHING_DIALOG);
		}
	}

	@Override
	public void categoriesLoaded()
	{
		LV = (ListView) findViewById(R.id.LSearchBy);
		LV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		List<String> dbs = new ArrayList<String>();
		for (Category c : categories)
			dbs.add(c.getName());
		// databases.add("All databases");
		LV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dbs));
		LV.setOnItemClickListener(this);
		LV.setVisibility(View.VISIBLE);

		ProgressBar pbLoading = (ProgressBar) findViewById(R.id.PBLoading);
		pbLoading.setVisibility(View.GONE);

		if (databases == null)
			databases = mediaApp.getDatabases();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		List<String> dbs = new ArrayList<String>();

		switch (checkedId)
		{
			case R.id.RBSearchBySubject:
				for (Category c : categories)
					dbs.add(c.getName());
				break;
			case R.id.RBSearchByDatabase:
				if (databases == null)
					databases = mediaApp.getDatabases();
				for (Database d : databases)
					dbs.add(d.getName());
				break;
		}

		if (dbs.size() > 0)
			LV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dbs));
	}

	@Override
	public void onNullResponseReceived()
	{
	}
}
