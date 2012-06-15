package mediaApp.main;

import mediaApp.compatible.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

public abstract class BaseActivity extends ActionBarActivity
{

	protected MediaApplication	mediaApp;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mediaApp = (MediaApplication) getApplication();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
				int StartUpSelection = sharedPreferences.getInt(SettingsActivity.SELECTION_KEY, R.id.RBNews);
				Intent intent;
				switch (StartUpSelection)
				{
					case R.id.RBSearch:
						intent = new Intent(this, SearchActivity.class);
						break;
					case R.id.RBNews:
						intent = new Intent(this, NewsActivity.class);
						break;
					case R.id.RBContact:
						intent = new Intent(this, ContactActivity.class);
						break;
					default:
						intent = new Intent(this, NewsActivity.class);
						break;
				}
				startActivity(intent);
				break;

			case R.id.menu_news:
				// Toast.makeText(this, "Fake refreshing...", Toast.LENGTH_SHORT).show();
				getActionBarHelper().setRefreshActionItemState(true);
				getWindow().getDecorView().postDelayed(new Runnable() {

					@Override
					public void run()
					{
						getActionBarHelper().setRefreshActionItemState(false);
					}
				}, 1000);
				final Intent news = new Intent(this, NewsActivity.class);
				startActivity(news);
				break;

			case R.id.menu_search:
				final Intent search = new Intent(this, SearchActivity.class);
				startActivity(search);
				break;

			case R.id.menu_contact:
				final Intent contact = new Intent(this, ContactActivity.class);
				startActivity(contact);
				break;

			case R.id.menu_settings:
				final Intent settings = new Intent(this, SettingsActivity.class);
				startActivity(settings);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void SaveStringPreferences(String key, String value)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	protected void SaveIntPreferences(String key, int value)
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
}
