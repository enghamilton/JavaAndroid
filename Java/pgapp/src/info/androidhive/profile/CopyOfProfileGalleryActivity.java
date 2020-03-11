package info.androidhive.profile;

//http://android-er.blogspot.com.br/2012/09/execute-asynctask-with-series-of.html

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import info.androidhive.imageslider.R;
import info.androidhive.lazyimagedownload.MainActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.GridView;

public class CopyOfProfileGalleryActivity extends Activity {
	
	ProgressBar loadingProgressBar;
	//ImageView[] targetImage = new ImageView[5];
	ImageView[] targetImage = new ImageView[2];
	String getIntentId;
	String getIntentUser;
	String getIntentImage;
	private GridView gridView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_grid_view);
      
      gridView = (GridView) findViewById(R.id.grid_view);
      
      Intent getId = getIntent();
	  getIntentId = getId.getStringExtra("id");
	  getIntentUser = getId.getStringExtra("user");
	  getIntentImage = getId.getStringExtra("image");
      
      //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8b9dc3")));
	  //getActionBar requires API level 11, manifest was 8 changed for 11
	  getActionBar().setDisplayHomeAsUpEnabled(true);
	  
      
      targetImage[0] = (ImageView)findViewById(R.id.target0);
      targetImage[1] = (ImageView)findViewById(R.id.target1);
      //targetImage[2] = (ImageView)findViewById(R.id.target2);
      //targetImage[3] = (ImageView)findViewById(R.id.target3);
      //targetImage[4] = (ImageView)findViewById(R.id.target4);
      
      loadingProgressBar = (ProgressBar)findViewById(R.id.loadingprogress);
      
      
      //Load bitmap from internet
      //As a example, I make all images load from the same source
      String onLineImage0 = "http://goo.gl/1VArP";
      String onLineImage1 = "http://goo.gl/1VArP";
      String onLineImage2 = "http://goo.gl/1VArP";
      String onLineImage3 = "http://goo.gl/1VArP";
      String onLineImage4 = "http://goo.gl/1VArP";
      URL onLineURL0, onLineURL1, onLineURL2, onLineURL3, onLineURL4;
      
      ArrayList<String> onLineImage = new ArrayList<String>();
      onLineImage.add("http://www.tamavodka.net23.net/images/"+getIntentId+"/photo1.jpg");
      onLineImage.add("http://www.tamavodka.net23.net/images/"+getIntentId+"/photo2.jpg");
      //onLineImage.add("http://www.tamavodka.net23.net/images/"+getIntentId+"/photo3.jpg");
      //onLineImage.add("http://www.tamavodka.net23.net/images/"+getIntentId+"/photo4.jpg");
      //onLineImage.add("http://www.tamavodka.net23.net/images/"+getIntentId+"/photo5.jpg");
      
		try {			
			onLineURL0 = new URL(onLineImage0);
			onLineURL1 = new URL(onLineImage1);
			onLineURL2 = new URL(onLineImage2);
			onLineURL3 = new URL(onLineImage3);
			onLineURL4 = new URL(onLineImage4);
									
			URL onLineURLconnection[] = new URL[2];
			for (int i=0; i < 2; i++){				
				onLineURLconnection[i] = new URL(onLineImage.get(i));
			}			
			
			//new MyNetworkTask(5, targetImage, loadingProgressBar).execute(onLineURL0, onLineURL1, onLineURL2, onLineURL3, onLineURL4);			
			//new MyNetworkTask(5, targetImage, loadingProgressBar).execute(onLineURLconnection);
			new MyNetworkTask(2, targetImage, loadingProgressBar).execute(onLineURLconnection);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
      
  }
  
  //Navigate Up with a New Back Stack - Beginning in Android 4.1 (API level 16)  Specify the ParentActivity - Up Navigation
  // http://developer.android.com/training/implementing-navigation/ancestral.html
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
  private class MyNetworkTask extends AsyncTask<URL, Integer, Void>{  
  	
  	ImageView[] tIV;
  	Bitmap[] tBM;
  	ProgressBar tProgressBar;
  	public ImageView imageGridView;
  	
  	public MyNetworkTask(int numberOfImage, ImageView[] iv, ProgressBar pb){
  		
  		tBM = new Bitmap[numberOfImage];
  		
  		tIV = new ImageView[numberOfImage];
  		for(int i = 0; i < numberOfImage; i++){
  			tIV[i] = iv[i];
  		}
  		
  		tProgressBar = pb;
  	}

		@Override
		//protected Void doInBackground(URL... urls) {		
		protected Void doInBackground(URL... urls) {

			if (urls.length > 0){
			
				for(int i = 0; i < urls.length; i++){
			
					URL networkUrl = urls[i];
					
					try {
						tBM[i] = BitmapFactory.decodeStream(
								networkUrl.openConnection().getInputStream());
						// http://stackoverflow.com/questions/5453708/android-how-to-use-environment-getexternalstoragedirectory
						OutputStream outStream = null;
						//File file = new File(extStorageDirectory, "er.PNG");
						//File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/gpApp/"+getIntentId+"/", "photo"+i+".png");						
						File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/gpapp/"+getIntentId+"/", "photo"+i+".png");
						//File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/gpApp/", "photo"+i+".png");
						file.getParentFile().mkdirs();
						
							outStream = new FileOutputStream(file);
							tBM[i].compress(Bitmap.CompressFormat.PNG, 100, outStream);
							outStream.flush();
							outStream.close();
							
							//Toast.makeText(getBaseContext(), "Saved"+i, Toast.LENGTH_LONG).show();												

						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					publishProgress(i);
					
					//insert dummy delay to simulate lone time operation
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//Toast.makeText(getBaseContext(), "Finished "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Toast.LENGTH_LONG).show();			
			Toast.makeText(getBaseContext(), "Finished "+Environment.getExternalStorageDirectory().getAbsolutePath()+"/gpapp/"+getIntentId+"/", Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			if(values.length > 0){
				for(int i = 0; i < values.length; i++){
					//tIV[values[i]].setImageBitmap(tBM[values[i]]);
					imageGridView = (ImageView) gridView.getChildAt(i);
					imageGridView.setImageBitmap(tBM[values[i]]);
					tProgressBar.setProgress(values[i]+1);
				}
			}
			
		}

  }

}
