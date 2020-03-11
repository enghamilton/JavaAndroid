package info.androidhive.lazyimagedownload;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
 
public class GetDataFromDB {
 
	/*
	 * http://www.coderzheaven.com/2012/09/23/simplest-lazy-loading-listview-android-data-populated-mysql-database-php/
	 */
	
    public String getImageUrlFromDB() {
        try {
 
            HttpPost httppost;
            HttpClient httpclient;
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(
                    "http://www.tamavodka.net23.net/android_php/getimagephp.php"); // change this to your URL.....
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost,
                    responseHandler);
             
            return response;
 
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
            return "error";
        }
    }
}

