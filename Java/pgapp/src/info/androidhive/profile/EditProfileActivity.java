package info.androidhive.profile;

import info.androidhive.imageslider.R;
import info.androidhive.lazyimagedownload.ImageLoader;
import info.androidhive.lazyimagedownload.MainActivity;
import info.androidhive.profile.ImageLoaderCover;
import info.androidhive.profile.JSONParser;
import info.androidhive.profile.EditProfileAsyncTask;
import info.androidhive.sqlite.DataBaseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfileActivity extends Activity implements View.OnClickListener {
	private ImageLoader imgLoader;
	private ImageLoaderCover imgLoaderCover;
	private ImageButton button;
	private Button buttonEditSave, buttonEditCancel;
	private String strURL = "http://www.tamavodka.net23.net/images/";
	private String description1, description2, description3;
	private EditText editUsername, editUserPhone, editDesc01, editDesc02, editDesc03;
	private ProgressDialog pDialog;
	// JSON parser class
	JSONParser jsonParser = new JSONParser();
	//Instance AsyncTask for updating database user editing profile info
	EditProfileAsyncTask editAsync = new EditProfileAsyncTask(EditProfileActivity.this);
	//Read User Profile Info Sqlite database
	final DataBaseHandler db = new DataBaseHandler(EditProfileActivity.this);
	// single product url
	private static final String url_edit_profile = "http://www.tamavodka.net23.net/android_connect/edit_profile_update.php";
	// webservice json nodes
	private static final String TAG_SUCCESS = "success";    
	private static final String TAG_PRODUCT = "product";
	private static final String TAG_ID = "pid";
	private static final String TAG_NAME = "username";
	private static final String TAG_PHONE = "userphone";
	private static final String TAG_DESCRIPTION1 = "description01";
	private static final String TAG_DESCRIPTION2 = "description02";
	private static final String TAG_DESCRIPTION3 = "description03";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Intent getId = getIntent();
		String getIntentId = getId.getStringExtra("EditPhone"); // idphone
		String getIntentUser = getId.getStringExtra("EditName");
		
        editUsername = (EditText) findViewById(R.id.profile_name);
        editUserPhone = (EditText) findViewById(R.id.profile_phone);
        editDesc01 = (EditText) findViewById(R.id.EditText01);
        editDesc02 = (EditText) findViewById(R.id.EditText02);
        editDesc03 = (EditText) findViewById(R.id.EditText03);
        
        description1 = db.getContact(Integer.parseInt(getIntentId)).getName();
        
        editUsername.setText(getIntentUser);
        editUserPhone.setText(getIntentId);
        editDesc01.setText(description1);
        editDesc02.setText("1.67 m");
        editDesc03.setText("56 kg");
        
        button = (ImageButton) findViewById(R.id.imageButton1);
    	button.setOnClickListener(this);
    	buttonEditSave = (Button) findViewById(R.id.button5);
    	buttonEditSave.setOnClickListener(this);
    	buttonEditCancel = (Button) findViewById(R.id.button4);
    	buttonEditCancel.setOnClickListener(this);
    	
    	
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
			case R.id.imageButton1:
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:912345678"));
				startActivity(callIntent);
			break;
			case R.id.button5:
				Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
				// http: //stackoverflow.com/questions/15487807/android-development-having-an-asynctask-in-a-separate-class-file
				//AsyncTask<String, String, String> editAsync = new EditProfileAsyncTask(arg0.getContext());
				editAsync.execute(editUsername.getText().toString(),editUserPhone.getText().toString());
			break;
			case R.id.button4:
				Toast.makeText(getBaseContext(), "canceled", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
