package mediaApp.main;

import mediaApp.compatible.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class BaseActivity extends ActionBarActivity
{

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
				Intent serverIntent = new Intent(this, MainActivity.class);
				serverIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(serverIntent);
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
