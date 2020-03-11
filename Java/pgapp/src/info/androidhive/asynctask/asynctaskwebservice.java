package info.androidhive.asynctask;

import info.androidhive.imageslider.R;
import info.androidhive.lazyimagedownload.HttpHandler;
import info.androidhive.lazyimagedownload.LazyImageLoadAdapter;
import info.androidhive.sqlite.DBUsers;
import info.androidhive.sqlite.DataBaseHandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class asynctaskwebservice extends AsyncTask<String,Integer,Integer>{

	private Activity _activity;
	private Context context;
	private ProgressDialog pDialog;
	private String TAG = asynctaskwebservice.class.getSimpleName();
	private static final String message = "Loading users from webservice";
	// URL to get contacts JSON
    private static String url = "http://www.tamavodka.net23.net/android_connect/get_all_products.php";
    ArrayList<HashMap<String,String>> productList;
    ArrayList<String> usernameArrayList;
    ArrayList<String> phoneArrayList;
    ArrayList<String> thumbnailImageURLArrayList;
    LazyImageLoadAdapter adapter;
    ListView list;
    
	public asynctaskwebservice(Context cxt){
		context = cxt;
	}
	
	final DataBaseHandler db = new DataBaseHandler(context);
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(message);
	}
	
	@Override
	protected Integer doInBackground(String... arg0) {
		/*
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {		
			e.printStackTrace();
		}
				return null;
		*/
		HttpHandler sh = new HttpHandler();
		 
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

        Log.e(TAG, "Response from url: " + jsonStr);
        
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray products = jsonObj.getJSONArray("products");
                
                // looping through All Contacts
                for (int i = 0; i < products.length(); i++) {
                    JSONObject p = products.getJSONObject(i);

                    //String id = p.getString("id");
                    String username = p.getString("username");
                    String phone = p.getString("phone");
                    String thumbnailImageURL = p.getString("image");

                    // descriptions node is JSON Object
                    JSONObject descriptions = p.getJSONObject("descriptions");
                    String description01 = descriptions.getString("description01");
                    String description02 = descriptions.getString("description02");
                    String description03 = descriptions.getString("description03");

                    usernameArrayList.add(username);
                    phoneArrayList.add(phone);
                    thumbnailImageURLArrayList.add(thumbnailImageURL);
                    // tmp hash map for single contact
                    HashMap<String, String> product = new HashMap<String,String>();

                    // adding each child node to HashMap key => value
                    //product.put("id", id);
                    product.put("username", username);
                    product.put("phone", phone);
                    product.put("thumbnailImageURL", thumbnailImageURL);
                    product.put("description01", description01);
                    product.put("description02", description02);
                    product.put("description03", description03);

                    // adding contact to contact list
                    productList.add(product);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                /*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
                */
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            /*
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
			*/
        }
        return null;
	}
	
	protected void onProgressUpgrade(Integer percentageDone){
		
	}
	
	protected void onPostExecute(Integer arg0){
		CharSequence text = "AsyncTask concluded";
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(context, text, duration).show();
		for(int i=0; i < usernameArrayList.size(); i++){
			/*
			 if(db.getContactsCount() == 0){
			 	db.addUserFromWebService(new DBUsers(usernameArrayList.get(i),phoneArrayList.get(i),thumbnailImageURLArrayList.get(i)));
			 } else {
			 	db.deleteAll();
			 	db.addUserFromWebService(new DBUsers(usernameArrayList.get(i),phoneArrayList.get(i),thumbnailImageURLArrayList.get(i)));
			 }
			 */
			//db.addUserFromWebService(new DBUsers(usernameArrayList.get(i),phoneArrayList.get(i),thumbnailImageURLArrayList.get(i)));
		}
		
		//adapter = new LazyImageLoadAdapter(_activity,  db.getAllUserImageUrl(), db.getAllUsername(), db.getAllUserPhone());
		//adapter.notifyDataSetChanged();
	}

}
