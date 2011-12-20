package com.lsn.LoadSensing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.lsn.LoadSensing.element.LSNetwork;
import com.lsn.LoadSensing.func.LSFunctions;
import com.lsn.LoadSensing.map.LSNetworksOverlay;

import greendroid.app.GDMapActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class LSNetMapsActivity extends GDMapActivity {

	MapView           mapView;
	List<Overlay>     mapOverlays;
	Drawable          drawable;
	Drawable          drawable2;
	LSNetworksOverlay itemizedOverlay;
	LSNetworksOverlay itemizedOverlay2;
	boolean           modeStreeView;
	
	private final int OPTIONS = 0;
	private final int HELP = 1;
	
	private QuickActionWidget quickActions;

	//private static String idSession;
	private ArrayList<LSNetwork> m_networks = null;

	
	@Override
	protected void onResume() {
		
		super.onResume();
		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		
		return false;
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.act_02_netmap);
        
        initActionBar();
        initQuickActionBar();
        
        Bundle bundle = getIntent().getExtras();
        
        if (bundle != null)
        {
        	//idSession = bundle.getString("SESSION");
    	}  
        m_networks = new ArrayList<LSNetwork>();
        
        
        final MapView mapView = (MapView) findViewById(R.id.netmap);
        mapView.setBuiltInZoomControls(true);
        setStreetView();
        
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
			
			e.printStackTrace();
		}
		
        mapOverlays = mapView.getOverlays();
		
		// first overlay
		drawable = getResources().getDrawable(R.drawable.marker);
		itemizedOverlay = new LSNetworksOverlay(drawable, mapView,m_networks);
		
		GeoPoint point = null;
		for (int i = 0; i<m_networks.size(); i++)
		{
			Integer intLat = (int) (m_networks.get(i).getNetworkPosition().getLatitude()*1e6);
			Integer intLon = (int) (m_networks.get(i).getNetworkPosition().getLongitude()*1e6);
			String strName = m_networks.get(i).getNetworkName();
			String strSituation = m_networks.get(i).getNetworkSituation();
			Integer strNumSensor = m_networks.get(i).getNetworkNumSensors();
			String strNetDescripFormat = getResources().getString(R.string.strNetDescrip);
		    String strNetDescrip = String.format(strNetDescripFormat, strSituation, strNumSensor);
			
			
			point = new GeoPoint(intLat,intLon);
			OverlayItem overlayItem = new OverlayItem(point, strName, strNetDescrip);
			itemizedOverlay.addOverlay(overlayItem);
			
			mapOverlays.add(itemizedOverlay);
		}
		
		
//		GeoPoint point = new GeoPoint((int)(51.5174723*1E6),(int)(-0.0899537*1E6));
//		OverlayItem overlayItem = new OverlayItem(point, "Network 1", 
//				"(Network 1 description)");
//		itemizedOverlay.addOverlay(overlayItem);
//		
//		GeoPoint point2 = new GeoPoint((int)(51.515259*1E6),(int)(-0.086623*1E6));
//		OverlayItem overlayItem2 = new OverlayItem(point2, "Network 2", 
//				"(Network 2 description)");		
//		itemizedOverlay.addOverlay(overlayItem2);
		
//		mapOverlays.add(itemizedOverlay);
		
		// second overlay
//		drawable2 = getResources().getDrawable(R.drawable.marker2);
//		itemizedOverlay2 = new LSNetworksOverlay(drawable2, mapView);
//		
//		GeoPoint point3 = new GeoPoint((int)(51.513329*1E6),(int)(-0.08896*1E6));
//		OverlayItem overlayItem3 = new OverlayItem(point3, "Network 3", 
//				"Network 3 description");
//		itemizedOverlay2.addOverlay(overlayItem3);
//		
//		GeoPoint point4 = new GeoPoint((int)(51.51738*1E6),(int)(-0.08186*1E6));
//		OverlayItem overlayItem4 = new OverlayItem(point4, "Network 4", 
//				"Network 4 description");		
//		itemizedOverlay2.addOverlay(overlayItem4);
//		
//		mapOverlays.add(itemizedOverlay2);
		
		point = new GeoPoint((int)(40.416369*1E6),(int)(-3.702992*1E6));
		final MapController mc = mapView.getController();
		mc.animateTo(point);
		mc.setZoom(6);
        
    }
	
	private void setStreetView(){
		
		final MapView mapView = (MapView) findViewById(R.id.netmap);
		mapView.setSatellite(false);
		mapView.setStreetView(true);
		modeStreeView=true;
	}
	
	private void setSatelliteView(){
		
		final MapView mapView = (MapView) findViewById(R.id.netmap);
		mapView.setSatellite(true);
		mapView.setStreetView(false);
		modeStreeView=false;
	}
	
	/**
	 * @return the modeStreeView
	 */
	public boolean isModeStreeView() {
		return modeStreeView;
	}
	/**
	 * @param modeStreeView the modeStreeView to set
	 */
	public void setModeStreeView(boolean modeStreeView) {
		this.modeStreeView = modeStreeView;
	}
	
	private void initActionBar() {
		
    	addActionBarItem(Type.Add,OPTIONS);
    	addActionBarItem(Type.Help,HELP);
    	
	}
	
	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		
		 switch (item.getItemId()) {
    	
    	case OPTIONS:
    		Toast.makeText(getApplicationContext(), "Has pulsado el boton OPTIONS", Toast.LENGTH_SHORT).show();
    		quickActions.show(item.getItemView());
    		break;
    	case HELP:
    		Toast.makeText(getApplicationContext(), "Has pulsado el boton HELP", Toast.LENGTH_SHORT).show();
    		
    		break;
    	default:
    		return super.onHandleActionBarItemClick(item, position);
    	}
    	
		return true;
	} 
 
	
	////@Override
	////public boolean onCreateOptionsMenu(Menu menu) {

	////	MenuInflater inflater = getMenuInflater();
	////	inflater.inflate(R.menu.lsnetmaps_gmapsmode, menu);
		
	////	return true;
	////}
	
	////@Override
	////public boolean onMenuItemSelected(int featureId, MenuItem item) {

	////	final MapView mapView = (MapView) findViewById(R.id.netmap);
		
	////	switch (item.getItemId()) {
		
	////	case R.id.lsnetmaps_mnu_mapSat:
	////		mapView.setSatellite(true);
	////		mapView.setStreetView(false);
	////		break;
	////	case R.id.lsnetmaps_mnu_mapStreet:
	////		mapView.setStreetView(true);
	////		mapView.setSatellite(false);
	////		break;			
	////	}
	////	
	////	return true;
	////}
	
	
	
	private void initQuickActionBar()
	{
		quickActions = new QuickActionBar(this);
		quickActions.addQuickAction(new QuickAction(this,R.drawable.ic_menu_search,R.string.strSearch));
		quickActions.addQuickAction(new QuickAction(this,R.drawable.ic_menu_filter,R.string.strFilter));
		quickActions.addQuickAction(new QuickAction(this,android.R.drawable.ic_menu_mapmode,R.string.strMapMode));
		quickActions.setOnQuickActionClickListener(new OnQuickActionClickListener() {

			@Override
			public void onQuickActionClicked(QuickActionWidget widget, int position) {
				Toast.makeText(LSNetMapsActivity.this, "Item " + position + " pulsado", Toast.LENGTH_SHORT).show();
				switch(position) {
				
				case 0:
					break;
				case 1:
					break;
				case 2:
					//Switch map mode
					if (isModeStreeView()) {
						
						setSatelliteView();
					}
					else {
						setStreetView();
					}
					break;
				}
			}
		});
	}
	
}
