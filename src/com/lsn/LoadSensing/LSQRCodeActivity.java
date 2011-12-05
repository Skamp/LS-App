package com.lsn.LoadSensing;

import com.lsn.LoadSensing.func.LSFunctions;
import com.lsn.LoadSensing.ui.CustomToast;

import greendroid.app.GDActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class LSQRCodeActivity extends GDActivity {
	
	private String intentQRCode = "com.google.zxing.client.android.SCAN";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.act_03_qrcode);
        
        //Check if QRCode reader intent is available
        if (LSFunctions.isIntentAvailable(this, intentQRCode))
        {
	        Intent intent = new Intent(intentQRCode);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent,0);
        }
        else
        {
        	CustomToast.showCustomToast(this,
        			                    R.string.msg_QRIntentError,
        			                    CustomToast.IMG_ERROR,
        			                    CustomToast.LENGTH_LONG);
        	this.finish();
        }
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
