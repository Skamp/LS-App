package com.lsn.LoadSensing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lsn.LoadSensing.element.Position;
import com.lsn.LoadSensing.func.LSFunctions;
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
import android.os.Environment;
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
	JSONObject mixareInfo;
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
        	SystemClock.sleep(5000);
        	
        	if (isMixareInstalled)
        	{
        		setActionBarContentView(R.layout.act_05_augreal);
        		
        		final Button berge = (Button)findViewById(R.id.Button);
        		berge.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						generateJSONFile();
						
						Intent i = new Intent();
						i.setAction(Intent.ACTION_VIEW);
						//i.setDataAndType(Uri.parse(mapServer + "?"+ getGets(curPosition.getLatitude(),curPosition.getLongitude(),curPosition.getAltitude())),"application/mixare-json");
						//i.setDataAndType(mixareInfo,"application/mixare-json");
						try{
						i.setDataAndType(Uri.parse("file:///sdcard/LSApp_mixare.json"), "application/mixare-json");
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						startActivity(i);
					}
        			
					private String getGets(Double lat, Double lon, Double alt)
					{
						return "latitude="+Double.toString(lat)+ "&longitude=" + Double.toString(lon) + "&altitude=" + Double.toString(alt);
					}
        			
        		});
        		//final TextView tv = (TextView)findViewById(R.id.urlText);
        		//tv.setText("This is the URL that will be called:\n" + mapServer+"?latitude="+Double.toString(curPosition.getLatitude())+ "&longitude=" + Double.toString(curPosition.getLongitude()) + "&altitude=" + Double.toString(curPosition.getAltitude()));
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


	protected void generateJSONFile() {
		
		try {
			
			//mixareInfo = new JSONObject("{\"status\": \"OK\",\"num_results\": 4,\"results\": [{\"id\": \"1\",\"lat\": \"42.11\",\"lng\": \"2.71\",\"elevation\": \"0\",\"title\": \"North\",\"has_detail_page\": \"0\",\"webpage\": \"\"},{\"id\": \"2\",\"lat\": \"42.1\",\"lng\": \"2.72\",\"elevation\": \"0\",\"title\": \"East\",\"has_detail_page\": \"0\",\"webpage\": \"\"},{\"id\": \"3\",\"lat\": \"42.09\",\"lng\": \"2.71\",\"elevation\": \"0\",\"title\": \"South\",\"has_detail_page\": \"0\",\"webpage\": \"\"},{\"id\": \"4\",\"lat\": \"42.1\",\"lng\": \"2.7\",\"elevation\": \"0\",\"title\": \"West\",\"has_detail_page\": \"0\"}]}");
			//mixareInfo = new JSONObject(Uri.parse("file:///sdcard/LSApp_mixare.json").toString());
			
			mixareInfo = new JSONObject();
		
			
			JSONObject obj1 = new JSONObject();
			obj1.put("id", "1");
			obj1.put("lat", "42.11");
			obj1.put("lng", "2.71");
			obj1.put("elevation", "0");
			obj1.put("title", "Network 1");
			obj1.put("has_detail_page", "0");
			obj1.put("webpage", "");
			
			JSONObject obj2 = new JSONObject();
			obj2.put("id", "2");
			obj2.put("lat", "42.1");
			obj2.put("lng", "2.72");
			obj2.put("elevation", "0");
			obj2.put("title", "Network 2");
			obj2.put("has_detail_page", "0");
			obj2.put("webpage", "");
			
			JSONObject obj3 = new JSONObject();
			obj3.put("id", "3");
			obj3.put("lat", "42.09");
			obj3.put("lng", "2.71");
			obj3.put("elevation", "0");
			obj3.put("title", "Network 3");
			obj3.put("has_detail_page", "0");
			obj3.put("webpage", "");
			
			JSONObject obj4 = new JSONObject();
			obj4.put("id", "4");
			obj4.put("lat", "42.1");
			obj4.put("lng", "2.7");
			obj4.put("elevation", "0");
			obj4.put("title", "Network 4");
			obj4.put("has_detail_page", "0");
			obj4.put("webpage", "");
			
			
			JSONArray arrayObj= new JSONArray();
			arrayObj.put(obj1);
			arrayObj.put(obj2);
			arrayObj.put(obj3);
			arrayObj.put(obj4);
			
			mixareInfo.put("results",arrayObj);
			mixareInfo.put("num_results", new Integer(4));
			mixareInfo.put("status", "OK");
			
			if (LSFunctions.checkSDCard(this))
			{
				String filename = "LSApp_mixare.json";
				File file = new File(Environment.getExternalStorageDirectory(), filename);
				FileOutputStream fos;
				byte[] data = mixareInfo.toString().getBytes();
				try {
				    fos = new FileOutputStream(file);
				    fos.write(data);
				    fos.flush();
				    fos.close();
				} catch (FileNotFoundException e) {
				    // handle exception
				} catch (IOException e) {
				    // handle exception
				}
				
			}
		
		}
		catch (JSONException e)
		{
			e.printStackTrace();
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
			
	    	if (gpsStatus) locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);
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
