package mediaApp.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class SettingsActivity extends BaseActivity implements
		OnCheckedChangeListener,
		android.widget.CompoundButton.OnCheckedChangeListener,
		OnClickListener,
		DialogInterface.OnClickListener,
		OnItemSelectedListener
{

	static final String	TAG				= "SettingsAct";

	// UI
	private RadioGroup			RGStartup;
	private CheckBox			CBShowSearch;
	private Spinner				SAmount;
	private Button				BLogout;
	private Button				BAbout;
	static final int			ABOUT_DIALOG	= 1;
	private AlertDialog			aboutDialog;
	// Preference Keys
	static final String	SELECTION_KEY	= "RGStartupSel";
	static final String	AMOUNT_KEY		= "SAmountOfResults";
	static final String	SHOW_KEY		= "CHShowSearch";

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		RGStartup = (RadioGroup) findViewById(R.id.RBGStart);
		CBShowSearch = (CheckBox) findViewById(R.id.CBShowSearch);
		SAmount = (Spinner) findViewById(R.id.SAmountOfResults);
		BLogout = (Button) findViewById(R.id.BLogout);
		BAbout  = (Button) findViewById(R.id.BAbout);
		
		LoadPreferences();
		
		RGStartup.setOnCheckedChangeListener(this);
		CBShowSearch.setOnCheckedChangeListener(this);
		SAmount.setOnItemSelectedListener(this);
		BLogout.setOnClickListener(this);
		BAbout.setOnClickListener(this);
	}
	
	
	protected Dialog onCreateDialog(int id)
	{ 
		switch (id)
		{
			case ABOUT_DIALOG:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);				
				String versionName = "1.0";
				try {
					versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0 ).versionName;
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
				builder.setTitle(getString(R.string.dialogAboutTitle));
				builder.setMessage(getString(R.string.dialogAboutText)+
						" "+versionName);				
				builder.setIcon(R.drawable.ic_home);
				builder.setNeutralButton("OK", this);
				aboutDialog = builder.create();
				return aboutDialog;
			default:
				return null;
		}
	}

	public void onClick(View v)
	{
		if (v == BLogout)
		{
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			Editor editor = prefs.edit();
			editor.remove(MainActivity.USER_KEY);
			editor.remove(MainActivity.PASS_KEY);
			editor.remove(MainActivity.REMEMBER_KEY);
			editor.commit();

			startActivity(new Intent(this, MainActivity.class));
			finish();
		}else if(v == BAbout)
		{
			showDialog(ABOUT_DIALOG);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.settings, menu);
		return true;// super.onCreateOptionsMenu(menu);
	}

	private void LoadPreferences()
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		int RadioButtonSelection = sharedPreferences.getInt(SELECTION_KEY, R.id.RBNews);
		RGStartup.check(RadioButtonSelection);
		int CheckBoxState = sharedPreferences.getInt(SHOW_KEY, 0);
		if (CheckBoxState == 1)
			CBShowSearch.setChecked(true);
		int SpinnerPos = sharedPreferences.getInt(AMOUNT_KEY, 0);
		SAmount.setSelection(SpinnerPos);
	}

	public void onCheckedChanged(RadioGroup arg0, int arg1)
	{
		SaveIntPreferences(SELECTION_KEY, arg1);
	}

	public void onCheckedChanged(CompoundButton arg0, boolean arg1)
	{
		int i;
		if (arg1)
			i = 1;
		else
			i = 0;
		SaveIntPreferences(SHOW_KEY, i);
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		SaveIntPreferences(AMOUNT_KEY, arg2);		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}


	public void onClick(DialogInterface dialog, int which)
	{
		if (dialog.equals(aboutDialog))
		{
			removeDialog(ABOUT_DIALOG);
		}		
	}
}
