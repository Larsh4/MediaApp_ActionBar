
package mediaApp.main;

import mediaApp.HTTP.HTTPResponseListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends BaseActivity implements 
					HTTPResponseListener, OnClickListener{

	static final String TAG = "MainAct";
	
	//Dialogs
	static final int CONNECTING_DIALOG = 1;   
    ProgressDialog progressDialog;
    //Preference Keys
    static final String REMEMBER_KEY	= "remember";
    static final String USER_KEY		= "user";
    static final String PASS_KEY		= "pass";    
    //UI
	EditText ETUser, ETPass;
	CheckBox CHRemember;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        	this.getActionBar().setHomeButtonEnabled(true);
        }       
        ETUser = (EditText)findViewById(R.id.ETLoginUser);
        ETPass = (EditText)findViewById(R.id.ETLoginPass);
        
        Button login = (Button)findViewById(R.id.BLogin);
        login.setOnClickListener( this);
        
        CHRemember = (CheckBox)findViewById(R.id.CHLoginRemember);
        
        LoadPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater menuInflater = getMenuInflater();
        //menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    protected Dialog onCreateDialog(int id) {
  	   Log.v(TAG, "onCreateDialog");
       switch(id) {
         	case CONNECTING_DIALOG:
	             progressDialog = new ProgressDialog(MainActivity.this);
	             progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	             progressDialog.setMessage("Logging in...");
	             progressDialog.setCancelable(false);
	             return progressDialog;
        	default:
        		return null;
        }
    }
     
	 @Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		Log.v(TAG, "onPrepareDialog");
        switch(id) {
       		case CONNECTING_DIALOG:
       			progressDialog.setMessage("Logging in...");
       			break;
         }
     }

	@Override
	public void onClick(View v) {
		//click login
		SaveIntPreferences(REMEMBER_KEY, CHRemember.isChecked()?1:0 );
		if(CHRemember.isChecked()){			
			SaveStringPreferences(USER_KEY, ETUser.getText().toString());
			SaveStringPreferences(PASS_KEY, ETPass.getText().toString());
		}
		else{
			SaveStringPreferences(USER_KEY, "");
			SaveStringPreferences(PASS_KEY, "");			
		}
		
		//showDialog(CONNECTING_DIALOG);
		//ProxyLoginTask plt = new ProxyLoginTask(this);
		//plt.execute("https://login.www.dbproxy.hu.nl/login",ETUser.getText().toString(), ETPass.getText().toString());
		
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int StartUpSelection = sharedPreferences.getInt(SettingsActivity.SELECTION_KEY, R.id.RBNews);
        Log.i(TAG, "id="+StartUpSelection);
        Intent serverIntent;
        switch(StartUpSelection){
        	case R.id.RBSearch:
        		serverIntent = new Intent(this, SearchActivity.class);
				break;			
			case R.id.RBNews:
				serverIntent = new Intent(this, NewsActivity.class);
				break;
			case R.id.RBContact:	
				serverIntent = new Intent(this, ContactActivity.class);
				break;
			default:	
				serverIntent = new Intent(this, NewsActivity.class);
				break;
        }
        startActivity(serverIntent);        
	}

      
   private void LoadPreferences(){
	    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	    boolean remember = (sharedPreferences.getInt(REMEMBER_KEY, 0)==1);
        String strSavedMem1 = sharedPreferences.getString(USER_KEY, "");
        String strSavedMem2 = sharedPreferences.getString(PASS_KEY, "");
        CHRemember.setChecked(remember);
        ETUser.setText(strSavedMem1);
        ETPass.setText(strSavedMem2);
   }   	 

	/*private void proxyLogin(String user, String pass) {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();	    
	    HttpPost httppost = new HttpPost("https://login.www.dbproxy.hu.nl/login");

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("user", user));
	        nameValuePairs.add(new BasicNameValuePair("pass", pass));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	        Log.i(TAG, EntityUtils.toString(response.getEntity()));
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }	    	    	    
	}*/
	
	@Override
	public void onResponseReceived(String response) {
		Log.v(TAG, "onResponseReceived");
		// TODO Auto-generated method stub
		removeDialog(CONNECTING_DIALOG);
	}
   
}
