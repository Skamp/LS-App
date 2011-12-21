package com.lsn.LoadSensing;

import com.lsn.LoadSensing.func.LSFunctions;
import com.lsn.LoadSensing.ui.CustomToast;

import greendroid.app.GDActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


public class LSQRCodeActivity extends GDActivity {
	
	private static boolean fromQRReader = false;
	
	private String intentQRCode = "com.google.zxing.client.android.SCAN";
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
	
	@Override
	protected void onResume() {
		
		super.onResume();
		if (!fromQRReader)
		{
			fromQRReader= true;
			//Check if QRCode reader intent is available
	        if (LSFunctions.isIntentAvailable(this, intentQRCode))
	        {
	        	//Start QRCode reader intent
		        Intent intent = new Intent(intentQRCode);
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent,0);
	        }
	        else
	        {
	        	//Show error message
	        	CustomToast.showCustomToast(this,
	        			                    R.string.msg_QRIntentError,
	        			                    CustomToast.IMG_ERROR,
	        			                    CustomToast.LENGTH_LONG);
	        	try
        		{
	        		//Access Android Market to install Google Googles
        			Intent i = new Intent();
        			i.setAction(Intent.ACTION_VIEW);
        			i.setData(Uri.parse("market://search?q=pname:com.google.android.apps.unveil"));
        			startActivity(i); 
                }
        		catch (Exception ex)
        		{
        			CustomToast.showCustomToast(this,
		                    R.string.msg_ErrMarketAccess,
		                    CustomToast.IMG_ERROR,
		                    CustomToast.LENGTH_LONG);
        		}
	        	
	        	this.finish();
	        }
		}
		else
		{
			fromQRReader=false;
			onBackPressed();
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

				//Check detected code is QRCode
				if (format.equals("QR_CODE"))
				{
					Intent i = null;
			        i = new Intent(LSQRCodeActivity.this,LSSensorInfoActivity.class);
			        
			        if (i!=null){
						Bundle bundle = new Bundle();
						//Inform bundle information with the id of sensor detected
						bundle.putString("SENSOR_SERIAL", contents);
						
						i.putExtras(bundle);
	
						//Display detected sensor
						String strQRCorrectFormat = getResources().getString(R.string.msg_QRCorrect);
					    String strQRCorrect = String.format(strQRCorrectFormat, contents);  
						CustomToast.showCustomToast(this,
			                    strQRCorrect,
			                    CustomToast.IMG_CORRECT,
			                    CustomToast.LENGTH_LONG);

						//Start activity showing the information of the sensor detected						
						startActivity(i);
					}
				}
				else
				{
					//Display error if code is not QRCode 
					CustomToast.showCustomToast(this,
		                    R.string.msg_QRFormatError,
		                    CustomToast.IMG_ERROR,
		                    CustomToast.LENGTH_LONG);
					
					fromQRReader=false;
					onBackPressed();
				}
				
			}
			else if (resultCode == RESULT_CANCELED)
			{
				// Go to Home activity if action is cancelled
				fromQRReader=false;
				onBackPressed();
			}
		}
	}
	
}
