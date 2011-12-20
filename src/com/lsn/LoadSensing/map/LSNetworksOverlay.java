package com.lsn.LoadSensing.map;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.lsn.LoadSensing.LSHomeActivity;
import com.lsn.LoadSensing.LSNetInfoActivity;
import com.lsn.LoadSensing.LSNetListActivity;
import com.lsn.LoadSensing.element.LSNetwork;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;

public class LSNetworksOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Context c;
	private ArrayList<LSNetwork> m_networks;
	
	public LSNetworksOverlay(Drawable defaultMarker, MapView mapView,ArrayList<LSNetwork> networks) {
		super(boundCenter(defaultMarker), mapView);
		c = mapView.getContext();
		m_networks = networks;
	}

	public void addOverlay(OverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
		Toast.makeText(c, "onBalloonTap for overlay index " + index + "item is " + item.getTitle(),
				Toast.LENGTH_LONG).show();
		Intent i = null;
        i = new Intent(c,LSNetInfoActivity.class);
        
        if (i!=null){
			Bundle bundle = new Bundle();
			
			//bundle.putString("SESSION", LSHomeActivity.idSession);
			bundle.putParcelable("NETWORK_OBJ", m_networks.get(index));
			
			i.putExtras(bundle);

			c.startActivity(i);
		}
		return true;
	}
	
}
