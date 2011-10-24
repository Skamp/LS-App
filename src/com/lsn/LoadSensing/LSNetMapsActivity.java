package com.lsn.LoadSensing;

import com.google.android.maps.MapView;

import greendroid.app.GDMapActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;
import android.os.Bundle;
import android.widget.Toast;

public class LSNetMapsActivity extends GDMapActivity {

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
        setActionBarContentView(R.layout.netmap);
        
        initActionBar();
        initQuickActionBar();
        
        final MapView mapView = (MapView) findViewById(R.id.netmap);
        mapView.setBuiltInZoomControls(true);
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
 
	private void initQuickActionBar()
	{
		quickActions = new QuickActionBar(this);
		quickActions.addQuickAction(new QuickAction(this,R.drawable.find,"Cercar"));
		quickActions.addQuickAction(new QuickAction(this,R.drawable.filter,"Filtrar"));
		quickActions.setOnQuickActionClickListener(new OnQuickActionClickListener() {

			@Override
			public void onQuickActionClicked(QuickActionWidget widget, int position) {
				Toast.makeText(LSNetMapsActivity.this, "Item " + position + " pulsado", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
}
