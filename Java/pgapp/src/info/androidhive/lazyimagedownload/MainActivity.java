package info.androidhive.lazyimagedownload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import info.androidhive.imageslider.R;
import info.androidhive.asynctask.*;
import info.androidhive.profile.EditProfileAsyncTask;
import info.androidhive.profile.ProfileMainActivity;
import info.androidhive.sqlite.DataBaseHandler;
import info.androidhive.sqlite.DBUsers;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
 
@SuppressLint("CommitPrefEdits")
public class MainActivity extends Activity {
         
	ListView list;
    LazyImageLoadAdapter adapter;
    AsyncTaskLazyImageLoadAdapter adapter2;
    public ProgressDialog dialog;
    // instance variable descrString,phoneString,imgString as global variable class will be used in onOptionsItemSelected R.id.button_refresh for list deferNotifyDataChanged 
    public ArrayList<String> descrString,phoneString,imgString;
    private String TAG = MainActivity.class.getSimpleName();
    
    static final int REFRESH = 1;
    public static final String PREFS_NAME = "UserArrayList";
    public static final String PREFS_DESCR = "DescriptionArrayList";
    public static final String PREFS_USER_FROM_PHP = "UserFromPHP";
    public static final String PREFS_PHONE_FROM_PHP = "PhoneFromPHP";
    public static final String PREFS_USERNAME_FROM_PHP = "UsernameFromPHP";

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);              
                
        //Parse.initialize(this, "37WGaCBDsU9jc3nRLA4Dv7W1McU0Ck2eOoOhlWMm", "xTiYcsKAi39VAu1tXlS7RwZI1Hqz2xZuDb2i19wU");
    	// Specify an Activity to handle all pushes by default.
    	//PushService.setDefaultPushCallback(this, MainActivity.class);                                       
        
    	list=(ListView)findViewById(R.id.list);
    	
    	ArrayList<String> imgString = new ArrayList<String>();
    	
    	/*
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image0.png"); //this adds an element to the list.
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image1.png");    			
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image2.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image3.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image4.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image5.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image6.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image7.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image8.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image9.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image0.png"); //this adds an element to the list.
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image1.png");    			
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image2.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image3.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image4.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image5.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image6.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image7.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image8.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image9.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image0.png"); //this adds an element to the list.
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image1.png");    			
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image2.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image3.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image4.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image5.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image6.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image7.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image8.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image9.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image0.png"); //this adds an element to the list.
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image1.png");    			
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image2.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image3.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image4.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image5.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image6.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image7.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image8.png");
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image9.png");    	
    	for(int i=0;i<count;i++){
    		if(count<100){imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image"+i+".png");}
    		else {imgString.add(null);}
    		
    	} 
    	*/
    	//imgString.add("http://www.tamavodka.net23.net/images/900000000/profile_thumbnail_app.png");
    	//    	
    	//String[] img = imgPHP.getString("UserFromPHP", "blank").split(",");    	    	
    	SharedPreferences imgPHP = getSharedPreferences(PREFS_USER_FROM_PHP, 0);
    	//String toast = imgPHP.getString("UserFromPHP4", "blank");
    	int size = imgPHP.getInt("imgStringLength", 0);
    	//String toast = String.valueOf(size);
    	//Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    	for(int h=0; h<size; h++){    	    		
    		//imgString.clear();    		
    		//imgString.add(imgPHP.getString("UsrFromPHP"+l, "http://"+l));    		
    		imgString.add(imgPHP.getString("UserFromPHP"+h, "blank http"));
    	}    	
    	
    	ArrayList<String> descrString = new ArrayList<String>();
    	/*
    	descrString.add("user1");
    	descrString.add("user2");
    	descrString.add("user3");
    	descrString.add("user4");
    	descrString.add("user5");
    	descrString.add("user6");
    	descrString.add("user7");
    	descrString.add("user8");
    	descrString.add("user9");
    	descrString.add("user10");
    	descrString.add("user11");
    	
    	descrString.add("user12");
    	descrString.add("user13");
    	descrString.add("user14");
    	descrString.add("user15");
    	descrString.add("user16");
    	descrString.add("user17");
    	descrString.add("user18");
    	descrString.add("user19");
    	descrString.add("user20");
    	descrString.add("user21");
    	descrString.add("user22");
    	descrString.add("user23");
    	descrString.add("user24");
    	descrString.add("user25");
    	descrString.add("user26");
    	descrString.add("user27");
    	descrString.add("user28");
    	descrString.add("user29");
    	descrString.add("user30");
    	descrString.add("user31");
    	descrString.add("user32");
    	descrString.add("user33");
    	descrString.add("user34");
    	descrString.add("user35");
    	descrString.add("user36");
    	descrString.add("user37");
    	descrString.add("user38");
    	descrString.add("user39");
    	descrString.add("user40");    	
    	*/
    	//descrString.add("first user");
    	SharedPreferences usernamePHP = getSharedPreferences(PREFS_USERNAME_FROM_PHP, 0);
    	//String toast = imgPHP.getString("UserFromPHP4", "blank");
    	int size2 = usernamePHP.getInt("usernameStringLength", 0);
    	//String toast = String.valueOf(size);
    	//Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    	for(int h=0; h<size2; h++){    	    		
    		//imgString.clear();    		
    		//imgString.add(usernamePHP.getString("UsrFromPHP"+l, "http://"+l));    		
    		descrString.add(usernamePHP.getString("UsernameFromPHP"+h, "blank http"));
    	}
    	    	
    	ArrayList<String> phoneString = new ArrayList<String>();
    	/*
    	phoneString.add("90000 0101");
    	phoneString.add("90000 0202");
    	phoneString.add("90000 0303");
    	phoneString.add("90000 0404");
    	phoneString.add("90000 0505");
    	phoneString.add("90000 0606");
    	phoneString.add("90000 0707");
    	phoneString.add("90000 0808");
    	phoneString.add("90000 0909");
    	phoneString.add("90000 1010");
    	phoneString.add("90000 1111");
    	
    	phoneString.add("90000 1212");
    	phoneString.add("90000 1313");
    	phoneString.add("90000 1414");
    	phoneString.add("90000 1515");
    	phoneString.add("90000 1616");
    	phoneString.add("90000 1717");
    	phoneString.add("90000 1818");
    	phoneString.add("90000 1919");
    	phoneString.add("90000 2020");
    	phoneString.add("90000 2121");
    	phoneString.add("90000 2222");
    	phoneString.add("90000 2323");
    	phoneString.add("90000 2424");
    	phoneString.add("90000 2525");
    	phoneString.add("90000 26226");
    	phoneString.add("90000 2727");
    	phoneString.add("90000 2828");
    	phoneString.add("90000 2929");
    	phoneString.add("90000 3030");
    	phoneString.add("90000 3131");
    	phoneString.add("90000 3232");
    	phoneString.add("90000 3333");
    	phoneString.add("90000 3434");
    	phoneString.add("90000 3535");
    	phoneString.add("90000 3636");
    	phoneString.add("90000 3737");
    	phoneString.add("90000 3838");
    	phoneString.add("90000 3939");
    	phoneString.add("90000 4040");
    	*/
    	//phoneString.add("900000000");
    	SharedPreferences phonePHP = getSharedPreferences(PREFS_PHONE_FROM_PHP, 0);
    	//String toast = imgPHP.getString("UserFromPHP4", "blank");
    	int size3 = phonePHP.getInt("phoneStringLength", 0);
    	//String toast = String.valueOf(size);
    	//Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    	for(int h=0; h<size3; h++){    	    		
    		//imgString.clear();    		
    		//imgString.add(imgPHP.getString("UsrFromPHP"+l, "http://"+l));
    		phoneString.add(phonePHP.getString("PhoneFromPHP"+h, "blank http"));
    	}
    	
    	/*
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        //editor.putString("phone", TextUtils.join(",", phoneString));
        
        editor.putInt("Status_size",phoneString.size());    
          
            for(int i=0;i<phoneString.size();i++) {  
                editor.remove("Status_" + i);  
                editor.putString("Status_" + i, phoneString.get(i));    
            }
                          
        // Commit the edits!
        editor.commit();
        */
        
    	/*
        SharedPreferences settingsDescr = getSharedPreferences(PREFS_DESCR, 0);
        SharedPreferences.Editor editorDescr = settingsDescr.edit();
        //editor.putString("phone", TextUtils.join(",", phoneString));
           
        editorDescr.putInt("Description_size",descrString.size());   
             
            for(int i=0;i<descrString.size();i++) {  
                editorDescr.remove("Description_" + i);  
                editorDescr.putString("Description_" + i, descrString.get(i));    
           }
            
        editorDescr.commit();
		*/
    	
    	//DataBaseHandler db = new DataBaseHandler(this);
    	
    	//adapter=new LazyImageLoadAdapter(this,  db.getAllUserImageUrl(), db.getAllUsername(), db.getAllUserPhone());
    	
    	// Create custom adapter for listview
    	
        adapter=new LazyImageLoadAdapter(this, imgString, descrString, phoneString);        
         
        //list.deferNotifyDataSetChanged();
        //Set adapter to listview
        
        list.setAdapter(adapter);
        
        Button b=(Button)findViewById(R.id.button1);
        b.setOnClickListener(listener);               
        
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);               
        //String defaultValue = sharedPref.getString("pref", "Welcome");
        //Toast.makeText(getApplicationContext(), defaultValue, Toast.LENGTH_LONG).show();
        
        
        //start code                
        
        /* This block is for getting the image url to download from the server   
        final GetDataFromDB getvalues = new GetDataFromDB();
        final GetPhoneFromDB getphone = new GetPhoneFromDB();
        final GetUserFromDB getusername = new GetUserFromDB();
        
        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this,
                "Verificando atualização", "Carregando usuários ...", true);
        new    Thread   (new Runnable() {
            public void run() {
                String response = getvalues.getImageUrlFromDB();
                String response2 = getphone.getPhoneFromDB();
                String response3 = getusername.getUserFromDB();
                System.out.println("Response : " + response);
                System.out.println("Response : " + response2);
                System.out.println("Response : " + response3);
                 
                dismissDialog(dialog);
                if (!response.equalsIgnoreCase("")) {
                    if (!response.equalsIgnoreCase("error")) {
                        dismissDialog(dialog);
                         
                        // Got the response, now split it to get the image Urls from GetDataFromDB
                        //String imgStrings = response;                        
                        String[] imgStringsPHP = response.split(",");
                        //{test[0] = response; test[1] = response; test[2] = response;}						
                        //List<String> your_array_list = new ArrayList<String>();
                        SharedPreferences imgPHP = getSharedPreferences(PREFS_USER_FROM_PHP, 0);
                        SharedPreferences.Editor editorImgPHP = imgPHP.edit();                        
                        editorImgPHP.putInt("imgStringLength", imgStringsPHP.length);
                        editorImgPHP.remove("UserFromPHP");
                        for(int j=0; j<imgStringsPHP.length; j++){
                        	editorImgPHP.putString("UserFromPHP"+j,imgStringsPHP[j]);                        				                                                
                            editorImgPHP.commit();
                        }
                        
                        String[] phoneStringsPHP = response2.split(",");
                        //{test[0] = response; test[1] = response; test[2] = response;}						
                        //List<String> your_array_list = new ArrayList<String>();
                        SharedPreferences phonePHP = getSharedPreferences(PREFS_PHONE_FROM_PHP, 0);
                        SharedPreferences.Editor editorPhonePHP = phonePHP.edit();                        
                        editorPhonePHP.putInt("phoneStringLength", phoneStringsPHP.length);
                        editorPhonePHP.remove("PhoneFromPHP");
                        for(int j=0; j<phoneStringsPHP.length; j++){
                        	editorPhonePHP.putString("PhoneFromPHP"+j,phoneStringsPHP[j]);                        				                                                
                            editorPhonePHP.commit();
                        }
                        
                        String[] usernameStringsPHP = response3.split(",");
                        //{test[0] = response; test[1] = response; test[2] = response;}						
                        //List<String> your_array_list = new ArrayList<String>();
                        SharedPreferences usernamePHP = getSharedPreferences(PREFS_USERNAME_FROM_PHP, 0);
                        SharedPreferences.Editor editorUsernamePHP = usernamePHP.edit();                        
                        editorUsernamePHP.putInt("usernameStringLength", usernameStringsPHP.length);
                        editorUsernamePHP.remove("UsernameFromPHP");
                        for(int j=0; j<usernameStringsPHP.length; j++){
                        	editorUsernamePHP.putString("UsernameFromPHP"+j,usernameStringsPHP[j]);                        				                                                
                            editorUsernamePHP.commit();
                        }
                                                                        
                    }
                     
                } else {
                    dismissDialog(dialog);
                }
            }
        }).start();
        
      */ 
      //end code        
	
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	/*
    	DataBaseHandler db = new DataBaseHandler(MainActivity.this);
    	adapter = new LazyImageLoadAdapter(MainActivity.this,  db.getAllUserImageUrl(), db.getAllUsername(), db.getAllUserPhone());
    	adapter.notifyDataSetChanged();
    	list.setAdapter(adapter);
    	System.out.println("onResume for this MainActivity");
    	Log.d("TAG_RESUME", "onResume for this MainActivity");
    	*/
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	/*
    	DataBaseHandler db = new DataBaseHandler(MainActivity.this);
    	adapter = new LazyImageLoadAdapter(MainActivity.this,  db.getAllUserImageUrl(), db.getAllUsername(), db.getAllUserPhone());
    	adapter.notifyDataSetChanged();
    	list.setAdapter(adapter);
    	System.out.println("onPause for this MainActivity");
    	Log.d("TAG_PAUSE", "onPause for this MainActivity");
    	Toast.makeText(getApplication(), "MainActivity enter in onPause state", Toast.LENGTH_SHORT).show();
    	*/
    }

 // Class with extends AsyncTask class
    /*
    public class LongOperation  extends AsyncTask<String, Void, Void> {
         
        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
         
     
         
        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
             
            //UI Element
     
            Dialog.setMessage("verificando acompanhantes..");
            Dialog.show();
        }
 
        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {
                 
                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.
                 
                // Server url call by GET method
                HttpGet httpget = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Content = Client.execute(httpget, responseHandler);
                
                final GetDataFromDB getvalues = new GetDataFromDB();
                final GetPhoneFromDB getphone = new GetPhoneFromDB();
                final GetUserFromDB getusername = new GetUserFromDB();
                
                String response = getvalues.getImageUrlFromDB();
                String response2 = getphone.getPhoneFromDB();
                String response3 = getusername.getUserFromDB();
                System.out.println("Response : " + response);
                System.out.println("Response : " + response2);
                System.out.println("Response : " + response3);
                
             // Got the response, now split it to get the image Urls from GetDataFromDB
                //String imgStrings = response;                        
                String[] imgStringsPHP = response.split(",");
                //{test[0] = response; test[1] = response; test[2] = response;}						
                //List<String> your_array_list = new ArrayList<String>();
                SharedPreferences imgPHP = getSharedPreferences(PREFS_USER_FROM_PHP, 0);
                SharedPreferences.Editor editorImgPHP = imgPHP.edit();                        
                editorImgPHP.putInt("imgStringLength", imgStringsPHP.length);
                editorImgPHP.remove("UserFromPHP");
                for(int j=0; j<imgStringsPHP.length; j++){
                	editorImgPHP.putString("UserFromPHP"+j,imgStringsPHP[j]);                        				                                                
                    editorImgPHP.commit();
                }
                
                String[] phoneStringsPHP = response2.split(",");
                //{test[0] = response; test[1] = response; test[2] = response;}						
                //List<String> your_array_list = new ArrayList<String>();
                SharedPreferences phonePHP = getSharedPreferences(PREFS_PHONE_FROM_PHP, 0);
                SharedPreferences.Editor editorPhonePHP = phonePHP.edit();                        
                editorPhonePHP.putInt("phoneStringLength", phoneStringsPHP.length);
                editorPhonePHP.remove("PhoneFromPHP");
                for(int j=0; j<phoneStringsPHP.length; j++){
                	editorPhonePHP.putString("PhoneFromPHP"+j,phoneStringsPHP[j]);                        				                                                
                    editorPhonePHP.commit();
                }
                
                String[] usernameStringsPHP = response3.split(",");
                //{test[0] = response; test[1] = response; test[2] = response;}						
                //List<String> your_array_list = new ArrayList<String>();
                SharedPreferences usernamePHP = getSharedPreferences(PREFS_USERNAME_FROM_PHP, 0);
                SharedPreferences.Editor editorUsernamePHP = usernamePHP.edit();                        
                editorUsernamePHP.putInt("usernameStringLength", usernameStringsPHP.length);
                editorUsernamePHP.remove("UsernameFromPHP");
                for(int j=0; j<usernameStringsPHP.length; j++){
                	editorUsernamePHP.putString("UsernameFromPHP"+j,usernameStringsPHP[j]);                        				                                                
                    editorUsernamePHP.commit();
                }
                
                //SharedPreferences imgPHP = getSharedPreferences(PREFS_USER_FROM_PHP, 0);
				ArrayList<String> imgString = new ArrayList<String>();   			    	
		    	int size = imgPHP.getInt("imgStringLength", 0);
		    	for(int h=0; h<size; h++){    	    		
		    		imgString.add(imgPHP.getString("UserFromPHP"+h, "blank http"));
		    	}    	
		    	
		    	//SharedPreferences usernamePHP = getSharedPreferences(PREFS_USERNAME_FROM_PHP, 0);    	
		    	ArrayList<String> descrString = new ArrayList<String>();
		    	int size2 = usernamePHP.getInt("usernameStringLength", 0);
		    	for(int h=0; h<size2; h++){    	    				
		    		descrString.add(usernamePHP.getString("UsernameFromPHP"+h, "blank http"));
		    	}
		    	
		    	//SharedPreferences phonePHP = getSharedPreferences(PREFS_PHONE_FROM_PHP, 0);
		    	ArrayList<String> phoneString = new ArrayList<String>();		    	
		    	int size3 = phonePHP.getInt("phoneStringLength", 0);
		    	for(int h=0; h<size3; h++){    	    		
		    		phoneString.add(phonePHP.getString("PhoneFromPHP"+h, "blank http"));
		    	}

                 
            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }
             
            return null;
        }
         
        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
             
            // Close progress dialog
            Dialog.dismiss();
             
            if (Error != null) {
                 
            	Toast.makeText(getApplicationContext(), Error, Toast.LENGTH_SHORT).show();
                 
            } else {
                 
            	Toast.makeText(getApplicationContext(), Content, Toast.LENGTH_SHORT).show();
            	
            	
            	runOnUiThread(new Runnable() {
    				public void run() {
    				// updating Ui should goes here
    					    			// Create custom adapter for listview
                //adapter2=  new AsyncTaskLazyImageLoadAdapter(MainActivity.this, imgString, descrString, phoneString);
                    					
    				}
    			});
    			

             }
        }
         
    }
    */
    
    /*
    @Override
    public void onPause()
    {
    	super.onPause();
    	list=(ListView)findViewById(R.id.list);
    	
    	ArrayList<String> imgString = new ArrayList<String>();    	
    	imgString.add("http://androidexample.com/media/webservice/LazyListView_images/image0.png");    	    	    	    	    	
    	SharedPreferences imgPHP = getSharedPreferences(PREFS_USER_FROM_PHP, 0);    	
    	int size = imgPHP.getInt("imgStringLength", 0);    	
    	for(int h=0; h<size; h++){    	    		    		    		
    		imgString.add(imgPHP.getString("UserFromPHP"+h, "blank http"));
    	}    	
    	
    	ArrayList<String> descrString = new ArrayList<String>();
    	
    	descrString.add("first user");
    	SharedPreferences usernamePHP = getSharedPreferences(PREFS_USERNAME_FROM_PHP, 0);
    	
    	int size2 = usernamePHP.getInt("usernameStringLength", 0);
    	
    	for(int h=0; h<size2; h++){    	    		
    		  		
    		descrString.add(usernamePHP.getString("UsernameFromPHP"+h, "blank http"));
    	}
    	    	
    	ArrayList<String> phoneString = new ArrayList<String>();
    	
    	phoneString.add("900000000");
    	SharedPreferences phonePHP = getSharedPreferences(PREFS_PHONE_FROM_PHP, 0);
    	//String toast = imgPHP.getString("UserFromPHP4", "blank");
    	int size3 = phonePHP.getInt("phoneStringLength", 0);
    	
    	for(int h=0; h<size3; h++){    	    		
    		
    		phoneString.add(phonePHP.getString("PhoneFromPHP"+h, "blank http"));
    	}
    	
    	// Create custom adapter for listview
        adapter=new LazyImageLoadAdapter(this, imgString, descrString, phoneString);        
         
        list.deferNotifyDataSetChanged();
        //Set adapter to listview
        list.setAdapter(adapter);
    	  	           
    }        
    */
    
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {            	
            	Toast.makeText(getApplicationContext(), "Intent recebida", Toast.LENGTH_SHORT).show();
            }
        }
    }
    */
    
    public void dismissDialog(final ProgressDialog dialog){
        runOnUiThread(new Runnable() {
            public void run() {
                dialog.dismiss();            
            }
        });
    }
    		
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	// Remove adapter refference from list
        list.setAdapter(null);        
    }
    
    
    
    public OnClickListener listener=new OnClickListener(){
        @Override
        public void onClick(View arg0) {
            
        	Toast.makeText(getApplication(), "desenvolvimento por Hamilton Kamiya (desenvolvedor web mobile pleno), sistema de gestão de pedidos para pizzarias microempresa", Toast.LENGTH_SHORT).show();
            //Refresh cache directory downloaded images
            adapter.imageLoader.clearCache();        	
            adapter.notifyDataSetChanged();
            
            File delFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/gpApp");            
            File directoryID0 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/gpapp/900000000");
            File directoryID1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/gpapp/900000101");
            File directoryID2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/gpapp/900000202");
            File dirPublic = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/gpApp");
            deleteDirectory(delFile);
            deleteDirectory(directoryID0);
            deleteDirectory(directoryID1);
            deleteDirectory(directoryID2);
            deleteDirectory(dirPublic);            
        	
        }
    };	
     
    //http://stackoverflow.com/questions/1248292/how-to-delete-a-file-from-sd-card
    public static boolean deleteDirectory(File path) {
        // TODO Auto-generated method stub
        if( path.exists() ) {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
     }
    
    public void onItemClick(int mPosition)
    {        		

    	//String idphone = imgStrings[mPosition];
    	/*
		ArrayList<String> phoneString = new ArrayList<String>();		
		SharedPreferences settingsPhone = getSharedPreferences(PREFS_NAME, 0);
		descrString.clear();  
		    int sizePhone = settingsPhone.getInt("Status_size", 0);    
		  
		    for(int i=0;i<sizePhone;i++) {  
		        phoneString.add(settingsPhone.getString("Status_" + i, null));    
		  
		    }
		*/
    	//Toast.makeText(getApplicationContext(), String.valueOf(mPosition), Toast.LENGTH_SHORT).show();
    	ArrayList<String> imgString = new ArrayList<String>();
    	
    	//imgString.add("http://www.tamavodka.net23.net/images/900000000/profile_thumbnail_app.png");
    	SharedPreferences imagePHP = getSharedPreferences(PREFS_USER_FROM_PHP, 0);
    	//String toast = imgPHP.getString("UserFromPHP4", "blank");
    	int size1 = imagePHP.getInt("imgStringLength", 0);
    	//String toast = String.valueOf(size);
    	//Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    	for(int h=0; h<size1; h++){    	    		
    		//imgString.clear();    		
    		//imgString.add(imgPHP.getString("UsrFromPHP"+l, "http://"+l));
    		imgString.add(imagePHP.getString("UserFromPHP"+h, "blank http"));
    	}
				
		String idimage = imgString.get(mPosition);
    	
    	ArrayList<String> phoneString = new ArrayList<String>();
    	
    	//phoneString.add("900000000");
    	SharedPreferences phonePHP = getSharedPreferences(PREFS_PHONE_FROM_PHP, 0);
    	//String toast = imgPHP.getString("UserFromPHP4", "blank");
    	int size3 = phonePHP.getInt("phoneStringLength", 0);
    	//String toast = String.valueOf(size);
    	//Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    	for(int h=0; h<size3; h++){    	    		
    		//imgString.clear();    		
    		//imgString.add(imgPHP.getString("UsrFromPHP"+l, "http://"+l));
    		phoneString.add(phonePHP.getString("PhoneFromPHP"+h, "blank http"));
    	}
				
		String idphone = phoneString.get(mPosition);
		//Toast.makeText(getApplicationContext(), idphone, Toast.LENGTH_SHORT).show();
		/*
		ArrayList<String> descrString = new ArrayList<String>();		
		SharedPreferences settingsDescr = getSharedPreferences(PREFS_DESCR, 0);
		descrString.clear();  
		    int sizeDescr = settingsDescr.getInt("Description_size", 0);    
		  
		    for(int i=0;i<sizeDescr;i++) {  
		        descrString.add(settingsDescr.getString("Description_" + i, null));    
		  
		    }
		*/
		ArrayList<String> descrString = new ArrayList<String>();
    	//descrString.add("first user");
    	SharedPreferences usernamePHP = getSharedPreferences(PREFS_USERNAME_FROM_PHP, 0);
    	//String toast = imgPHP.getString("UserFromPHP4", "blank");
    	int size2 = usernamePHP.getInt("usernameStringLength", 0);
    	//String toast = String.valueOf(size);
    	//Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    	for(int h=0; h<size2; h++){    	    		
    		//imgString.clear();    		
    		//imgString.add(usernamePHP.getString("UsrFromPHP"+l, "http://"+l));    		
    		descrString.add(usernamePHP.getString("UsernameFromPHP"+h, "blank http"));
    	}
		String iddescription = descrString.get(mPosition);
		System.out.println(" descrString.get(mPosition) is equal to : " + descrString.get(mPosition));
    	//DataBaseHandler db = new DataBaseHandler(MainActivity.this);
    	//String iddescription = db.getAllUserImageUrl().get(mPosition);
		
		int count = 0;		
		//SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		        int defaultValue = getPreferences(MODE_PRIVATE).getInt("count_key",count);
		             ++defaultValue;
		            getPreferences(MODE_PRIVATE).edit().putInt("count_key",defaultValue).commit();
		            count = getPreferences(MODE_PRIVATE).getInt("count_key",count);
		            System.out.println("The count value is " + count);
		        
		        if(count == 1)
		        {
		            count = 1;
		            getPreferences(MODE_PRIVATE).edit().putInt("count_key",count).commit();

		        }
		        else
		        {		             
		            getPreferences(MODE_PRIVATE).edit().putInt("count_key",count).commit();
		            count = getPreferences(MODE_PRIVATE).getInt("count_key",count);
		            System.out.println("The count value is " + count);
		        }
		            
    	            			
    	//Convert the long id to a string pid, to be send for puExtra
    	//int pid = (int)id;	    	
    	//String idphone = phoneString.get(mPosition);
    	//Toast.makeText(MainActivity.this,"phone : "+idphone+" "+iddescription+" contador "+count,Toast.LENGTH_LONG).show();
    	//String idphone.setText(Integer.toString(phoneString.get(mPosition)));
    	
    	//Intent i = new Intent("profile.intent.action.LAUNCH");
		Intent i = new Intent(getApplicationContext(), ProfileMainActivity.class);
    	i.putExtra("image", idimage);
    	i.putExtra("id", idphone);
		i.putExtra("user", iddescription);
		startActivity(i);
    	
    	//SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPref.edit();        
        //editor.putString("pref", phoneStrings[mPosition]);
        //editor.commit();
    	
    }
     

    // Image urls used in LazyImageLoadAdapter.java file
       
	/*
    private String[] mStrings={
            "http://androidexample.com/media/webservice/LazyListView_images/image0.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image1.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image2.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image3.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image4.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image5.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image6.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image7.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image8.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image9.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image10.png"
            
             
    };
    
    
    private String[] wimgStrings = {
    		"http://www.feibook.com.br/profilethumbnail/profilejulianasaad.png",
            "",
            "http://androidexample.com/media/webservice/LazyListView_images/image2.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image3.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image4.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image5.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image6.png"
    };       
    
    
    
    private String[] descrStrings = {
    		"Juliana Saad",
            "",
            "Juliana Saad3",
            "Juliana Saad4",
            "Juliana Saad5",
            "Juliana Saad6",
            "Juliana Saad7"
    };
    
    private String[] phoneStrings = {
    		"91234 5678",
            "",
            "92345 6789",
            "99999 1234",
            "95555 5555",
            "96666 6666",
            "97777 7777",
    };
    */
    String gpapprow = new String("40"); 
    int count_row = Integer.parseInt(gpapprow);
    String example = String.valueOf(count_row);
        
    int count = count_row;               
    
    public String[] imgStrings = new String[count];
    {    	
    	for(int i=0;i<count;i++){
    		if(count<100){imgStrings[i]="http://androidexample.com/media/webservice/LazyListView_images/image"+i+".png";}
    		else {imgStrings[i] = null;}
    		
    	} 
    	    	    	
    }       
    
        
    public String[] descrStrings = new String[count];
    {    	
    	for(int i=0;i<count;i++){
    		if(count<100){descrStrings[i]="username "+i+"lastname";}
    		else {descrStrings[i] = null;}
    		
    	} 
    	    	
    }
    
    public String[] phoneStrings = new String[count];
    {  phoneStrings[0] = "90000 0011"; phoneStrings[1] = "90000 0022";   	}
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }    
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_refresh:
	        	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	            
	        	if (networkInfo != null && networkInfo.isConnected()) {
	            // fetch data
	        		//new asynctaskwebservice(MainActivity.this).execute();            		
	        	} else {
	            // display error
	        		Toast.makeText(getApplicationContext(), "sem acesso a internet", Toast.LENGTH_SHORT).show();		
	        	}
	        	//start code                
	            
	         //new UpdateProduct().execute();
	      // Progress Dialog                        
	      	
	         //end code
	        	
	        	//start code    
	        	
	        	// Server Request URL
	            //String serverURL = "http://androidexample.com/media/webservice/getPage.php";
	             
	            // Create Object and call AsyncTask execute Method
	            //new LongOperation().execute(serverURL);
	            
	            /* This block is for getting the image url to download from the server */
	                           
	        	//Intent refresh = new Intent("android.intent.action.MAIN");            	    
	        	
	        	final GetDataFromDB getvalues = new GetDataFromDB();
	            final GetPhoneFromDB getphone = new GetPhoneFromDB();
	            final GetUserFromDB getusername = new GetUserFromDB();
	            final DataBaseHandler db = new DataBaseHandler(MainActivity.this);
	            
	            // use final PrgressDialog can not use setCancelable 
	            //final ProgressDialog dialog = ProgressDialog.show(MainActivity.this,"gp app", "Verificando acompanhantes ...", true);
	            //dialog = ProgressDialog.show(MainActivity.this,"gp app", "Verificando acompanhantes ...", true);
	            dialog = new ProgressDialog(this);
	    		dialog.setMessage("Updating user database. Please wait...");
	    		dialog.setIndeterminate(false);
	            dialog.setCancelable(true);
	            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancelar", new DialogInterface.OnClickListener() {
	    		    @Override
	    		    public void onClick(DialogInterface dialog, int which) {
	    		      Thread.interrupted();
	    		      dialog.dismiss();
	    		    }
	    		  });
	            dialog.show();
	
	            new Thread(new Runnable() {
	                public void run() {
	                    String response = getvalues.getImageUrlFromDB();
	                    String response2 = getphone.getPhoneFromDB();
	                    String response3 = getusername.getUserFromDB();
	                    System.out.println("Response : " + response);
	                    System.out.println("Response : " + response2);
	                    System.out.println("Response : " + response3);
	                     
	                    dismissDialog(dialog);
	                    if (!response.equalsIgnoreCase("")) {
	                        if (!response.equalsIgnoreCase("error")) {
	                            dismissDialog(dialog);
	                             
	                            // Got the response, now split it to get the image Urls from GetDataFromDB
	                            //String imgStrings = response;                        
	                            String[] imgStringsPHP = response.split(",");
	                            //{test[0] = response; test[1] = response; test[2] = response;}						
	                            //List<String> your_array_list = new ArrayList<String>();
	                            SharedPreferences imgPHP = getSharedPreferences(PREFS_USER_FROM_PHP, 0);
	                            SharedPreferences.Editor editorImgPHP = imgPHP.edit();
	                            editorImgPHP.putInt("imgStringLength", imgStringsPHP.length);
	                            editorImgPHP.remove("UserFromPHP");
	                            for(int j=0; j<imgStringsPHP.length; j++){
	                            	editorImgPHP.putString("UserFromPHP"+j,imgStringsPHP[j]);     				                                                
	                                editorImgPHP.commit();
	                            }
	                            
	                            String[] phoneStringsPHP = response2.split(",");
	                            //{test[0] = response; test[1] = response; test[2] = response;}						
	                            //List<String> your_array_list = new ArrayList<String>();
	                            SharedPreferences phonePHP = getSharedPreferences(PREFS_PHONE_FROM_PHP, 0);
	                            SharedPreferences.Editor editorPhonePHP = phonePHP.edit();                        
	                            editorPhonePHP.putInt("phoneStringLength", phoneStringsPHP.length);
	                            editorPhonePHP.remove("PhoneFromPHP");
	                            for(int j=0; j<phoneStringsPHP.length; j++){
	                            	editorPhonePHP.putString("PhoneFromPHP"+j,phoneStringsPHP[j]);                        				                                                
	                                editorPhonePHP.commit();
	                            }
	                            
	                            String[] usernameStringsPHP = response3.split(",");
	                            //{test[0] = response; test[1] = response; test[2] = response;}						
	                            //List<String> your_array_list = new ArrayList<String>();
	                            SharedPreferences usernamePHP = getSharedPreferences(PREFS_USERNAME_FROM_PHP, 0);
	                            SharedPreferences.Editor editorUsernamePHP = usernamePHP.edit();                        
	                            editorUsernamePHP.putInt("usernameStringLength", usernameStringsPHP.length);
	                            editorUsernamePHP.remove("UsernameFromPHP");
	                            System.out.println("db.getContactsCount() value : " + db.getContactsCount());
	                            if(db.getContactsCount() > 0){
	                            	db.deleteAll();
	                            }
	                            for(int j=0; j<usernameStringsPHP.length; j++){
	                            	editorUsernamePHP.putString("UsernameFromPHP"+j,usernameStringsPHP[j]);                        				                                                
	                                editorUsernamePHP.commit();
	                                db.addUserFromWebService(new DBUsers(usernameStringsPHP[j], phoneStringsPHP[j], imgStringsPHP[j]));
	                                //db.addUserFromWebService(new DBUsers(usernameStringsPHP[j], phoneStringsPHP[j], imgStringsPHP[j]));
	                                Log.e(TAG, "db.getContactsCount() values is : "+String.valueOf(db.getContactsCount()));
	                            }
	                            
	                        }
	                        //Intent refresh = new Intent("main.intent.action.LAUNCH");
	                        //startActivity(refresh);
	                        //finish();
	                        //startActivity(getIntent());
	                        
	                    } else {
	                        dismissDialog(dialog);
	                    }                        
	                    
	                    runOnUiThread(new Runnable(){
							@Override
							public void run() {
								list.setAdapter(null);
								adapter = new LazyImageLoadAdapter(MainActivity.this,  db.getAllUserImageUrl(), db.getAllUsername(), db.getAllUserPhone());
								list.setAdapter(adapter);
		                        //adapter.notifyDataSetChanged();
							}
						});
	                }
	            }).start();
	           
	          //end code                                                		
	            
	            return true;            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	    
	}
        
}