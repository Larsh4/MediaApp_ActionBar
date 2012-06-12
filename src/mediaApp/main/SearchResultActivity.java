package mediaApp.main;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SearchResultActivity extends BaseActivity implements OnItemClickListener
{

	private static final String			TAG	= "SearchResultAct";
	private static List<LucasResult>	results;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchresult);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		ListView resultList = (ListView) findViewById(R.id.LVResults);
		MediaApplication app = (MediaApplication) getApplication();
		results = app.getResults();
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		resultList.setAdapter(new SearchResultAdapter(inflater, results));
		resultList.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		String resultUrl = results.get(position).getUrl().toString();

		Intent resultDetailIntent = new Intent(this, ResultDetailActivity.class);
		resultDetailIntent.putExtra("url", resultUrl);
		startActivity(resultDetailIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.searchresult, menu);
		return super.onCreateOptionsMenu(menu);
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
}
