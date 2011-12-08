package com.lsn.LoadSensing.map;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlayForge;

public class LSNetworksOverlayForge extends BalloonItemizedOverlayForge<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Context c;
	
	public LSNetworksOverlayForge(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		c = mapView.getContext();
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
		return true;
	}

}
