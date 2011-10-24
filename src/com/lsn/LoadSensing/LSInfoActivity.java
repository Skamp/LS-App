package com.lsn.LoadSensing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import greendroid.app.ActionBarActivity;
import greendroid.app.GDTabActivity;

public class LSInfoActivity extends GDTabActivity {

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        final String aboutText =  getString(R.string.infoTabAbout);
	        final Intent aboutIntent = new Intent(this, LSAboutActivity.class);
	        aboutIntent.putExtra(ActionBarActivity.GD_ACTION_BAR_VISIBILITY, View.GONE);
	        addTab(aboutText, aboutText, aboutIntent);

	        final String licenseText =  getString(R.string.infoTabLicense);
	        final Intent licenseIntent = new Intent(this, LSLicenseActivity.class);
	        licenseIntent.putExtra(ActionBarActivity.GD_ACTION_BAR_VISIBILITY, View.GONE);
	        licenseIntent.putExtra(LSLicenseActivity.EXTRA_CONTENT_URL, "file:///android_asset/LICENSE.txt");
	        addTab(licenseText, licenseText, licenseIntent);
	    }
	    
	    @Override
	    public int createLayout() {
	        return R.layout.info;
	    }

	    public void onAppUrlClicked(View v) {
	        final Uri appUri = Uri.parse(getString(R.string.app_url));
	        startActivity(new Intent(Intent.ACTION_VIEW, appUri));
	    }
}
