package com.lsn.LoadSensing.mapsforge;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

import org.mapsforge.android.maps.GeoPoint;
import org.mapsforge.android.maps.ItemizedOverlay;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.Overlay;
//import org.mapsforge.android.maps.Overlay.EventType;
import org.mapsforge.android.maps.OverlayItem;

import com.lsn.LoadSensing.LSNetInfoActivity;
import com.lsn.LoadSensing.element.LSNetwork;
import com.readystatesoftware.mapviewballoons.R;

public class LSNetworksOverlayForge extends ItemizedOverlay<OverlayItem> {
//public class LSNetworksOverlayForge<Item extends OverlayItem> extends ItemizedOverlay<Item> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Context c;
	private OverlayItem currentFocussedItem;
	private View clickRegion;
	private int currentFocussedIndex;
	private int viewOffset;
	private MapView mapView;
	private MapController mc;
	private BalloonOverlayViewForge<OverlayItem> balloonView;
	private LinearLayout layout;
    private TextView title;
    private TextView snippet;
    private ArrayList<LSNetwork> m_networks;
	
	public LSNetworksOverlayForge(Drawable defaultMarker, MapView mapView,ArrayList<LSNetwork> networks) {
		super(boundCenter(defaultMarker));
		this.c = mapView.getContext();
		this.mc = mapView.getController();
		this.mapView = mapView;
		this.m_networks = networks;
	}

//	@Override
//	protected boolean checkItemHit(GeoPoint geoPoint, MapView mapView,
//			EventType eventType) {
//		// TODO Auto-generated method stub
//		((ViewGroup) mapView.getParent()).removeView(layout);
//		return super.checkItemHit(geoPoint, mapView, eventType);
//	}

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
	protected final boolean onTap(int index) {
		
		currentFocussedIndex = index;
		currentFocussedItem = createItem(index);
		//OverlayItem item = createItem(index);
		
		List<Overlay> mapOverlays = mapView.getOverlays();
		if (mapOverlays.size() >= 1) {
			hideOtherBalloons(mapOverlays);
		}
		
		((ViewGroup) mapView.getParent()).removeView(layout);
		
		MapController mMapController = mapView.getController();
        //mMapController.setCenter(item.getPoint());
        mMapController.setCenter(currentFocussedItem.getPoint());
        
        layout = new LinearLayout(c);
		layout.setVisibility(View.VISIBLE);

		LayoutInflater inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.balloon_overlay, layout);
		
		title = (TextView) v.findViewById(R.id.balloon_item_title);
		snippet = (TextView) v.findViewById(R.id.balloon_item_snippet);

		layout.setVisibility(View.VISIBLE);
		//if (item.getTitle() != null) {
		if (currentFocussedItem.getTitle() != null) {
			title.setVisibility(View.VISIBLE);
			//title.setText(item.getTitle());
			title.setText(currentFocussedItem.getTitle());
		} else {
			title.setVisibility(View.GONE);
		}
		//if (item.getSnippet() != null) {
		if (currentFocussedItem.getSnippet() != null) {
			snippet.setVisibility(View.VISIBLE);
			//snippet.setText(item.getSnippet());
			snippet.setText(currentFocussedItem.getSnippet());
		} else {
			snippet.setVisibility(View.GONE);
		}
		                		
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//params.gravity = Gravity.NO_GRAVITY;
		params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
		
		////Point screenPoint = new Point();
		////mapView.getProjection().toPixels(geoPoint, screenPoint);
		
		//WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
		//Display display = wm.getDefaultDisplay();
		//params.topMargin -= 150;
		
		//params.topMargin = display.getHeight()/2;
		layout.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		params.topMargin -= layout.getMeasuredHeight();
		//params.topMargin = mapView.getHeight()/2-layout.getMeasuredHeight();
		//params.leftMargin = (mapView.getWidth()/2)-(layout.getMeasuredWidth()/2);
		
		ImageView imgClose = (ImageView)layout.findViewById(R.id.close_img_button);
		imgClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				layout.setVisibility(View.GONE);
				//List<Overlay> mapOverlays = mapView.getOverlays();
				//if (mapOverlays.size() >= 1) {
				//	hideOtherBalloons(mapOverlays);
				//}
				((ViewGroup) mapView.getParent()).removeView(layout);
			}
			
		});
		
		clickRegion = layout.findViewById(R.id.balloon_inner_layout);
		clickRegion.setOnTouchListener(createBalloonTouchListener());
		
		((ViewGroup) mapView.getParent()).addView(layout, params);
		
		//mapOverlays.add(layout);
		//this.addOverlay(currentFocussedItem);
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.mapsforge.android.maps.ItemizedOverlay#onTap(org.mapsforge.android.maps.GeoPoint, org.mapsforge.android.maps.MapView)
	 */
	
//	@Override
//	public boolean onTap(GeoPoint geoPoint, MapView mapView) {
//		
//		//super.onTap(geoPoint, mapView);
//		Point screenPoint = new Point();
//		mapView.getProjection().toPixels(geoPoint, screenPoint);
//				
//		return true;
//	}

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

	protected void hideBalloon() {
		if (balloonView != null) {
			balloonView.setVisibility(View.GONE);
		}
	}
	
	private void hideOtherBalloons(List<Overlay> overlays) {
		
		for (Overlay overlay : overlays) {
			if (overlay instanceof LSNetworksOverlayForge && overlay != this) {
				((LSNetworksOverlayForge) overlay).hideBalloon();
				
			}
		}
		
	}

	private boolean createAndDisplayBalloonOverlay(){
		boolean isRecycled;
		if (balloonView == null) {
			balloonView = createBalloonOverlayView();
			clickRegion = (View) balloonView.findViewById(R.id.balloon_inner_layout);
			clickRegion.setOnTouchListener(createBalloonTouchListener());
			isRecycled = false;
		} else {
			isRecycled = true;
		}
	
		balloonView.setVisibility(View.GONE);
		
		List<Overlay> mapOverlays = mapView.getOverlays();
		if (mapOverlays.size() > 1) {
			hideOtherBalloons(mapOverlays);
			
		}
		
		if (currentFocussedItem != null)
			balloonView.setData(currentFocussedItem);
		
		GeoPoint point = currentFocussedItem.getPoint();
		//MapView.LayoutParams params = new MapView.LayoutParams(
		//		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, point,
		//		MapView.LayoutParams.BOTTOM_CENTER);
		//params.mode = MapView.LayoutParams.MODE_MAP;
		//MapView.LayoutParams params = new MapView.LayoutParams(
				//LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//		LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		        
		mc.setCenter(point);
		
		
		balloonView.setVisibility(View.VISIBLE);
		
		if (isRecycled) {
			balloonView.setLayoutParams(params);
		} else {
			mapView.addView(balloonView, params);
			//mapView.addView(balloonView,new ViewGroup.LayoutParams(
                    //ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			
			//((ViewGroup) this.mapView.getRootView()).addView(balloonView,params);
			//((ViewGroup) this.mapView.getParent()).addView(balloonView,params);
			//setBalloonBottomOffset(0);
			//balloonView.offsetTopAndBottom(100);
			//balloonView.layout(100, 100, 100, 100);
			//balloonView.scrollTo(mapView.idth()/2, mapView.getHeight()/2);
			//balloonView.scrollTo(-50, -50);
			
			
			//balloonView.bringToFront();
		}
		
		return isRecycled;
	}
	
	private OnTouchListener createBalloonTouchListener() {
		return new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				
				View l =  ((View) v.getParent()).findViewById(R.id.balloon_main_layout);
				//View l =  v.findViewById(R.id.balloon_main_layout);
				Drawable d = l.getBackground();
				
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					int[] states = {android.R.attr.state_pressed};
					if (d.setState(states)) {
						d.invalidateSelf();
					}
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					int newStates[] = {};
					if (d.setState(newStates)) {
						d.invalidateSelf();
					}
					// call overridden method
					onBalloonTap(currentFocussedIndex, currentFocussedItem);
					return true;
				} else {
					return false;
				}
				
			}
		};
	}
	
	protected BalloonOverlayViewForge<OverlayItem> createBalloonOverlayView() {
		return new BalloonOverlayViewForge<OverlayItem>(getMapView().getContext(), getBalloonBottomOffset());
	}
	
	protected MapView getMapView() {
		return mapView;
	}
	
	public void setBalloonBottomOffset(int pixels) {
		viewOffset = pixels;
	}
	public int getBalloonBottomOffset() {
		return viewOffset;
	}
	
	public class BalloonOverlayViewForge<Item extends OverlayItem> extends FrameLayout {

		private LinearLayout layout;
		private TextView title;
		private TextView snippet;

		/**
		 * Create a new BalloonOverlayView.
		 * 
		 * @param context - The activity context.
		 * @param balloonBottomOffset - The bottom padding (in pixels) to be applied
		 * when rendering this view.
		 */
		public BalloonOverlayViewForge(Context context, int balloonBottomOffset) {

			super(context);

			setPadding(10, 0, 10, balloonBottomOffset);
			layout = new LinearLayout(context);
			layout.setVisibility(VISIBLE);

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(R.layout.balloon_overlay, layout);
			title = (TextView) v.findViewById(R.id.balloon_item_title);
			snippet = (TextView) v.findViewById(R.id.balloon_item_snippet);

			ImageView close = (ImageView) v.findViewById(R.id.close_img_button);
			close.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					layout.setVisibility(GONE);
				}
			});

			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.NO_GRAVITY;

			addView(layout, params);
		}
		
		/**
		 * Sets the view data from a given overlay item.
		 * 
		 * @param item - The overlay item containing the relevant view data 
		 * (title and snippet). 
		 */
		public void setData(Item item) {
			
			layout.setVisibility(VISIBLE);
			if (item.getTitle() != null) {
				title.setVisibility(VISIBLE);
				title.setText(item.getTitle());
			} else {
				title.setVisibility(GONE);
			}
			if (item.getSnippet() != null) {
				snippet.setVisibility(VISIBLE);
				snippet.setText(item.getSnippet());
			} else {
				snippet.setVisibility(GONE);
			}
			
		}

	}
}


