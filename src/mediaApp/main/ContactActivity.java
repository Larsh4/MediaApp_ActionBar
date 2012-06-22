package mediaApp.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ContactActivity extends BaseActivity implements OnClickListener
{	
	private Resources res;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
			int StartUpSelection = sharedPreferences.getInt(SettingsActivity.SELECTION_KEY, R.id.RBNews);
			if(StartUpSelection!=R.id.RBContact)
				this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		res = getResources();
		//FCJ
		Button but = (Button) findViewById(R.id.BEmailFCJ);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BCallFCJ);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BLocationFCJ);
		but.setOnClickListener(this);
		//FE
		but = (Button) findViewById(R.id.BEmailFE);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BCallFE);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BLocationFE);
		but.setOnClickListener(this);
		//FEM
		but = (Button) findViewById(R.id.BEmailFEM);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BCallFEM);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BLocationFEM);
		but.setOnClickListener(this);
		//FG
		but = (Button) findViewById(R.id.BEmailFG);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BCallFG);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BLocationFG);
		but.setOnClickListener(this);
		//FMR
		but = (Button) findViewById(R.id.BEmailFMR);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BCallFMR);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BLocationFMR);
		but.setOnClickListener(this);
		//FNT NN1
		but = (Button) findViewById(R.id.BEmailFNTNN1);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BCallFNTNN1);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BLocationFNTNN1);
		but.setOnClickListener(this);
		//FNT/ILC
		but = (Button) findViewById(R.id.BEmailFNTILC);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BCallFNTILC);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BLocationFNTILC);
		but.setOnClickListener(this);
		//HU Amersfoort
		but = (Button) findViewById(R.id.BEmailHUAmers);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BCallHUAmers);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.BLocationHUAmers);
		but.setOnClickListener(this);
		//Mediatheek alemeen
		but = (Button) findViewById(R.id.BEmailAlgemeen);
		but.setOnClickListener(this);
		//Webmaster
		but = (Button) findViewById(R.id.BEmailWebmaster);
		but.setOnClickListener(this);	
	}	
	
	
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contact, menu);
        return true;//super.onCreateOptionsMenu(menu);
    }

	
	public void onClick(View v)
	{
		Intent intent;		
		switch (v.getId())
		{
			//FCJ
			case R.id.BCallFCJ:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:"+res.getString(R.string.contactFCJ_call)));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFCJ:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { res.getString(R.string.contactFCJ_email) });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFCJ:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+res.getString(R.string.contactFCJ_location)));
				startActivity(Intent.createChooser(intent, getString(R.string.contactLocationIntent)));
				break;	
			//FE	
			case R.id.BCallFE:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:"+res.getString(R.string.contactFE_call)));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFE:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { res.getString(R.string.contactFE_email) });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFE:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+res.getString(R.string.contactFE_location)));
				startActivity(Intent.createChooser(intent, getString(R.string.contactLocationIntent)));
				break;			
			//FEM	
			case R.id.BCallFEM:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:"+res.getString(R.string.contactFEM_call)));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFEM:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { res.getString(R.string.contactFEM_email) });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFEM:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+res.getString(R.string.contactFEM_location)));
				startActivity(Intent.createChooser(intent, getString(R.string.contactLocationIntent)));
				break;		
			//FG	
			case R.id.BCallFG:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:"+res.getString(R.string.contactFG_call)));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFG:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { res.getString(R.string.contactFG_email) });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFG:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+res.getString(R.string.contactFG_location)));
				startActivity(Intent.createChooser(intent, getString(R.string.contactLocationIntent)));
				break;			
			//FMR	
			case R.id.BCallFMR:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:"+res.getString(R.string.contactFE_call)));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFMR:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { res.getString(R.string.contactFMR_email) });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFMR:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+res.getString(R.string.contactFMR_location)));
				startActivity(Intent.createChooser(intent, getString(R.string.contactLocationIntent)));
				break;	
			//FNT NN1	
			case R.id.BCallFNTNN1:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:"+res.getString(R.string.contactFNTNN1_call)));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFNTNN1:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { res.getString(R.string.contactFNTNN1_email) });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFNTNN1:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+res.getString(R.string.contactFNTNN1_location)));
				startActivity(Intent.createChooser(intent, getString(R.string.contactLocationIntent)));
				break;		
			//FNT/ILC	
			case R.id.BCallFNTILC:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:"+res.getString(R.string.contactFNTILC_call)));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFNTILC:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { res.getString(R.string.contactFNTILC_email) });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFNTILC:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+res.getString(R.string.contactFNTILC_location)));
				startActivity(Intent.createChooser(intent, getString(R.string.contactLocationIntent)));
				break;	
			//HU Amersfoort	
			case R.id.BCallHUAmers:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:"+res.getString(R.string.contactHUAmers_call)));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailHUAmers:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { res.getString(R.string.contactHUAmers_email) });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationHUAmers:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+res.getString(R.string.contactHUAmers_location)));
				startActivity(Intent.createChooser(intent, getString(R.string.contactLocationIntent)));
				break;		
			//Mediatheek Algemeen	
			case R.id.BEmailAlgemeen:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("message/rfc822");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { res.getString(R.string.contactAlgemeen_email) });
				startActivity(Intent.createChooser(intent, getString(R.string.contactEmailIntent)));
				break;	
			//Webmaster	
			case R.id.BEmailWebmaster:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("message/rfc822");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HU Mediatheek Android App");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { res.getString(R.string.contactWebmaster_email) });
				startActivity(Intent.createChooser(intent, getString(R.string.contactEmailIntent)));
				break;	
		}
	}

}
