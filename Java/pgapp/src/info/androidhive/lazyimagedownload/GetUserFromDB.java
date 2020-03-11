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
 
public class GetUserFromDB {
 
    public String getUserFromDB() {
        try {
 
            HttpPost httppost3;
            HttpClient httpclient3;
            httpclient3 = new DefaultHttpClient();
            httppost3 = new HttpPost(
                    "http://www.tamavodka.net23.net/android_php/getusernamephp.php"); // change this to your URL.....
            ResponseHandler<String> response3Handler = new BasicResponseHandler();
            final String response3 = httpclient3.execute(httppost3,
                    response3Handler);
             
            return response3;
 
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
            return "error";
        }
    }
}

