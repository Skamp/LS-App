package com.lsn.LoadSensing;

import java.util.ArrayList;

import com.lsn.LoadSensing.adapter.LSNetworkAdapter;
import com.lsn.LoadSensing.element.LSNetwork;
import com.lsn.LoadSensing.ui.CustomToast;
import com.lsn.LoadSensing.element.Position;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import greendroid.app.GDListActivity;

public class LSNetCloserActivity extends GDListActivity{
	
	
	private LocationManager  locManager;
	private LocationListener locationListenerGPS;
	private LocationListener locationListenerNetwork;
	private GetLocation      getLocation;
	private boolean gpsStatus;
	private boolean netStatus;
	
	private ProgressDialog       m_ProgressDialog = null;
	private ArrayList<LSNetwork> m_networks = null;
	private LSNetworkAdapter     m_adapter;
	private Runnable             viewNetworks;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_06_netcloser);
        
        m_networks = new ArrayList<LSNetwork>();
        this.m_adapter = new LSNetworkAdapter(this,R.layout.row_list_network,m_networks);
        setListAdapter(this.m_adapter);
        
        
        if (locManager == null)
		{
        	//Obtain reference to LocationManager
        	locManager = 
        			(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		}
        //Check status of location services
        checkGPSStatus();
        checkNETStatus();
        
        
        if (!gpsStatus && !netStatus)
        {
        	//Show error message if there are no active services
        	CustomToast.showCustomToast(this,
                    R.string.msg_NOLocServ,
                    CustomToast.IMG_ERROR,
                    CustomToast.LENGTH_LONG);
        	
        	TextView txtLocation = (TextView)findViewById(R.id.txtLocation);
        	txtLocation.setBackgroundColor(Color.RED);
        	txtLocation.setText(R.string.msg_NOLocServ);
        }
        else
        {
            //Obtain location using Async Task
        	getLocation = new GetLocation();
        	getLocation.execute();
        
        	//Feed list with closer networks
	        viewNetworks = new Runnable()
	        {
	
	        	@Override
	        	public void run() {
	        		
	        		getNetworks();
	        	}
	        };
	        Thread thread = new Thread(null,viewNetworks,"ViewNetworks");
	        thread.start();
	        m_ProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.msg_PleaseWait), getResources().getString(R.string.msg_retrievNetworks), true);
        }
    }
	
	
	private void checkNETStatus() {
		
		try
		{
			gpsStatus = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}
		catch(Exception ex) {}
		
	}


	private void checkGPSStatus() {
		
		try
		{
			netStatus = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		}
		catch(Exception ex) {}
	}
	
	
	private Runnable returnRes = new Runnable() {

    	@Override
    	public void run() {

    		if(m_networks != null && m_networks.size() > 0){
    			
    		   m_adapter.notifyDataSetChanged();
    		   for(int i=0;i<m_networks.size();i++)
    		   m_adapter.add(m_networks.get(i));
    		}
    		
    		m_ProgressDialog.dismiss();
    		m_adapter.notifyDataSetChanged();
    	}
    };

	private void getNetworks() {

		try{
          m_networks = new ArrayList<LSNetwork>();
          LSNetwork o1 = new LSNetwork();
          o1.setNetworkName("Network 1");
          o1.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o1.setNetworkNumSensors(3);
          LSNetwork o2 = new LSNetwork();
          o2.setNetworkName("Network 2");
          o2.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o2.setNetworkNumSensors(2);
          LSNetwork o3 = new LSNetwork();
          o3.setNetworkName("Network 3");
          o3.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o3.setNetworkNumSensors(4);
          LSNetwork o4 = new LSNetwork();
          o4.setNetworkName("Network 4");
          o4.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o4.setNetworkNumSensors(5);
          LSNetwork o5 = new LSNetwork();
          o5.setNetworkName("Network 5");
          o5.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o5.setNetworkNumSensors(6);
          LSNetwork o6 = new LSNetwork();
          o6.setNetworkName("Network 6");
          o6.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o6.setNetworkNumSensors(7);
          LSNetwork o7 = new LSNetwork();
          o7.setNetworkName("Network 7");
          o7.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o7.setNetworkNumSensors(5);
          LSNetwork o8 = new LSNetwork();
          o8.setNetworkName("Network 8");
          o8.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o8.setNetworkNumSensors(6);
          LSNetwork o9 = new LSNetwork();
          o9.setNetworkName("Network 9");
          o9.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o9.setNetworkNumSensors(4);
          LSNetwork o10 = new LSNetwork();
          o10.setNetworkName("Network 10");
          o10.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o10.setNetworkNumSensors(3);
          
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

          Thread.sleep(500);
          Log.i("ARRAY", ""+ m_networks.size());
        } catch (Exception e) { 
          Log.e("BACKGROUND_PROC", e.getMessage());
        }
        runOnUiThread(returnRes);		
	}
	
	@Override
	public void onBackPressed() {
		
		if (gpsStatus || netStatus)
		{
			if (gpsStatus) {
				locManager.removeUpdates(locationListenerGPS);
			}
	
			if (netStatus) {
				locManager.removeUpdates(locationListenerNetwork);
			}
			
			getLocation.cancel(true);
		}
		super.onBackPressed();
	}


	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
        Toast.makeText(getApplicationContext(), "Has pulsado la posición " + position +", Item " + m_adapter.getNetworkName(position), Toast.LENGTH_LONG).show();
    }
	
	public class GetLocation extends AsyncTask<Void,Position,Void>
	{
		private Position curPosition;
		private boolean running = true;
		
		@Override
		protected Void doInBackground(Void... arg0) {
		
			while (running)
			{
					getCurrentLocation();
					publishProgress(curPosition);
					SystemClock.sleep(5000);
			}
			return null;
		}

		
		@Override
		protected void onCancelled() {
			
			running=false;
			super.onCancelled();
		}

		
		@Override
		protected void onPostExecute(Void result) {
			
			locManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 15000, 0, locationListenerGPS);
			locManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 15000, 0, locationListenerNetwork);
			
			TextView txtLocation = (TextView)findViewById(R.id.txtLocation);
			
			String strYourLocationFormat = getResources().getString(R.string.strYourLocation);
		    String strYourLocation = String.format(strYourLocationFormat, String.valueOf(curPosition.getLatitude()), String.valueOf(curPosition.getLongitude()));  
		        
		    txtLocation.setText(strYourLocation);
		}

		
		@Override
		protected void onPreExecute() {
			
			curPosition = new Position();
			
			super.onPreExecute();
			locationListenerGPS = new LocationListener()
			{

				@Override
				public void onLocationChanged(Location location) {
					curPosition = new Position(location);
				}

				@Override
				public void onProviderDisabled(String provider) {}

				@Override
				public void onProviderEnabled(String provider) {}

				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {}
				
			};
			
			locationListenerNetwork = new LocationListener()
			{
				@Override
				public void onLocationChanged(Location location) {
					
					curPosition = new Position(location);
				}

				@Override
				public void onProviderDisabled(String provider) {}

				@Override
				public void onProviderEnabled(String provider) {}

				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {}
			};
			
	    	if (gpsStatus)     locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);
			if (netStatus) locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
			
		}

		
		@Override
		protected void onProgressUpdate(Position... values) {
			
			
			
			locManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 15000, 0, locationListenerGPS);
			locManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 15000, 0, locationListenerNetwork);

			
			TextView txtLocation = (TextView)findViewById(R.id.txtLocation);

			String strYourLocationFormat = getResources().getString(R.string.strYourLocation);
		    String strYourLocation = String.format(strYourLocationFormat, String.valueOf(curPosition.getLatitude()), String.valueOf(curPosition.getLongitude()));  
		        
		    txtLocation.setText(strYourLocation);
		}
		
		public void getCurrentLocation()
		{
			locManager.removeUpdates(locationListenerGPS);
			locManager.removeUpdates(locationListenerNetwork);

			Location gpsLocation=null;
			Location netLocation=null;
			
			if (gpsStatus) gpsLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (netStatus) netLocation = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			
			if (gpsLocation!=null && netLocation != null)
			{
				if (gpsLocation.getTime() > netLocation.getTime())
				{
					curPosition.setPosition(gpsLocation);
				}
				else
				{
					curPosition.setPosition(netLocation);
				}
			}
			else if (gpsLocation !=  null)
			{
				curPosition.setPosition(gpsLocation);			
			}
			else if (netLocation !=  null)
			{
				curPosition.setPosition(netLocation);			
			}
			else
			{
				return;
			}
		}
	}
}
