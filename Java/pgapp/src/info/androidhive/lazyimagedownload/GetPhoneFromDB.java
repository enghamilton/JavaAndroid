/**
 * 
 */
/**
 * @author Microsoft
 *
 */
package info.androidhive.lazyimagedownload;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
 
public class GetPhoneFromDB {
 
    public String getPhoneFromDB() {
        try {
 
            HttpPost httppost2;
            HttpClient httpclient2;
            httpclient2 = new DefaultHttpClient();
            httppost2 = new HttpPost(
                    "http://www.tamavodka.net23.net/android_php/getphonephp.php"); // change this to your URL.....
            ResponseHandler<String> response2Handler = new BasicResponseHandler();
            final String response2 = httpclient2.execute(httppost2,
                    response2Handler);
             
            return response2;
 
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
            return "error";
        }
    }
}

