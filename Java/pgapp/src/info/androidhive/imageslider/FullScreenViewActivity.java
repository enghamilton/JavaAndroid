package info.androidhive.imageslider;

import info.androidhive.imageslider.adapter.FullScreenImageAdapter;
import info.androidhive.imageslider.helper.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class FullScreenViewActivity extends Activity{
	
	private Utils utils;
	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;
	String getIntentId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);

		viewPager = (ViewPager) findViewById(R.id.pager);

		utils = new Utils(getApplicationContext());
		
		Intent i = getIntent();
		int position = i.getIntExtra("position", 0);
		getIntentId = i.getStringExtra("imagePath");
		//getIntentId = "900000202";

		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
				utils.getFilePaths(null,getIntentId));

		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(position);
		
		//Toast.makeText(getBaseContext(), getIntentId, Toast.LENGTH_LONG).show();
	}
}
