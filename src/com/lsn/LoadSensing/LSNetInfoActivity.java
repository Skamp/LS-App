package com.lsn.LoadSensing;

import android.os.Bundle;
import android.widget.TextView;
import greendroid.app.GDActivity;

public class LSNetInfoActivity extends GDActivity {
	
	private String netID;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.act_netinfo);
     
        Bundle bundle = getIntent().getExtras();
        
        if (bundle != null)
        {
        	netID = bundle.getString("NETID");
    	}
        else
        {
        	netID = "Network not found";
        }
        
        TextView txtNetName = (TextView) findViewById(R.id.netName);
        txtNetName.setText("Network ID: " + netID);
    }

}
