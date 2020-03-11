package info.androidhive.profile;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.imageslider.R;
import info.androidhive.profile.JSONParser;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


public class EditProfileAsyncTask extends AsyncTask<String, String, String> {

	private Context context;
	private ProgressDialog pDialog;
	private static final String TAG_USERNAME = "username";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_SUCCESS = "success";
	private static final String url_update_product = "http://www.tamavodka.net23.net/android_connect/url_update_product.php";
	private static final String method_post = "POST";
	EditText editText01;
	EditText editText02;
	String updateEditUsername;
	String updateEditUserPhone;
	JSONObject jsonUpdate = new JSONObject();
	JSONParser jsonParser = new JSONParser(); 
	
	// http:// stackoverflow.com/questions/3347247/external-asynctask-class-with-progressdialog-update-and-returning-back
	public EditProfileAsyncTask(Context cxt){
		this.context = cxt;
		//pDialog = new ProgressDialog(this.context);
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Saving edited profile. Please wait...");
		pDialog.setIndeterminate(false);
		// http: //stackoverflow.com/questions/26846347/cancel-asynctask-with-progress-dialog-button
		pDialog.setCancelable(true);
		pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		      new EditProfileAsyncTask(context).cancel(true);
		      dialog.dismiss();
		    }
		  });
		/*  http: //stackoverflow.com/questions/2735102/ideal-way-to-cancel-an-executing-asynctask
		pDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // actually could set running = false; right here, but I'll
                // stick to contract.
                cancel(true);
            }
        });
		*/
		pDialog.show();
	}
	
	@Override
	protected String doInBackground(String... args) {
		// http://stackoverflow.com/questions/32464338/obtaining-the-value-of-an-edittext-inside-asynctask 
		//editText01 = args[0];
		//editText02 = args[1];
		
		//updateEditUsername = editText01.getText().toString();
		//updateEditUserPhone = editText02.getText().toString();
		updateEditUsername = args[0];
		updateEditUserPhone = args[1];
		//updateEditUsername = "user2";
		//updateEditUserPhone = "900000202";
		
		Log.d("username",args[0]);
		Log.d("phone",args[1]);
		Log.d("updateEditUsername", updateEditUsername);
		Log.d("updateEditPhone", updateEditUserPhone);
		//CharSequence text = "Hello toast!";
		//Toast.makeText(context, updateEditUsername + " - " +updateEditUserPhone, Toast.LENGTH_SHORT).show();
		
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair(TAG_USERNAME, updateEditUsername));
		//params.add(new BasicNameValuePair(TAG_PHONE, updateEditUserPhone));
		params.add(new BasicNameValuePair(TAG_USERNAME, args[0]));
		params.add(new BasicNameValuePair(TAG_PHONE, args[1]));
		jsonUpdate  = jsonParser.makeHttpRequest(url_update_product, method_post, params);
		Log.d("POST: jsonUpdate JSONObject", jsonUpdate.toString());

		try {
			int success = jsonUpdate.getInt(TAG_SUCCESS);
			if(success == 1){
				Log.d("success","Json : "+success);
				// update sqlite here
				//Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
			} else {
				Log.d("error","Json : "+success);
				//Toast.makeText(context, "not updated", Toast.LENGTH_SHORT).show();
			}
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String file_url) {
		pDialog.dismiss();

	}
}
