package com.lsn.LoadSensing;

import java.util.ArrayList;

import com.lsn.LoadSensing.adapter.LSNetworkAdapter;
import com.lsn.LoadSensing.element.LSNetwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import greendroid.app.GDListActivity;

public class LSNetCloserActivity extends GDListActivity{
	
	private double curLat;
	private double curLon;
	private LocationManager locManager;
	private LocationListener locListener;
	private boolean providerStatus;
	
	private ProgressDialog       m_ProgressDialog = null;
	private ArrayList<LSNetwork> m_networks = null;
	private LSNetworkAdapter       m_adapter;
	private Runnable             viewNetworks;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_06_netcloser);
        
        m_networks = new ArrayList<LSNetwork>();
        this.m_adapter = new LSNetworkAdapter(this,R.layout.row_list_network,m_networks);
        setListAdapter(this.m_adapter);
        
        
        viewNetworks = new Runnable()
        {

			@Override
			public void run() {
				
				//getLocation();
				getNetworks();
			}
        };
        Thread thread = new Thread(null,viewNetworks,"ViewNetworks");
        thread.start();
        
        m_ProgressDialog = ProgressDialog.show(LSNetCloserActivity.this, "Please wait...", "Retrieving location and networks...", true);
    }
	
	
	private Runnable returnRes = new Runnable() {

    	@Override
    	public void run() {
    		if(m_networks != null && m_networks.size() > 0){
    			
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_networks.size();i++)
                m_adapter.add(m_networks.get(i));
            }
    		
    		m_ProgressDialog.hide();
            m_adapter.notifyDataSetChanged();
            
    		showLocation();
    		Log.i("LOCATION",String.valueOf(providerStatus));
    	}
    };
	
    private void getLocation() {
    	try{
	    	//Obtenemos una referencia al LocationManager
	    	locManager = 
	    		(LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    	
	    	//Obtenemos la última posición conocida
	    	//Location loc = 
	    	//	locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    	
	    	//Mostramos la última posición conocida
	    	//curLat=loc.getLatitude();
    		//curLon=loc.getLongitude();
	    	
	    	//Nos registramos para recibir actualizaciones de la posición
	    	locListener = new LocationListener() {
		    	public void onLocationChanged(Location location) {
		    		
		    		setProviderStatus(true);
		    		curLat= location.getLatitude();
		    		curLon= location.getLongitude();
		    		//Log.i("LOCATION","onLocationChanged");
		    	}
		    	public void onProviderDisabled(String provider){
		    		
		    		setProviderStatus(false);
		    		//Log.i("LOCATION","onProviderDisabled");
		    	}
		    	public void onProviderEnabled(String provider){
		    		
		    		setProviderStatus(true);
		    		//Log.i("LOCATION","onProviderEnabled");
		    	}
		    	public void onStatusChanged(String provider, int status, Bundle extras){
		    		
		    		switch (status)
		    		{
		    			case LocationProvider.AVAILABLE:
		    				setProviderStatus(true);
		    				break;
		    			case LocationProvider.OUT_OF_SERVICE:
		    				setProviderStatus(false);
		    				break;
		    			case LocationProvider.TEMPORARILY_UNAVAILABLE:
		    				setProviderStatus(false);
		    				break;
		    		}
		    		//Log.i("LOCATION","onStatusChanged");
		    	}
	    	};
	    	
	    	locManager.requestLocationUpdates(
	    			LocationManager.GPS_PROVIDER, 15000, 0, locListener);
    	} catch (Exception e) { 
            Log.e("BACKGROUND_PROC", e.getMessage());
	    }
	    runOnUiThread(returnRes);		
	}
    
	protected void showLocation() {
		
		TextView txtLocation = (TextView)findViewById(R.id.txtLocation);
//		if (providerStatus)
//        {
	        String strYourLocationFormat = getResources().getString(R.string.strYourLocation);
	        String strYourLocation = String.format(strYourLocationFormat, String.valueOf(curLat), String.valueOf(curLon));  
	        
	        txtLocation.setText(strYourLocation);
//        }     
//        else
//        {
//        	txtLocation.setText(R.string.strErrorLocation);
//        }
		
	}

	private void getNetworks() {

		try{
          m_networks = new ArrayList<LSNetwork>();
          LSNetwork o1 = new LSNetwork();
          o1.setNetworkName("Network 1");
          o1.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o1.setNetworkNumber("XX");
          LSNetwork o2 = new LSNetwork();
          o2.setNetworkName("Network 2");
          o2.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o2.setNetworkNumber("YY");
          LSNetwork o3 = new LSNetwork();
          o3.setNetworkName("Network 3");
          o3.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o3.setNetworkNumber("YY");
          LSNetwork o4 = new LSNetwork();
          o4.setNetworkName("Network 4");
          o4.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o4.setNetworkNumber("YY");
          LSNetwork o5 = new LSNetwork();
          o5.setNetworkName("Network 5");
          o5.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o5.setNetworkNumber("YY");
          LSNetwork o6 = new LSNetwork();
          o6.setNetworkName("Network 6");
          o6.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o6.setNetworkNumber("XX");
          LSNetwork o7 = new LSNetwork();
          o7.setNetworkName("Network 7");
          o7.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o7.setNetworkNumber("YY");
          LSNetwork o8 = new LSNetwork();
          o8.setNetworkName("Network 8");
          o8.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o8.setNetworkNumber("YY");
          LSNetwork o9 = new LSNetwork();
          o9.setNetworkName("Network 9");
          o9.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o9.setNetworkNumber("YY");
          LSNetwork o10 = new LSNetwork();
          o10.setNetworkName("Network 10");
          o10.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o10.setNetworkNumber("YY");
          
          m_networks.add(o1);
          m_networks.add(o2);
          m_networks.add(o3);
          m_networks.add(o4);
          m_networks.add(o5);
          m_networks.add(o6);
          m_networks.add(o7);
          m_networks.add(o8);
          m_networks.add(o9);
          m_networks.add(o10);

          Thread.sleep(1000);
          Log.i("ARRAY", ""+ m_networks.size());
        } catch (Exception e) { 
          Log.e("BACKGROUND_PROC", e.getMessage());
        }
        runOnUiThread(returnRes);		
	}
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
        Toast.makeText(getApplicationContext(), "Has pulsado la posición " + position +", Item " + m_adapter.getNetworkName(position), Toast.LENGTH_LONG).show();
    }

	public boolean isProviderEnabled() {
		return providerStatus;
	}

	public void setProviderStatus(boolean providerStatus) {
		this.providerStatus = providerStatus;
	}
	
}
