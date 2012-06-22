package mediaApp.main;

import mediaApp.HTTP.HTTPResponseListener;
import mediaApp.HTTP.ProxyLoginTask;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class MainActivity extends BaseActivity implements
		HTTPResponseListener,
		OnClickListener,
		android.content.DialogInterface.OnClickListener,
		OnKeyListener
{

	// Dialogs
	static final int	LOGGING_IN_DIALOG	= 1;
	ProgressDialog		progressDialog;
	static final int	UNSUCCESSFUL_DIALOG	= 2;
	AlertDialog			alertDialog;	
	// UI
	EditText			ETUser, ETPass;
	Button				BLogin;
	CheckBox			CHRemember;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			this.getActionBar().setHomeButtonEnabled(true);
		}
		// UI
		ETUser = (EditText) findViewById(R.id.ETLoginUser);
		ETPass = (EditText) findViewById(R.id.ETLoginPass);
		ETPass.setOnKeyListener(this);
		CHRemember = (CheckBox) findViewById(R.id.CHLoginRemember);
		BLogin = ((Button) findViewById(R.id.BLogin));
		BLogin.setOnClickListener(this);
		if(savedInstanceState!=null)
		{
			ETUser.setText(savedInstanceState.getString(SettingsActivity.USER_KEY));
			ETPass.setText(savedInstanceState.getString(SettingsActivity.PASS_KEY));
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		outState.putString(SettingsActivity.USER_KEY, ETUser.getText().toString());
		outState.putString(SettingsActivity.PASS_KEY, ETPass.getText().toString());
		super.onSaveInstanceState(outState);
	}

	
	protected void onStart()
	{
		super.onResume();
		LoadPreferences();
	}

	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return true;
	}

	protected Dialog onCreateDialog(int id)
	{
		switch (id)
		{
			case LOGGING_IN_DIALOG:
				progressDialog = new ProgressDialog(MainActivity.this);
				progressDialog.setMessage(getString(R.string.dialogLoggingIn));
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setCancelable(false);
				return progressDialog;
			case UNSUCCESSFUL_DIALOG:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.appName);
				builder.setMessage(getString(R.string.dialogLoginUnsuccessful));
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
					builder.setIconAttribute(android.R.attr.alertDialogIcon);
				else
					builder.setIcon(R.drawable.ic_dialog_alert_holo_light);
				builder.setNeutralButton("OK", this);
				alertDialog = builder.create();
				return alertDialog;
			default:
				return null;
		}
	}

	
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.BLogin:
				login();
				break;
		}
	}

	private void LoadPreferences()
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean remember = (sharedPreferences.getInt(SettingsActivity.REMEMBER_KEY, 0) == 1);		
		CHRemember.setChecked(remember);		
		if(ETUser.getText().length()==0 && ETPass.getText().length()==0){
			String strSavedMem = sharedPreferences.getString(SettingsActivity.USER_KEY, "");			
			ETUser.setText(strSavedMem);			
			strSavedMem = sharedPreferences.getString(SettingsActivity.PASS_KEY, "");			
			ETPass.setText(strSavedMem);
			if(ETUser.getText().length()!=0 && ETPass.getText().length()!=0)
				login();
		}			
	}

	
	public void onResponseReceived(String response)
	{
		removeDialog(LOGGING_IN_DIALOG);

		if (response != null && response.startsWith("<html>\n<head>\n<title>Database Menu</title>"))
		{
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
			int StartUpSelection = sharedPreferences.getInt(SettingsActivity.SELECTION_KEY, R.id.RBNews);
			CookieSyncManager.createInstance(getApplicationContext());
			CookieSyncManager.getInstance().sync();
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
			finish();
		}
		else
		{
			showDialog(UNSUCCESSFUL_DIALOG);
		}
	}

	
	public void onClick(DialogInterface dialog, int which)
	{
		if (dialog.equals(alertDialog))
		{
			removeDialog(UNSUCCESSFUL_DIALOG);
		}
		if (dialog.equals(progressDialog))
		{
			removeDialog(LOGGING_IN_DIALOG);
		}
	}

	private void login()
	{
		SaveIntPreferences(SettingsActivity.REMEMBER_KEY, CHRemember.isChecked() ? 1 : 0);
		if (CHRemember.isChecked())
		{
			SaveStringPreferences(SettingsActivity.USER_KEY, ETUser.getText().toString());
			SaveStringPreferences(SettingsActivity.PASS_KEY, ETPass.getText().toString());
		}
		else
		{
			SaveStringPreferences(SettingsActivity.USER_KEY, "");
			SaveStringPreferences(SettingsActivity.PASS_KEY, "");
		}

		showDialog(LOGGING_IN_DIALOG);
		final Handler handler = new Handler();
		final MainActivity act = this;
		handler.postDelayed(new Runnable() {

			
			public void run()
			{
				mediaApp.refreshHttp();
				ProxyLoginTask plt = new ProxyLoginTask(act, mediaApp);
				plt.execute("http://login.www.dbproxy.hu.nl/login", ETUser.getText().toString(), ETPass.getText()
						.toString());
			}
		}, 1500);
		GoogleAnalyticsTracker tracker = ((MediaApplication) getApplication()).getTracker();
		tracker.trackEvent("Android", "proxy", "login", 0);
		tracker.dispatch();
	}

	
	public void onNullResponseReceived()
	{
		removeDialog(LOGGING_IN_DIALOG);
		showDialog(UNSUCCESSFUL_DIALOG);
	}
	
	
	public boolean onKey(View arg0, int keyCode, KeyEvent event) {
		if((event.getAction() == KeyEvent.ACTION_DOWN) &&
				(keyCode == KeyEvent.KEYCODE_ENTER)){			
			onClick(BLogin);
			return true;
		}			
		return false;
	}
}
