package com.lsn.LoadSensing;

import android.os.Bundle;
import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem.Type;

public class LSConfigActivity extends GDActivity {

private final int HELP = 1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.main);
        
        initActionBar();
    }
	 private void initActionBar() {
		
    	addActionBarItem(Type.Help,HELP);
	}
	
}
