package com.lsn.LoadSensing;

import android.content.Intent;
import greendroid.app.GDApplication;

public class LSApplication extends GDApplication {

	@Override
	public Class<?> getHomeActivityClass() {

		return LSHomeActivity.class;
	}

	@Override
	public Intent getMainApplicationIntent() {

		return new Intent(Intent.ACTION_DEFAULT);
	}

	
	
}
