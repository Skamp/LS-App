package com.lsn.LoadSensing;

import com.lsn.LoadSensing.element.Position;
import com.lsn.LoadSensing.ui.CustomToast;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import greendroid.app.GDActivity;

public class LSAugRealActivity extends GDActivity{
	
	private LocationManager  locManager;
	private LocationListener locationListenerGPS;
	private LocationListener locationListenerNetwork;
	private GetLocation      getLocation;
	private Position         curPosition;
	private boolean gpsStatus;
	private boolean netStatus;
	
	private static String mapServer = "http://www.mixare.org/geotest.php";
	boolean isMixareInstalled = false;
	
	
	
	
	
	
	@Override
	protected void onPause() {
		
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
		super.onPause();
		
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
	protected void onResume() {
		
		super.onResume();
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
        }
        else
        {
        	//Obtain location using Async Task
        	getLocation = new GetLocation();
        	getLocation.execute();
        	curPosition = getLocation.getPosition();
        	
        	if (isMixareInstalled)
        	{
        		setActionBarContentView(R.layout.act_05_augreal);
        		
        		final Button berge = (Button)findViewById(R.id.Button);
        		berge.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent i = new Intent();
						i.setAction(Intent.ACTION_VIEW);
						i.setDataAndType(Uri.parse(mapServer + "?"+ getGets(curPosition.getLatitude(),curPosition.getLongitude(),curPosition.getAltitude())),"application/mixare-json");
						startActivity(i);
					}
        			
					private String getGets(Double lat, Double lon, Double alt)
					{
						return "latitude="+Double.toString(lat)+ "&longitude=" + Double.toString(lon) + "&altitude=" + Double.toString(alt);
					}
        			
        		});
        		final TextView tv = (TextView)findViewById(R.id.urlText);
        		tv.setText("This is the URL that will be called:\n" + mapServer+"?latitude="+Double.toString(curPosition.getLatitude())+ "&longitude=" + Double.toString(curPosition.getLongitude()) + "&altitude=" + Double.toString(curPosition.getAltitude()));
        	}
        	else
        	{
        		CustomToast.showCustomToast(this,
                        "Install Mixare",
                        CustomToast.IMG_ERROR,
                        CustomToast.LENGTH_LONG);
        		try
        		{
        			Intent i = new Intent();
        			i.setAction(Intent.ACTION_VIEW);
        			i.setData(Uri.parse("market://search?q=pname:org.mixare"));
        			startActivity(i); 

        			finish();
                }
        		catch (Exception ex)
        		{
        			ex.printStackTrace();
        		}
        	}
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
	



	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        isMixareInstalled = false;
        try
        {
        	PackageInfo pi = getPackageManager().getPackageInfo("org.mixare", 0);
        	
        	if (pi.versionCode >= 1)
        	{
        		isMixareInstalled = true;
        	}
        }
        catch (PackageManager.NameNotFoundException ex)
        {
        	ex.printStackTrace();
        }
        
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
		}
		
		private void getCurrentLocation()
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
		
		public Position getPosition()
		{
			return curPosition;
		}
		
	}
	
}
