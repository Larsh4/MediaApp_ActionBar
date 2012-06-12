package mediaApp.main;

import java.util.List;
import mediaApp.HTTP.HTTPGetTask;
import mediaApp.HTTP.HTTPResponseListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.Spinner;

public class SearchActivity extends BaseActivity implements
		HTTPResponseListener,
		OnClickListener,
		OnItemClickListener,
		OnItemSelectedListener
{

	private static String	TAG	= "SearchAct";
	private ListView		LV;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		// search field part
		Spinner spinner = (Spinner) findViewById(R.id.searchFieldSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(	this, R.array.searchFieldArray,
																				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);

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
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.search, menu);
		return true;// super.onCreateOptionsMenu(menu);
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
	{

	}

	@SuppressWarnings("rawtypes")
	public void onNothingSelected(AdapterView parent)
	{
		// Do nothing.
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.searchBut:

				// String URL =
				// "http://yd3wb8fs2g.cs.xml.serialssolutions.com/sru?version=1.1&query=title+any+aardvark&recordSchema=cs1.2&operation=searchRetrieve&x-cs-categories=101486,101500";
				new HTTPGetTask(this).execute(createURL());
				break;
		}
	}

	@Override
	public void onResponseReceived(String response)
	{
		LucasParser lp = new LucasParser();
		List<LucasResult> list = lp.parse(response);
		for (LucasResult r : list)
		{
			Log.d(TAG, r.toString());
		}

		((MediaApplication) getApplication()).setResults(list);

		Intent resultIntent = new Intent(this, SearchResultActivity.class);
		startActivity(resultIntent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		if (position == 0)
		{ // select all item
			boolean isChecked = LV.isItemChecked(position);
			for (int i = 1; i < LV.getCount(); i++)
				LV.setItemChecked(i, isChecked);
		}
		else
			LV.setItemChecked(0, false);
	}

	private String createURL()
	{
		String URL = "http://yd3wb8fs2g.cs.xml.serialssolutions.com/sru?version=1.1&query=title+any+aardvark&recordSchema=cs1.2&operation=searchRetrieve&x-cs-categories=";

		for (int i = 1; i < LV.getCount() - 1; i++) // one less because of 'Other Databases'
		{
			if (LV.isItemChecked(i))
			{
				URL += getResources().getIntArray(R.array.dbCodes)[i - 1];
				URL += ",";
			}
		}
		URL = URL.substring(0, URL.length() - 1);

		return URL;
	}
}
