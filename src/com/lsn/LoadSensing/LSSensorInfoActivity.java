package com.lsn.LoadSensing;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lsn.LoadSensing.element.LSSensor;
import com.lsn.LoadSensing.func.LSFunctions;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import greendroid.app.GDActivity;

public class LSSensorInfoActivity extends GDActivity {

	private String sensorSerial = null;
	private LSSensor sensorBundle = null;
	private LSSensor sensorObj = null;
	//private static String idSession;
	private static HashMap<String,Bitmap> hashImages = new HashMap<String,Bitmap>();
	private Bitmap imgSensor;
	private ArrayList<LSSensor>  m_sensors = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.act_sensorinfo);
     
        Bundle bundle = getIntent().getExtras();
        
        
        if (bundle != null)
        {
        	sensorSerial = bundle.getString("SENSOR_SERIAL");
        	sensorBundle = bundle.getParcelable("SENSOR_OBJ");
    	}
        
        m_sensors = new ArrayList<LSSensor>();
        JSONObject jsonData = null;
        JSONArray jArray = null;
        
        if (sensorSerial != null)
        {
        	// Server Request Ini
			Map<String, String> params = new HashMap<String, String>();
			params.put("session", LSHomeActivity.idSession);
			params.put("serialNumber", sensorSerial);
			jArray = LSFunctions.urlRequestJSONArray("http://viuterrassa.com/Android/getSensorInfo.php",params);
        }
        
        if (sensorBundle != null)
        {
        	
	        // Server Request Ini
			Map<String, String> params = new HashMap<String, String>();
			params.put("session", LSHomeActivity.idSession);
			params.put("sensor", sensorBundle.getSensorName());
			jArray = LSFunctions.urlRequestJSONArray("http://viuterrassa.com/Android/getSensorInfo.php",params);
        	
    	}
    	
        try {
			for (int i = 0; i<jArray.length(); i++)
			{
				
				jsonData = jArray.getJSONObject(i);
		
				sensorObj = new LSSensor();
				sensorObj.setSensorId(jsonData.getString("sensor"));
				sensorObj.setSensorSerial(jsonData.getString("serialNumber"));
				sensorObj.setSensorName(jsonData.getString("sensorName"));
				sensorObj.setSensorMeasure(jsonData.getString("measure"), jsonData.getString("measureUnit"));
				sensorObj.setSensorMaxLoad(jsonData.getString("MaxLoad"), jsonData.getString("MaxLoadUnit"));
				sensorObj.setSensorSensitivity(jsonData.getString("Sensivity"), jsonData.getString("SensivityUnit"));
				sensorObj.setSensorOffset(jsonData.getString("offset"), jsonData.getString("offsetUnit"));
				sensorObj.setSensorAlarmAt(jsonData.getString("AlarmAt"), jsonData.getString("AlarmAtUnit"));
				sensorObj.setSensorLastTare(jsonData.getString("LastTare"));
				sensorObj.setSensorChannel(jsonData.getString("canal"));
				sensorObj.setSensorType(jsonData.getString("tipus"));
				String image = jsonData.getString("imatge");
				if (hashImages.containsKey(image))
				{
					imgSensor = hashImages.get(image);
				}
				else
				{
					imgSensor = LSFunctions.getRemoteImage(new URL("http://viuterrassa.com/Android/Imatges/"+image));
					hashImages.put(image, imgSensor);
				}
				sensorObj.setSensorImage(imgSensor);
				sensorObj.setSensorDesc(jsonData.getString("Descripcio"));
				sensorObj.setSensorSituation(jsonData.getString("Poblacio"));
				sensorObj.setSensorNetwork(jsonData.getString("Nom"));
		        m_sensors.add(sensorObj);
			}
			
			// Server Request End  
        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
		TextView txtNetName = (TextView) findViewById(R.id.netName);
		txtNetName.setText(sensorObj.getSensorNetwork());
		TextView txtSensorName = (TextView) findViewById(R.id.sensorName);
		txtSensorName.setText(sensorObj.getSensorName());
		TextView txtSensorSituation = (TextView) findViewById(R.id.sensorSituation);
		txtSensorSituation.setText(sensorObj.getSensorSituation());
        ImageView imageBitmap = (ImageView)findViewById(R.id.imageBitmap);
        imageBitmap.setImageBitmap(sensorObj.getSensorImage());
        TextView txtSensorType = (TextView) findViewById(R.id.sensorType);
        txtSensorType.setText(sensorObj.getSensorType());
        TextView txtSensorChannel = (TextView) findViewById(R.id.sensorChannel);
        txtSensorChannel.setText(sensorObj.getSensorChannel());
        TextView txtSensorDesc = (TextView) findViewById(R.id.sensorDescription);
        txtSensorDesc.setText(sensorObj.getSensorDesc());
        
        TextView txtSensorMeasure = (TextView) findViewById(R.id.sensorMeasure);
        txtSensorMeasure.setText(sensorObj.getSensorMeasure().toString());
        TextView txtSensorMeasureUnit = (TextView) findViewById(R.id.sensorMeasureUnit);
        txtSensorMeasureUnit.setText(sensorObj.getSensorMeasureUnit());
        
        TextView txtSensorMaxLoad = (TextView) findViewById(R.id.sensorMaxLoad);
        txtSensorMaxLoad.setText(sensorObj.getSensorMaxLoad().toString());
        TextView txtSensorMaxLoadUnit = (TextView) findViewById(R.id.sensorMaxLoadUnit);
        txtSensorMaxLoadUnit.setText(sensorObj.getSensorMaxLoadUnit());
        
        TextView txtSensorSensitivity = (TextView) findViewById(R.id.sensorSensitivity);
        txtSensorSensitivity.setText(sensorObj.getSensorSensitivity().toString());
        TextView txtSensorSensitivityUnit = (TextView) findViewById(R.id.sensorSensitivityUnit);
        txtSensorSensitivityUnit.setText(sensorObj.getSensorSensitivityUnit());
        
        TextView txtSensorOffset = (TextView) findViewById(R.id.sensorOffset);
        txtSensorOffset.setText(sensorObj.getSensorOffset().toString());
        TextView txtSensorOffsetUnit = (TextView) findViewById(R.id.sensorOffsetUnit);
        txtSensorOffsetUnit.setText(sensorObj.getSensorOffsetUnit());
        
        TextView txtSensorAlarmAt = (TextView) findViewById(R.id.sensorAlarmAt);
        txtSensorAlarmAt.setText(sensorObj.getSensorAlarmAt().toString());
        TextView txtSensorAlarmAtUnit = (TextView) findViewById(R.id.sensorAlarmAtUnit);
        txtSensorAlarmAtUnit.setText(sensorObj.getSensorAlarmAtUnit());
        
        TextView txtSensorLastTare = (TextView) findViewById(R.id.sensorLastTare);
        txtSensorLastTare.setText(sensorObj.getSensorLastTare().toString());
        
        txtNetName.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				
			}
        	
        });
        
        Button loadChart = (Button)findViewById(R.id.btnLoadChart);
        loadChart.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent i = null;
		        i = new Intent(LSSensorInfoActivity.this,LSSensorChartActivity.class);
		        
		        if (i!=null){
					Bundle bundle = new Bundle();
					
					bundle.putParcelable("SENSOR_OBJ", sensorObj);
					i.putExtras(bundle);
		        	
					startActivity(i);
				}
			}
        	
        	
        });
        
        
//        TextView txtNetName = (TextView) findViewById(R.id.netName);
//        txtNetName.setText(sensorObj.getNetworkName());
//        TextView txtNetSituation = (TextView) findViewById(R.id.netSituation);
//        txtNetSituation.setText(sensorObj.getNetworkSituation());
//        TextView txtNetPosLatitude = (TextView) findViewById(R.id.netPosLatitude);
//        txtNetPosLatitude.setText(sensorObj.getNetworkPosition().getLatitude().toString());
//        TextView txtNetPosLongitude = (TextView) findViewById(R.id.netPosLongitude);
//        txtNetPosLongitude.setText(sensorObj.getNetworkPosition().getLongitude().toString());
//        TextView txtNetPosAltitude = (TextView) findViewById(R.id.netPosAltitude);
//        txtNetPosAltitude.setText(sensorObj.getNetworkPosition().getAltitude().toString());
//        TextView txtNetNumSensors = (TextView) findViewById(R.id.netNumSensors);
//        txtNetNumSensors.setText(sensorObj.getNetworkNumSensors().toString());
        
//        Button btnSensors = (Button) findViewById(R.id.btnLoadSensors);
//        btnSensors.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				
//				Intent i = null;
//		        i = new Intent(LSSensorInfoActivity.this,LSSensorListActivity.class);
//		        
//		        if (i!=null){
//					Bundle bundle = new Bundle();
//					
//					bundle.putString("SESSION", LSHomeActivity.idSession);
//					bundle.putParcelable("SENSOR_OBJ", sensorObj);
//					i.putExtras(bundle);
//		        	
//					startActivity(i);
//				}
//			}
//        	
//        	
//        });
        
//        Button btnImages = (Button) findViewById(R.id.btnLoadImages);
//        btnImages.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				
//				Intent i = null;
//		        i = new Intent(LSSensorInfoActivity.this,LSNetImagesActivity.class);
//		        
//		        if (i!=null){
//					Bundle bundle = new Bundle();
//					
//					bundle.putString("SESSION", LSHomeActivity.idSession);
//					bundle.putParcelable("SENSOR_OBJ", sensorObj);
//					i.putExtras(bundle);
//		        	
//					startActivity(i);
//				}
//			}
//        
//        	
//        });
    }

}