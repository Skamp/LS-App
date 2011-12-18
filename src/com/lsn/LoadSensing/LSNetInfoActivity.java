package com.lsn.LoadSensing;

import com.lsn.LoadSensing.element.LSNetwork;
import com.lsn.LoadSensing.element.Position;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import greendroid.app.GDActivity;

public class LSNetInfoActivity extends GDActivity {

	private String netID;
	private LSNetwork networkObj;
	private static String idSession;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.act_netinfo);
     
        
        //Intent i = getIntent();
        //networkObj = new LSNetwork();
        //networkObj = (LSNetwork) i.getParcelableExtra("NETWORK_OBJ");
        
        Bundle bundle = getIntent().getExtras();
        
        if (bundle != null)
        {
        	idSession = bundle.getString("SESSION");
        	netID = bundle.getString("NETID");
        	networkObj = new LSNetwork();
        	networkObj = bundle.getParcelable("NETWORK_OBJ");
    	}
        else
        {
        	netID = "Network not found";
        }
        
        TextView txtNetName = (TextView) findViewById(R.id.netName);
        txtNetName.setText(networkObj.getNetworkName());
        TextView txtNetSituation = (TextView) findViewById(R.id.netSituation);
        txtNetSituation.setText(networkObj.getNetworkSituation());
        TextView txtNetPosLatitude = (TextView) findViewById(R.id.netPosLatitude);
        txtNetPosLatitude.setText(networkObj.getNetworkPosition().getLatitude().toString());
        TextView txtNetPosLongitude = (TextView) findViewById(R.id.netPosLongitude);
        txtNetPosLongitude.setText(networkObj.getNetworkPosition().getLongitude().toString());
        TextView txtNetPosAltitude = (TextView) findViewById(R.id.netPosAltitude);
        txtNetPosAltitude.setText(networkObj.getNetworkPosition().getAltitude().toString());
        TextView txtNetNumSensors = (TextView) findViewById(R.id.netNumSensors);
        txtNetNumSensors.setText(networkObj.getNetworkNumSensors().toString());
        
        Button btnSensors = (Button) findViewById(R.id.btnLoadSensors);
        btnSensors.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent i = null;
		        i = new Intent(LSNetInfoActivity.this,LSSensorListActivity.class);
		        
		        if (i!=null){
					Bundle bundle = new Bundle();
					
					bundle.putString("SESSION", idSession);
					bundle.putString("ID_XARXA", networkObj.getNetworkId());
					
					i.putExtras(bundle);
		        	
					startActivity(i);
				}
			}
        	
        	
        });
        
    }

}