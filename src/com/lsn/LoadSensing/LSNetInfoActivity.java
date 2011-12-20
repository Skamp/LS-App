package com.lsn.LoadSensing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lsn.LoadSensing.element.LSNetwork;
import com.lsn.LoadSensing.element.Position;
import com.lsn.LoadSensing.func.LSFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import greendroid.app.GDActivity;

public class LSNetInfoActivity extends GDActivity {

	private String netID = null;
	private LSNetwork networkObj = null;
	private ArrayList<LSNetwork> m_networks = null;

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
        	//idSession = bundle.getString("SESSION");
        	netID = bundle.getString("NETID");
        	//networkObj = new LSNetwork();
        	networkObj = bundle.getParcelable("NETWORK_OBJ");
    	}
        
        if ((netID != null) && (networkObj == null))
        {
        	m_networks = new ArrayList<LSNetwork>();
            
        	JSONObject jsonData;
			try {
				// Server Request Ini
				Map<String, String> params = new HashMap<String, String>();
				params.put("session", LSHomeActivity.idSession);
				JSONArray jArray = LSFunctions.urlRequestJSONArray("http://viuterrassa.com/Android/getLlistatXarxes.php",params);
	
				for (int i = 0; i<jArray.length(); i++)
				{
					
						jsonData = jArray.getJSONObject(i);
					
					LSNetwork o1 = new LSNetwork();
					o1.setNetworkName(jsonData.getString("Nom"));
			        o1.setNetworkPosition(jsonData.getString("Lat"),jsonData.getString("Lon"));
			        o1.setNetworkNumSensors(jsonData.getString("Sensors"));
					o1.setNetworkId(jsonData.getString("IdXarxa"));
					o1.setNetworkSituation(jsonData.getString("Poblacio"));
					m_networks.add(o1);
				}
				// Server Request End 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
			for (int i = 0; i<m_networks.size(); i++)
			{
				if (netID.equals(m_networks.get(i).getNetworkId()))
				{
					networkObj = m_networks.get(i);
				}
			}
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
					
					bundle.putString("SESSION", LSHomeActivity.idSession);
					bundle.putParcelable("NETWORK_OBJ", networkObj);
					i.putExtras(bundle);
		        	
					startActivity(i);
				}
			}
        	
        	
        });
        
        Button btnImages = (Button) findViewById(R.id.btnLoadImages);
        btnImages.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent i = null;
		        i = new Intent(LSNetInfoActivity.this,LSNetImagesActivity.class);
		        
		        if (i!=null){
					Bundle bundle = new Bundle();
					
					bundle.putString("SESSION", LSHomeActivity.idSession);
					bundle.putParcelable("NETWORK_OBJ", networkObj);
					i.putExtras(bundle);
		        	
					startActivity(i);
				}
			}
        
        	
        });
    }

}
