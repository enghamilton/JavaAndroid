package info.androidhive.profile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.phpmysql.AllProductsActivity;
import info.androidhive.phpmysql.EditProductActivity;
import info.androidhive.profile.JSONParser;
import info.androidhive.imageslider.GridViewActivity;
import info.androidhive.lazyimagedownload.ImageLoader;
import info.androidhive.profile.ImageLoaderCover;
import info.androidhive.sqlite.DataBaseHandler;
import info.androidhive.lazyimagedownload.MainActivity;

import info.androidhive.imageslider.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class ProfileMainActivity extends Activity implements View.OnClickListener {

	
	TextView textView01;
	TextView textView02;
	TextView textView03;
	TextView textView04;
	TextView textView05;
	
	private ImageView imgView;
	private ImageView imgView2;
	private ImageButton button;
	//private Button buttonfollow;
	private Button buttonEditProfile;
	private Button buttonPhotos;
	private Button buttonAgenda;
	private ImageLoader imgLoader;
	private ImageLoaderCover imgLoaderCover;
	private TextView userphone;
	private TextView username;	
	private String strURL = "http://www.tamavodka.net23.net/images/";
	public String userAge;
	public String userHeight;
	public String userWeight;
	public String userOral;
	public String userPrice;
	
	String getIntentImage;
	String getIntentId;
	String getIntentUser;
	private ProgressDialog pDialog;
	// JSON parser class
	JSONParser jsonParser = new JSONParser();
	//Read User Profile Info Sqlite database
	final DataBaseHandler db = new DataBaseHandler(ProfileMainActivity.this);
	// single product url
	private static final String url_product_detials = "http://www.tamavodka.net23.net/android_connect/get_product_details.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";    
	private static final String TAG_PRODUCT = "product";
	private static final String TAG_ID = "pid";
	private static final String TAG_NAME = "age";
	private static final String TAG_PRICE = "height";
	private static final String TAG_DESCRIPTION = "weight";
	private static final String TAG_SERVICE1 = "oral";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        // http://stackoverflow.com/questions/22466387/how-to-obtain-this-xml-android-layout
        // http://antoine-merle.com/blog/2013/10/04/making-that-google-plus-profile-screen/
        // http://hmkcode.com/android-designing-whatsapp-like-user-profile-screen/
        // http://www.androidhive.info/2014/06/android-facebook-like-custom-listview-feed-using-volley/
        // https://www.template.net/design-templates/android-app-design-templates/
        // http://myapptemplates.com/product/sample-android-chat-app-template-with-parse-backend/
        
        //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8b9dc3")));
		getActionBar().setDisplayHomeAsUpEnabled(true);			
		

		Intent getId = getIntent();
		getIntentId = getId.getStringExtra("id"); // idphone
		getIntentUser = getId.getStringExtra("user");
		getIntentImage = getId.getStringExtra("image");
    	
    	    	
    	imgView = (ImageView) findViewById(R.id.header_imageview);
    	imgView2 = (ImageView) findViewById(R.id.header_imageprofile);
    	imgLoader = new ImageLoader(this);
    	imgLoaderCover = new ImageLoaderCover(this);
    	button = (ImageButton) findViewById(R.id.imageButton1);
    	button.setOnClickListener(this);
    	//buttonfollow = (Button) findViewById(R.id.button1);
    	//buttonfollow.setText("Seguir");
    	
    	buttonEditProfile = (Button) findViewById(R.id.button1);
		buttonEditProfile.setOnClickListener(this);
		if(!getIntentId.equals("900000202")){
    		buttonEditProfile.setVisibility(View.INVISIBLE);
    	}
		buttonPhotos = (Button) findViewById(R.id.button2);
    	buttonPhotos.setOnClickListener(this);
    	buttonAgenda = (Button) findViewById(R.id.button3);
    	buttonAgenda.setOnClickListener(this);
    	
    	userphone = (TextView) findViewById(R.id.profile_phone);
    	username = (TextView) findViewById(R.id.profile_name);
    	
    	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            
    	if (networkInfo != null && networkInfo.isConnected()) {
        // fetch data
        	//new GetProductDetails().execute();            		
    	} else {
        // display error
    		Toast.makeText(getApplicationContext(), "sem acesso a internet", Toast.LENGTH_SHORT).show();		
    	}
    	
    	//String formattedNumber = PhoneNumberUtils.formatNumber(getIntentId);
    	//userphone.setText(getIntentId.substring(0,5)+' '+getIntentId.substring(5,9));
    	String brasilPhoneFormat = getIntentId;
    	brasilPhoneFormat = new StringBuilder(brasilPhoneFormat).insert(5, " ").toString();
    	userphone.setText(brasilPhoneFormat);
    	//username.setText(getIntentUser);
    	username.setText(db.getContact(Integer.parseInt(getIntentId)).getName());
    	    	    	        
    	if(getIntentImage.equals("90000")) {    	
    	}
    	    	
    	if(getIntentImage != null) {    	
        imgLoader.DisplayImage(getIntentImage, imgView2);
        imgLoaderCover.DisplayImage(strURL+getIntentId+"/cover_profile_app.png", imgView);
        }
    	
    	//Toast.makeText(getApplicationContext(),"Me liga "+getIntentId,Toast.LENGTH_SHORT).show();
    	
    	/*buttonEditProfile.setOnClickListener(new OnClickListener(){
    		@Override
        	
    	});*/
    	
    	
    	/** The event listener for the Up navigation selection */
    	
    			// add button listener    			
    			/*
    			button.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View arg0) {    					
    					
    				}
    			});
    			buttonPhotos.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View arg2) {
    					
    				}
    			});
    			buttonAgenda.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View arg3) {
    					
    				}
    			});
				*/
    }
    
    /*
    public void btnLoadImageClick(View v){
    	
    	imgLoader.DisplayImage(strURL, imgView);
    } */

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intentUpdate = new Intent(this,MainActivity.class);                
                startActivity(intentUpdate);
        }
        return true;
    }
    */
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
    
    	
    /**
     * Background Async Task to Get complete product details
     * */
    class GetProductDetails extends AsyncTask<String, String, String> {
    	
    	/**
    	 * Before starting background thread Show Progress Dialog
    	 * */
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		pDialog = new ProgressDialog(ProfileMainActivity.this);
    		pDialog.setMessage("Loading product details. Please wait...");
    		pDialog.setIndeterminate(false);
    		// http: //stackoverflow.com/questions/26846347/cancel-asynctask-with-progress-dialog-button
    		pDialog.setCancelable(true);
    		pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
    		    @Override
    		    public void onClick(DialogInterface dialog, int which) {
    		      new GetProductDetails().cancel(true);
    		      dialog.dismiss();
    		    }
    		  });

    		pDialog.show();
    	}

    	/**
    	 * Getting product details in background thread
    	 * */
    	protected String doInBackground(String... params) {
    			int success;
    		try {				
    			// Building Parameters
    			
    			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
    			
    			//params1.add(new BasicNameValuePair(TAG_ID,getIntentId));				
    			params1.add(new BasicNameValuePair(TAG_ID,getIntentId));
    			
    			// getting product details by making HTTP request
    			// Note that product details url will use GET request
    			JSONObject json = jsonParser.makeHttpRequest(
    					url_product_detials, "GET", params1);

    			// check your log for json response
    			Log.d("Single Product Details", json.toString());
    			
    			// json success tag
    			success = json.getInt(TAG_SUCCESS);
    			if (success == 1) {
    				// successfully received product details
    				JSONArray productObj = json.getJSONArray(TAG_PRODUCT); // JSON Array
    				
    				// get first product object from JSON Array
    				JSONObject product = productObj.getJSONObject(0);
    				
    				userAge = product.getString(TAG_NAME);
    				userHeight = product.getString(TAG_PRICE);
    				userWeight = product.getString(TAG_DESCRIPTION);
    				userOral = product.getString(TAG_SERVICE1);
    				//userPrice = product.getString("price");

    				// product with this pid found
    				// Edit Text
    				//textView01 = (TextView) findViewById(R.id.TextView01);
    				//textView02 = (TextView) findViewById(R.id.TextView04);
    				//textView03 = (TextView) findViewById(R.id.TextView06);

    				// display product data in EditText
    				//textView01.setText(product.getString(TAG_NAME));
    				//textView02.setText(product.getString(TAG_PRICE));
    				//textView03.setText(product.getString(TAG_DESCRIPTION));    				    				    				    				    				
    				    				
    			}else{
    				// product with pid not found
    			}
    				} catch (JSONException e) {
    			    e.printStackTrace();
    				}
    		return null;
    		}
    	


    	
    //}


    	/**
    	 * After completing background task Dismiss the progress dialog
    	 * **/
    	protected void onPostExecute(String file_url) {						
    		
    		// dismiss the dialog once got all details
    		pDialog.dismiss();
    		// updating UI from Background Thread    		
    		
    		runOnUiThread(new Runnable() {
    			public void run() {    				
				/**
    				 * Updating parsed JSON data into Edit Form
    				 * */
    			//Toast.makeText(ProfileMainActivity.this, "AsyncTask", Toast.LENGTH_LONG).show();
    				// Edit Text
    				textView01 = (TextView) findViewById(R.id.TextView01);
    				textView02 = (TextView) findViewById(R.id.TextView02);
    				textView03 = (TextView) findViewById(R.id.TextView03);
    				textView04 = (TextView) findViewById(R.id.TextView04);
    				textView05 = (TextView) findViewById(R.id.TextView05);
    				
    				// display product data in EditText
    				// original code above gives message "app stoped !"
    				//textView01.setText(product.getString(TAG_NAME);
    				//textView02.setText(product.getString(TAG_PRICE));
    				//textView03.setText(product.getString(TAG_DESCRIPTION));
    				    				
					// display product data in EditText
    				textView01.setText(userAge);
    				textView02.setText(userHeight);
    				textView03.setText(userWeight);
    				textView04.setText(userOral);
    				textView05.setText(userOral);
    			}
    		});
    		
    	}
    }
    /**
     * Background Async Task to Get complete product details
     * */


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
			case R.id.imageButton1:
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+getIntentId));
				startActivity(callIntent);
			break;
			case R.id.button1:
				Intent intentEditProfile = new Intent(getBaseContext(),EditProfileActivity.class);
		    	intentEditProfile.putExtra("EditName",getIntentUser);
		    	intentEditProfile.putExtra("EditPhone",getIntentId);
				
				/*
				intentEditProfile.putExtra("textview01", textView01.getText().toString());
				intentEditProfile.putExtra("textview02", textView02.getText().toString());
				intentEditProfile.putExtra("textview03", textView03.getText().toString());
				*/
				startActivity(intentEditProfile);
				/*
				Intent intentEditProfile = new Intent(getBaseContext(),EditProfileActivity.class);
				intentEditProfile.putExtra("id",getIntentId);
				intentEditProfile.putExtra("username",getIntentUser);
				intentEditProfile.putExtra("image",getIntentImage);
				intentEditProfile.putExtra(TAG_NAME, userAge);
				intentEditProfile.putExtra(TAG_PRICE, userHeight);
				intentEditProfile.putExtra(TAG_DESCRIPTION, userWeight);
				startActivity(intentEditProfile);
				*/
			break;
			case R.id.button2:
			// http://stackoverflow.com/questions/5453708/android-how-to-use-environment-getexternalstoragedirectory
			//File directoryID = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/gpApp/"+getIntentId+"/");
			File directoryID = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/gpapp/"+getIntentId+"/photo1.png");
			//File directoryID = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/gpApp");
			
			// http://stackoverflow.com/questions/12780446/check-if-a-path-represents-a-file-or-a-folder
			if(directoryID.exists()){
				//Intent intentGallery = new Intent("gridview.intent.action.LAUNCH");
				Intent intentGridView = new Intent(getBaseContext(), GridViewActivity.class);
				intentGridView.putExtra("id", getIntentId);
				startActivity(intentGridView);
				} else {
					//Intent intentGallery = new Intent("gallery.intent.action.LAUNCH");        						    						
					//startActivity(intentGallery);
					Intent intentGalleryID = new Intent(getBaseContext(), ProfileGalleryActivity.class);
					intentGalleryID.putExtra("id", getIntentId);	    						
					startActivity(intentGalleryID);
				}
			break;
			case R.id.button3:
				Intent agendaIntent = new Intent(getBaseContext(),AllProductsActivity.class);
				agendaIntent.putExtra("pid", getIntentId);
				agendaIntent.putExtra("user", getIntentUser);
				agendaIntent.putExtra("image", getIntentImage);
				startActivity(agendaIntent);
			break;
		}
	}    
    
}