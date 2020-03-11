package info.androidhive.phpmysql;

// ctrl shift O to organize imports

// http: //stackoverflow.com/questions/6804053/understand-the-r-class-in-android
import info.androidhive.imageslider.R;
import info.androidhive.lazyimagedownload.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
//import info.androidhive.imageslider.R;
import android.widget.Toast;

public class AllProductsActivity extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;
	private ImageLoader imgLoader;
	private ImageView imgView;
	private TextView textView;
	private TextView textViewPhone;

	String phone;
	String user;
	String image;
	
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> productsList;

	// url to get all products list
	//private static String url_all_products = "http://192.168.0.109/android_connect/get_all_products.php";
	private static String url_all_products = "http://www.tamavodka.net23.net/android_connect/get_agenda_datetime.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "datetime";
	private static final String TAG_PID = "pid";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_KEY = "key";
	private static final String TAG_USER = "user";
	private static final String TAG_IMAGE = "image";
	private static final String TAG_BUTTON = "button";
	private static final String TAG_START_DATETIME = "start_datetime";
	private static final String TAG_END_DATETIME = "end_datetime";
		
	// products JSONArray
	JSONArray products = null;
	//JSONObject c = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_products);

		getActionBar().setDisplayHomeAsUpEnabled(true);	
		// getting product details from intent
		Intent i = getIntent();
		// getting product id (pid) from intent
		phone = i.getStringExtra(TAG_PID);
		user = i.getStringExtra(TAG_USER);
		image = i.getStringExtra(TAG_IMAGE);
		
		imgView = (ImageView) findViewById(R.id.agenda_imageprofile);
		textView = (TextView) findViewById(R.id.agenda_username);
		textViewPhone = (TextView) findViewById(R.id.agenda_phone);
		imgLoader = new ImageLoader(this);
		if(phone != null) {    	
	        imgLoader.DisplayImage(image, imgView);
	        textView.setText(user);
	        textViewPhone.setText(phone);
	        //imgLoaderCover.DisplayImage(strURL+getIntentId+"/cover_profile_app.png", imgView);
	        }				
		
		// Hashmap for ListView
		productsList = new ArrayList<HashMap<String, String>>();

		ConnectivityManager connMgr = (ConnectivityManager) 
                getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            
            	if (networkInfo != null && networkInfo.isConnected()) {
                // fetch data            		
                	new LoadAllProducts().execute();                	
                	// http: //cyrilmottier.com/2011/11/23/listview-tips-tricks-4-add-several-clickable-areas/
            	} else {
                // display alert no wi-fi or 3G networking internet to acess JSON database
            		Toast.makeText(getApplicationContext(), "sem acesso a internet", Toast.LENGTH_SHORT).show();		
            	}
		
        // https: //sriramramani.wordpress.com/2014/03/13/pseudo-selectors/    first-child last-child
		// Loading products in Background Thread
		//new LoadAllProducts().execute();

		// Get listview
		ListView lv = getListView();

		// on seleting single product
		// launching Edit Product Screen
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();				
				//String datetime = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String start_datetime = ((TextView) view.findViewById(R.id.start_datetime)).getText().toString();
				String end_datetime = ((TextView) view.findViewById(R.id.end_datetime)).getText().toString();
				Toast.makeText(getApplicationContext(), "datetime : ["+start_datetime+" - "+end_datetime+"] phone : ["+phone+"]", Toast.LENGTH_LONG).show();
			}
			
		});
		

	}

	// Response from Edit Product Activity
	/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			// if result code 100 is received 
			// means user edited/deleted product
			// reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

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
	
	// https: //sriramramani.wordpress.com/2014/03/13/pseudo-selectors/
	
	class PseudoAwareListView extends ListView {
	     
	    final int[] STATE_ONLY_ONE = new int[] {
	        android.R.attr.state_first,
	        android.R.attr.state_last,
	    };
	     
	    final int[] STATE_FIRST = new int[] {
	        android.R.attr.state_first
	    };
	     
	    final int[] STATE_LAST = new int[] {
	        android.R.attr.state_last
	    };
	     
	    private int mTouchX, mTouchY;
	 
	    public PseudoAwareListView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }
	     
	    @Override
	    public boolean onInterceptTouchEvent(MotionEvent event) {
	        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
	            mTouchX = (int) event.getX();
	            mTouchY = (int) event.getY();
	        } else {
	            mTouchX = -1;
	            mTouchY = -1;
	        }
	         
	        return super.onInterceptTouchEvent(event);
	    }
	     
	    @Override
	    protected int[] onCreateDrawableState(int extraSpace) {
	        if (!isPressed()) {
	            return super.onCreateDrawableState(extraSpace);
	        }
	         
	        final int selection = pointToPosition(mTouchX, mTouchY);
	        final int size = getChildCount();
	        final boolean isFirst = (0 == selection);
	        final boolean isLast = (size-1 == selection);
	         
	        int[] states = super.onCreateDrawableState(extraSpace + 2);
	        if (isFirst && isLast) {
	            mergeDrawableStates(states, STATE_ONLY_ONE);
	        } else if (isFirst) {
	            mergeDrawableStates(states, STATE_FIRST);
	        } else if (isLast) {
	            mergeDrawableStates(states, STATE_LAST);
	        }
	         
	        return states;
	    }
	 
	}

	
	
	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AllProductsActivity.this);
			pDialog.setMessage("Carregando agenda, por favor aguarde ...");
			//http: //stackoverflow.com/questions/9531292/what-does-the-indeterminate-mean-in-progressdialog   answer : It means the "loading amount" is not measured.
			pDialog.setIndeterminate(false);
			// http: //stackoverflow.com/questions/26846347/cancel-asynctask-with-progress-dialog-button
			pDialog.setCancelable(true);			
			pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
    		    @Override
    		    public void onClick(DialogInterface dialog, int which) {
    		      new LoadAllProducts().cancel(true);
    		      dialog.dismiss();
    		    }
    		  });
						
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_PHONE,phone));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray(TAG_PRODUCTS);									
					
					//Toast.makeText(getApplicationContext(), products.length(), Toast.LENGTH_SHORT).show();
													
					// creating new HashMap
					//HashMap<String, String> map = new HashMap<String, String>();
					// http: //stackoverflow.com/questions/7755806/android-hashmap-shows-only-last-item
					
					// looping through All Products
					for (int i = 0; i < products.length(); i++) {					
						JSONObject c = products.getJSONObject(i);
						//c = products.getJSONObject(i);
						
						// Storing each json item in variable
						//String id = c.getString(TAG_PID);
						String value1 = c.getString(TAG_KEY);
						String value2 = c.getString(TAG_BUTTON);
						String value3 = c.getString(TAG_START_DATETIME);
						String value4 = c.getString(TAG_END_DATETIME);
						//String value = c.getString(TAG_KEY);											
						//  http: //stackoverflow.com/questions/15836797/android-how-to-convert-int-to-string
						//String value2 = String.valueOf( products.length() );
												
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						// http: //stackoverflow.com/questions/10664906/android-listview-displays-last-element-in-the-arraylist					
						
						// adding each child node to HashMap key => value
						//map.put(TAG_PID, id);						
						map.put(TAG_KEY, value1);
						map.put(TAG_BUTTON, value2);
						map.put(TAG_START_DATETIME, value3);
						map.put(TAG_END_DATETIME, value4);
						// adding HashList to ArrayList
						productsList.add(map);
					}
				} else {
					// no products found
					// Launch Add New product Activity
					//Intent i = new Intent(getApplicationContext(),NewProductActivity.class);
					// Closing all previous activities
					//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					//startActivity(i);
					Toast.makeText(getApplicationContext(), "not json", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */									
					ListAdapter adapter = new SimpleAdapter(
							AllProductsActivity.this, productsList,
							R.layout.list_item, new String[]{ TAG_KEY, TAG_BUTTON, TAG_START_DATETIME, TAG_END_DATETIME },new int[]{ R.id.name, R.id.agendar, R.id.start_datetime, R.id.end_datetime } );								
					// updating listview
					setListAdapter(adapter);
					//http://stackoverflow.com/questions/14581103/use-base-adapter-with-asynctask-to-display-listview
					//http://stackoverflow.com/questions/14581103/use-base-adapter-with-asynctask-to-display-listview
					//http://pt.stackoverflow.com/questions/38853/o-que-%C3%A9-um-context-no-android/39070
					
				}
			});

		}

	}
}