package com.lsn.LoadSensing;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
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
        
        final MapView mapView = (MapView) findViewById(R.id.netmap);
        mapView.setBuiltInZoomControls(true);
        setStreetView();
        
        
        //List<Overlay> capas = mapView.getOverlays();
        //LSdrawNetworks om = new LSdrawNetworks();
        //capas.add(om);
        //mapView.postInvalidate();
        
        mapOverlays = mapView.getOverlays();
		
		// first overlay
		drawable = getResources().getDrawable(R.drawable.marker);
		itemizedOverlay = new LSNetworksOverlay(drawable, mapView);
		
		GeoPoint point = new GeoPoint((int)(51.5174723*1E6),(int)(-0.0899537*1E6));
		OverlayItem overlayItem = new OverlayItem(point, "Network 1", 
				"(Network 1 description)");
		itemizedOverlay.addOverlay(overlayItem);
		
		GeoPoint point2 = new GeoPoint((int)(51.515259*1E6),(int)(-0.086623*1E6));
		OverlayItem overlayItem2 = new OverlayItem(point2, "Network 2", 
				"(Network 2 description)");		
		itemizedOverlay.addOverlay(overlayItem2);
		
		mapOverlays.add(itemizedOverlay);
		
		// second overlay
		drawable2 = getResources().getDrawable(R.drawable.marker2);
		itemizedOverlay2 = new LSNetworksOverlay(drawable2, mapView);
		
		GeoPoint point3 = new GeoPoint((int)(51.513329*1E6),(int)(-0.08896*1E6));
		OverlayItem overlayItem3 = new OverlayItem(point3, "Network 3", 
				"Network 3 description");
		itemizedOverlay2.addOverlay(overlayItem3);
		
		GeoPoint point4 = new GeoPoint((int)(51.51738*1E6),(int)(-0.08186*1E6));
		OverlayItem overlayItem4 = new OverlayItem(point4, "Network 4", 
				"Network 4 description");		
		itemizedOverlay2.addOverlay(overlayItem4);
		
		mapOverlays.add(itemizedOverlay2);
		
		final MapController mc = mapView.getController();
		mc.animateTo(point2);
		mc.setZoom(16);
        
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
