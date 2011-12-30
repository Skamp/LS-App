package com.lsn.LoadSensing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import greendroid.widget.ActionBar;
import greendroid.widget.ActionBar.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.android.maps.ArrayCircleOverlay;
import org.mapsforge.android.maps.ArrayItemizedOverlay;
import org.mapsforge.android.maps.ArrayWayOverlay;
import org.mapsforge.android.maps.GeoPoint;
import org.mapsforge.android.maps.ItemizedOverlay;
import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.MapViewMode;
import org.mapsforge.android.maps.Overlay;
import org.mapsforge.android.maps.OverlayCircle;
import org.mapsforge.android.maps.OverlayItem;
import org.mapsforge.android.maps.OverlayWay;

import com.lsn.LoadSensing.element.LSNetwork;
import com.lsn.LoadSensing.func.LSFunctions;
import com.lsn.LoadSensing.map.LSNetworksOverlay;
import com.lsn.LoadSensing.mapsforge.LSNetworksOverlayForge;
import com.readystatesoftware.mapviewballoons.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;


public class LSNetMapsForgeActivity extends MapActivity {
	
	private ArrayList<LSNetwork> m_networks = null;
	private List<Overlay>     mapOverlays;
	private MapView mapView;
	private LSNetworksOverlayForge itemizedOverlay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_02_netmapsforge);

		ActionBar actBar = (ActionBar)findViewById(R.id.gd_action_bar);
		actBar.setTitle(getResources().getString(R.string.act_lbl_homNetMaps));
		actBar.setType(Type.Normal);
		ImageButton	mHomeButton = (ImageButton) findViewById(R.id.gd_action_bar_home_item);
		
		mHomeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				Intent i = new Intent(LSNetMapsForgeActivity.this,LSHomeActivity.class);
				startActivity(i);
			}
		});
		
		//MapView mapView = (MapView)findViewById(R.id.mapView);
		mapView = (MapView)findViewById(R.id.mapView);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		mapView.setMapViewMode(MapViewMode.MAPNIK_TILE_DOWNLOAD);
		
		
		mapOverlays = mapView.getOverlays();
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
			
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		// create some points to be used in the different overlays
//        GeoPoint geoPoint1 = new GeoPoint(51.5174723, -0.0899537); 
//        GeoPoint geoPoint2 = new GeoPoint(51.515259, -0.086623); 
//        GeoPoint geoPoint3 = new GeoPoint(51.513329, -0.08896); 
//        GeoPoint geoPoint4 = new GeoPoint(51.51738, -0.08186); 
		
     // create the default paint objects for overlay circles
//        Paint circleDefaultPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
//        circleDefaultPaintFill.setStyle(Paint.Style.FILL);
//        circleDefaultPaintFill.setColor(Color.BLUE);
//        circleDefaultPaintFill.setAlpha(64);
//
//        Paint circleDefaultPaintOutline = new Paint(Paint.ANTI_ALIAS_FLAG);
//        circleDefaultPaintOutline.setStyle(Paint.Style.STROKE);
//        circleDefaultPaintOutline.setColor(Color.BLUE);
//        circleDefaultPaintOutline.setAlpha(128);
//        circleDefaultPaintOutline.setStrokeWidth(3);

        // create an individual paint object for an overlay circle
//        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        circlePaint.setStyle(Paint.Style.FILL);
//        circlePaint.setColor(Color.MAGENTA);
//        circlePaint.setAlpha(96);

        // create the CircleOverlay and add the circles
//        ArrayCircleOverlay circleOverlay = new ArrayCircleOverlay(circleDefaultPaintFill,
//                        circleDefaultPaintOutline,this);
//        OverlayCircle circle1 = new OverlayCircle(geoPoint3, 200, null);
//        OverlayCircle circle2 = new OverlayCircle(geoPoint4, 150, circlePaint, null, null);
//        circleOverlay.addCircle(circle1);
//        circleOverlay.addCircle(circle2);
        
//        // create the default paint objects for overlay ways
//        Paint wayDefaultPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
//        wayDefaultPaintFill.setStyle(Paint.Style.STROKE);
//        wayDefaultPaintFill.setColor(Color.BLUE);
//        wayDefaultPaintFill.setAlpha(160);
//        wayDefaultPaintFill.setStrokeWidth(7);
//        wayDefaultPaintFill.setStrokeJoin(Paint.Join.ROUND);
//        wayDefaultPaintFill.setPathEffect(new DashPathEffect(new float[] { 20, 20 }, 0));
//
//        Paint wayDefaultPaintOutline = new Paint(Paint.ANTI_ALIAS_FLAG);
//        wayDefaultPaintOutline.setStyle(Paint.Style.STROKE);
//        wayDefaultPaintOutline.setColor(Color.BLUE);
//        wayDefaultPaintOutline.setAlpha(128);
//        wayDefaultPaintOutline.setStrokeWidth(7);
//        wayDefaultPaintOutline.setStrokeJoin(Paint.Join.ROUND);

        // create an individual paint object for an overlay way
//        Paint wayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        wayPaint.setStyle(Paint.Style.FILL);
//        wayPaint.setColor(Color.YELLOW);
//        wayPaint.setAlpha(192);
        
//     // create the WayOverlay and add the ways
//        ArrayWayOverlay wayOverlay = new ArrayWayOverlay(wayDefaultPaintFill,
//                        wayDefaultPaintOutline);
//        OverlayWay way1 = new OverlayWay(new GeoPoint[][] { { geoPoint1, geoPoint2 } });
//        OverlayWay way2 = new OverlayWay(new GeoPoint[][] { { geoPoint1, geoPoint3, geoPoint4,
//                        geoPoint1 } }, wayPaint, null);
//        wayOverlay.addWay(way1);
//        wayOverlay.addWay(way2);

        // create the default marker for overlay items
        Drawable itemDefaultMarker = getResources().getDrawable(R.drawable.marker);

        // create an individual marker for an overlay item
        Drawable itemMarker = getResources().getDrawable(R.drawable.marker2);

        // create the ItemizedOverlay and add the items
        //ArrayItemizedOverlay itemizedOverlay = new MyItemizedOverlay(mapView,itemDefaultMarker, this);
        //LSNetworksOverlayForge itemizedOverlay = new LSNetworksOverlayForge(itemDefaultMarker, mapView,m_networks);
        itemizedOverlay = new LSNetworksOverlayForge(itemDefaultMarker, mapView,m_networks);
        
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
        
//        OverlayItem item1 = new OverlayItem(geoPoint1, "Network 1",
//        		"(Network 1 description)");
//        OverlayItem item2 = new OverlayItem(geoPoint2, "Network 2",
//        		"(Network 2 description)");
//        OverlayItem item3 = new OverlayItem(geoPoint3, "Network 3",
//        		"(Network 3 description)",
//                ItemizedOverlay.boundCenterBottom(itemMarker));
//        OverlayItem item4 = new OverlayItem(geoPoint4, "Network 4",
//        		"(Network 4 description)",
//                ItemizedOverlay.boundCenterBottom(itemMarker));
//        itemizedOverlay.addOverlay(item1);
//        itemizedOverlay.addOverlay(item2);
//        itemizedOverlay.addOverlay(item3);
//        itemizedOverlay.addOverlay(item4);
        
        
        
//        Drawable drawable = getResources().getDrawable(R.drawable.marker);
//        LSNetworksOverlayForge itemizedOverlay = new LSNetworksOverlayForge(drawable, mapView);
//		
//		
//		OverlayItem overlayItem = new OverlayItem(geoPoint1, "Network 1", 
//				"(Network 1 description)");
//		itemizedOverlay.addOverlay(overlayItem);
//		
//		OverlayItem overlayItem2 = new OverlayItem(geoPoint2, "Network 2", 
//				"(Network 2 description)");		
//		itemizedOverlay.addOverlay(overlayItem2);
		
        
        
        
        
        
        // add all overlays to the MapView
//        mapView.getOverlays().add(wayOverlay);
//        mapView.getOverlays().add(circleOverlay);
        //mapView.getOverlays().add(itemizedOverlay);
        
		point = new GeoPoint((int)(40.416369*1E6),(int)(-3.702992*1E6));
        MapController mMapController = mapView.getController();
        mMapController.setCenter(point);
        mMapController.setZoom(5);
        
		//isCentered = true;
		
	}

		private class MyItemizedOverlay extends ArrayItemizedOverlay {
        private final Context context;
        private final MapView mapView;

        
        private LinearLayout layout;
        private TextView title;
        private TextView snippet;
        
        /**
         * Constructs a new MyItemizedOverlay.
         * 
         * @param defaultMarker
         *            the default marker (may be null).
         * @param context
         *            the reference to the application context.
         */
        MyItemizedOverlay(MapView mapView, Drawable defaultMarker, Context context) {
                super(defaultMarker,context);
                this.context = context;
                this.mapView = mapView;
        }

        /**
         * Handles a tap event on the given item.
         */
        @Override
        protected boolean onTap(int index) {
                OverlayItem item = createItem(index);
                if (item != null) {
//                        Builder builder = new AlertDialog.Builder(this.context);
//                        builder.setIcon(android.R.drawable.ic_menu_info_details);
//                        builder.setTitle(item.getTitle());
//                        builder.setMessage(item.getSnippet());
//                        builder.setPositiveButton("OK", null);
//                        builder.show();
                        MapController mMapController = mapView.getController();
                        mMapController.setCenter(item.getPoint());
                        
                        layout = new LinearLayout(context);
                		layout.setVisibility(View.VISIBLE);

                		LayoutInflater inflater = (LayoutInflater) context
                				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                		View v = inflater.inflate(R.layout.balloon_overlay, layout);
                		title = (TextView) v.findViewById(R.id.balloon_item_title);
                		snippet = (TextView) v.findViewById(R.id.balloon_item_snippet);

                		layout.setVisibility(View.VISIBLE);
                		if (item.getTitle() != null) {
                			title.setVisibility(View.VISIBLE);
                			title.setText(item.getTitle());
                		} else {
                			title.setVisibility(View.GONE);
                		}
                		if (item.getSnippet() != null) {
                			snippet.setVisibility(View.VISIBLE);
                			snippet.setText(item.getSnippet());
                		} else {
                			snippet.setVisibility(View.GONE);
                		}
                		                		
                		ImageView close = (ImageView) v.findViewById(R.id.close_img_button);
                		close.setOnClickListener(new OnClickListener() {
                			public void onClick(View v) {
                				layout.setVisibility(View.GONE);
                			}
                		});

                		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                		params.gravity = Gravity.NO_GRAVITY;
                		params.topMargin = mapView.getHeight()/2-layout.getHeight();
                		
                		((ViewGroup) mapView.getParent()).addView(layout, params);
                        
                }
                return true;
        }
        
}
	
	
}
