package com.lsn.LoadSensing;

//import android.view.View;
//import android.view.View.OnClickListener;
//import greendroid.app.ActionBarActivity;
import greendroid.app.ActionBarActivity;
import greendroid.app.GDTabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lsn.LoadSensing.faves.LSFavesImagesActivity;
import com.lsn.LoadSensing.faves.LSFavesNetworksActivity;
import com.lsn.LoadSensing.faves.LSFavesSensorsActivity;


public class LSFavesActivity extends GDTabActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        //initQuickActionBar();
        
        final String listText =  getString(R.string.tab_fav_networks);
		final Intent listIntent = new Intent(this, LSFavesNetworksActivity.class);
		listIntent.putExtra(ActionBarActivity.GD_ACTION_BAR_VISIBILITY, View.GONE);
		addTab(listText, listText, listIntent);
		
		final String sensorText =  getString(R.string.tab_fav_sensors);
		final Intent sensorIntent = new Intent(this, LSFavesSensorsActivity.class);
		sensorIntent.putExtra(ActionBarActivity.GD_ACTION_BAR_VISIBILITY, View.GONE);
		addTab(sensorText, sensorText, sensorIntent);
		
		final String imageText =  getString(R.string.tab_fav_images);
		final Intent imageIntent = new Intent(this, LSFavesImagesActivity.class);
		imageIntent.putExtra(ActionBarActivity.GD_ACTION_BAR_VISIBILITY, View.GONE);
		addTab(imageText, imageText, imageIntent);
              
        
        
        
    }
	
//	private void initQuickActionBar(){
//		
//		DashboardClickListener dbClickListener = new DashboardClickListener();
//        findViewById(R.id.dsh_btn_favNetwork).setOnClickListener(dbClickListener);
//        findViewById(R.id.dsh_btn_favSensor).setOnClickListener(dbClickListener);
//        findViewById(R.id.dsh_btn_favImage).setOnClickListener(dbClickListener);
//	}
//	
//	
//	private class DashboardClickListener implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			Intent i = null;
//			switch (v.getId()) {
//			case R.id.dsh_btn_favNetwork:
//				i = new Intent(LSFavesActivity.this,LSNetListActivity.class);
//				break;
//			case R.id.dsh_btn_favSensor:
//				i = new Intent(LSFavesActivity.this,LSNetMapsActivity.class);
//				break;
//			case R.id.dsh_btn_favImage:
//				i = new Intent(LSFavesActivity.this,LSQRCodeActivity.class);
//				break;
//			default:
//				break;
//			}
//			if (i!=null){
//				startActivity(i);
//			}
//		}
//    }
//	@Override
//    public int createLayout() {
//        return R.layout.info;
//    }
	
}
