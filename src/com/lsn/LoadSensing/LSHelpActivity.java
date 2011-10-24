package com.lsn.LoadSensing;

import android.os.Bundle;
import android.widget.Toast;
import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.NormalActionBarItem;

public class LSHelpActivity extends GDActivity {
	
	private final int BACK = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.main);
        
        initActionBar();
    }
	
	private void initActionBar() {
		
    	addActionBarItem(getActionBar()
				.newActionBarItem(NormalActionBarItem.class)
				.setDrawable(R.drawable.ic_mnu_back)
				.setContentDescription("Back"), BACK);
	}
	
	 @Override
		public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
			
	    	
	    	
	    	switch (item.getItemId()) {
	    	
	    	case BACK:
	    		Toast.makeText(getApplicationContext(), "Has pulsado el boton BACK", Toast.LENGTH_SHORT).show();
	    	
	    		break;
	    	
	    	default:
	    		return super.onHandleActionBarItemClick(item, position);
	    	}
	    	
			return true;
		} 
	
}

//.setDrawable(new ActionBarDrawable(getResources(),R.drawable.map)),
//.setContentDescription("Direction"), R.id.action_bar_direction);
