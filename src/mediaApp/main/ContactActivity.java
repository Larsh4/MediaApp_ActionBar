package mediaApp.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ContactActivity extends BaseActivity implements OnClickListener
{
	static final String TAG 			= "ContactAct";
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}
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
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contact, menu);
        return true;//super.onCreateOptionsMenu(menu);
    }

	@Override
	public void onClick(View v)
	{
		Intent intent;		
		switch (v.getId())
		{
			//FCJ
			case R.id.BCallFCJ:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:088 481 35 83"));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFCJ:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HU Mediatheek Android App");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "mediatheek.fcj@hu.nl" });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFCJ:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Padualaan 99, Utrecht"));
				startActivity(Intent.createChooser(intent, "Show on Map..."));
				break;	
			//FE	
			case R.id.BCallFE:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:088 481 71 25"));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFE:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HU Mediatheek Android App");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "mediatheek.fe@hu.nl" });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFE:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Padualaan 97, Utrecht"));
				startActivity(Intent.createChooser(intent, "Show on Map..."));
				break;		
			//FEM	
			case R.id.BCallFEM:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:088 481 64 33"));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFEM:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HU Mediatheek Android App");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "mediatheek.fem@hu.nl" });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFEM:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Padualaan 101, Utrecht"));
				startActivity(Intent.createChooser(intent, "Show on Map..."));
				break;	
			//FG	
			case R.id.BCallFG:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:088 481 51 59"));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFG:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HU Mediatheek Android App");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "mediatheek.fg@hu.nl" });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFG:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Bolognalaan 101, Utrecht"));
				startActivity(Intent.createChooser(intent, "Show on Map..."));
				break;		
			//FMR	
			case R.id.BCallFMR:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:+31612345678"));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFMR:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HU Mediatheek Android App");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "mediatheek.fmr@hu.nl" });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFMR:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Heidelberglaan 7, Utrecht"));
				startActivity(Intent.createChooser(intent, "Show on Map..."));
				break;	
			//FNT NN1	
			case R.id.BCallFNTNN1:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:088 481 98 78"));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFNTNN1:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HU Mediatheek Android App");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "mediatheek.fnt@hu.nl" });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFNTNN1:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q= Nijenoord 1, Utrecht"));
				startActivity(Intent.createChooser(intent, "Show on Map..."));
				break;	
			//FNT/ILC	
			case R.id.BCallFNTILC:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:088 481 81 88"));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailFNTILC:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HU Mediatheek Android App");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "mediatheek.ilc@hu.nl" });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationFNTILC:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q= F.C.Dondersstraat 65, Utrecht"));
				startActivity(Intent.createChooser(intent, "Show on Map..."));
				break;	
			//HU Amersfoort	
			case R.id.BCallHUAmers:
				intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:088 481 88 61"));
				startActivity(Intent.createChooser(intent, "Call..."));
				break;
			case R.id.BEmailHUAmers:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HU Mediatheek Android App");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "mediatheek.amersfoort@hu.nl" });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;
			case R.id.BLocationHUAmers:
				intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=De Nieuwe Poort 21, Amersfoort"));
				startActivity(Intent.createChooser(intent, "Show on Map..."));
				break;	
			//Mediatheek Algemeen	
			case R.id.BEmailAlgemeen:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				//intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HU Mediatheek Android App");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "mediatheek@hu.nl" });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;	
			//Webmaster	
			case R.id.BEmailWebmaster:
				intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HU Mediatheek Android App");
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "harold.bakker@hu.nl" });
				startActivity(Intent.createChooser(intent, "Send mail..."));
				break;	
		}
	}

}
