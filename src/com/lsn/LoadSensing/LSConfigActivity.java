package com.lsn.LoadSensing;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class LSConfigActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.maps); 
    }
}