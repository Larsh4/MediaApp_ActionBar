package mediaApp.main;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SettingsActivity extends BaseActivity implements OnCheckedChangeListener
{
	static final String TAG = "SettingsAct";
	
	//UI
	RadioGroup			RGStartup;
	//Preference Keys
	static final String	SELECTION_KEY	= "RGStartupSel";	

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		RGStartup = (RadioGroup) findViewById(R.id.RBGStart);
		LoadPreferences();
		RGStartup.setOnCheckedChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.settings, menu);
		return true;//super.onCreateOptionsMenu(menu);
	}

	private void LoadPreferences()
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		int RadioButtonSelection = sharedPreferences.getInt(SELECTION_KEY, R.id.RBNews);
		RGStartup.check(RadioButtonSelection);
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1)
	{
		SaveIntPreferences(SELECTION_KEY, arg1);
	}
}
