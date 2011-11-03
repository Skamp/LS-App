package com.lsn.LoadSensing;

import greendroid.app.GDActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class LSQRCodeActivity extends GDActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.act_03_qrcode);
        
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent,0);
    }
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (requestCode==0)
		{
			if (resultCode == RESULT_OK)
			{
				//Handle successful scan
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				TextView txtResult = (TextView) findViewById(R.id.txtResultValue);
				TextView txtResultFormat = (TextView) findViewById(R.id.txtResultFormat);
				txtResult.setText(contents);
				txtResultFormat.setText(format);
			}
			else if (resultCode == RESULT_CANCELED)
			{
				//Handle cancel
			}
		}
	}
	
	
	
	
}
