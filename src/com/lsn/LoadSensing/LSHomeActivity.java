package com.lsn.LoadSensing;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBar;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class LSHomeActivity extends GDActivity {
    
	private final int CONFIG = 0;
	private final int ABOUT = 1;
	private final int HELP = 2;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.dshb_home);
        getActionBar().setType(ActionBar.Type.Empty);
        initActionBar();
        
        Bundle bundle = getIntent().getExtras();
        
        if (bundle != null)
        {
        	Toast.makeText(getApplicationContext(), "Bienvenido usuario "+ bundle.getString("USER") +" con password "+ bundle.getString("PASS"), Toast.LENGTH_LONG).show();
    	}       
        
        DashboardClickListener dbClickListener = new DashboardClickListener();
        findViewById(R.id.dsh_btn_netList).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_netMaps).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_QRCode).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_Faves).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_AR).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_netCloser).setOnClickListener(dbClickListener);
    }

    @Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		
    	Intent i = null;
    	
    	switch (item.getItemId()) {
    	
    	case CONFIG:
    		Toast.makeText(getApplicationContext(), "Has pulsado el boton CONFIG", Toast.LENGTH_SHORT).show();
    		i = new Intent(LSHomeActivity.this,LSConfigActivity.class);
    		break;
    	case ABOUT:
    		Toast.makeText(getApplicationContext(), "Has pulsado el boton ABOUT", Toast.LENGTH_SHORT).show();
    		//i = new Intent(LSHomeActivity.this,LSAboutActivity.class);
    		i = new Intent(LSHomeActivity.this,LSInfoActivity.class);
    		break;
    	case HELP:
    		Toast.makeText(getApplicationContext(), "Has pulsado el boton HELP", Toast.LENGTH_SHORT).show();
    		i = new Intent(LSHomeActivity.this,LSHelpActivity.class);
    		break;
    	default:
    		return super.onHandleActionBarItemClick(item, position);
    	}
    	if (i!=null){
			startActivity(i);
		}
		return true;
	}

	private void initActionBar() {
		
    	addActionBarItem(Type.Settings,CONFIG);
    	addActionBarItem(Type.Info,ABOUT);
    	addActionBarItem(Type.Help,HELP);
	}

	private class DashboardClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent i = null;
			switch (v.getId()) {
			case R.id.dsh_btn_netList:
				i = new Intent(LSHomeActivity.this,LSNetListActivity.class);
				break;
			case R.id.dsh_btn_netMaps:
				i = new Intent(LSHomeActivity.this,LSNetMapsActivity.class);
				break;
			case R.id.dsh_btn_QRCode:
				i = new Intent(LSHomeActivity.this,LSQRCodeActivity.class);
				break;
			case R.id.dsh_btn_Faves:
				i = new Intent(LSHomeActivity.this,LSFavesActivity.class);
				break;
			case R.id.dsh_btn_AR:
				i = new Intent(LSHomeActivity.this,LSAugRealActivity.class);
				break;
			case R.id.dsh_btn_netCloser:
				i = new Intent(LSHomeActivity.this,LSNetCloserActivity.class);
				break;
			default:
				break;
			}
			if (i!=null){
				startActivity(i);
			}
		}
    }
}