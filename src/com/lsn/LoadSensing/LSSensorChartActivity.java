package com.lsn.LoadSensing;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.lsn.LoadSensing.element.LSSensor;
import com.lsn.LoadSensing.func.LSFunctions;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.widget.LinearLayout;
import greendroid.app.GDActivity;

public class LSSensorChartActivity extends GDActivity {

	private String sensorSerial = null;
	private LSSensor sensorBundle = null;
	private ArrayList<LSSensor>  m_sensors = null;
	private static HashMap<String,Double> hashValues = new HashMap<String,Double>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.act_sensorchart);
        
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
			jArray = LSFunctions.urlRequestJSONArray("http://viuterrassa.com/Android/getValorsGrafic.php",params);
        }
        
        if (sensorBundle != null)
        {
        	
	        // Server Request Ini
			Map<String, String> params = new HashMap<String, String>();
			params.put("session", LSHomeActivity.idSession);
			params.put("sensor", sensorBundle.getSensorId());
			jArray = LSFunctions.urlRequestJSONArray("http://viuterrassa.com/Android/getValorsGrafic.php",params);
        	
    	}
        
        try {
			jsonData = jArray.getJSONObject(0);
			jArray = jsonData.getJSONArray("ValorsGrafica");
        } catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        int num = jArray.length();
        GraphViewData[] data = new GraphViewData[num];
        String[] strKeys = new String[num];
        try {
        	
        	
			
			
			for (int i = 0; i<jArray.length(); i++)
			{
				jsonData = jArray.getJSONObject(i);
				hashValues.put(jsonData.getString("date"), jsonData.getDouble("value"));
				data[i] = new GraphViewData(i+1,jsonData.getDouble("value"));
		
				Format formatter = new SimpleDateFormat("yyyy-MM-dd");
	        	Date date = null;
				try {
					date = ((DateFormat) formatter).parse(jsonData.getString("date"));
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
	        	
				formatter = new SimpleDateFormat("dd/MM");
	        	strKeys[i] = formatter.format(date);
			}
			
			// Server Request End  
        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        GraphView graphView = new BarGraphView(this,sensorBundle.getSensorName())
        {

			
			
			@Override
			public void drawSeries(Canvas canvas, GraphViewData[] values, float graphwidth, float graphheight,
					float border, double minX, double minY, double diffX, double diffY,
					float horstart) {
				//super.drawSeries(canvas,values,graphwidth, graphheight ,border, minX, minY,diffX, diffY,horstart);
				//float colwidth = (graphwidth - (2 * border)) / values.length;
				float colwidth = (graphwidth) / values.length;

				
				//paint.setColor(Color.WHITE);
				paint.setColor(Color.parseColor("#bcd9f2"));
				paint.setTextAlign(Align.CENTER);
				
				// draw data
				for (int i = 0; i < values.length; i++) {
					float valY = (float) (values[i].valueY - minY);
					float ratY = (float) (valY / diffY);
					float y = graphheight * ratY;
					canvas.drawRect((i * colwidth) + horstart, (border - y) + graphheight, ((i * colwidth) + horstart) + (colwidth - 1), graphheight + border - 1, paint);
				}
			}
			
        };

        
        graphView.addSeries(new GraphViewSeries(data));
        graphView.setViewPort(2, 40);
        graphView.setScrollable(false);
        graphView.setScalable(false);
        
        LinearLayout chartLayout = (LinearLayout)findViewById(R.id.sensorChart);
        
//        Set<String> hashKeys = hashValues.keySet();
//        String[] strKeys = (String[])hashKeys.toArray(new String[hashKeys.size()]);
//        
//        for (int i = 0; i<strKeys.length; i++)
//		{
//        	Format formatter = new SimpleDateFormat("yyyy-MM-dd");
//        	//Date date = new Date(strKeys[i]);
//        	Date date = null;
//			try {
//				date = ((DateFormat) formatter).parse(strKeys[i]);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        	
//			formatter = new SimpleDateFormat("dd/MM");
//        	strKeys[i] = formatter.format(date);
//		}
        
        graphView.setHorizontalLabels(strKeys);

        //graphView.setBackgroundColor(Color.parseColor("#174a76"));
        graphView.setBackgroundColor(Color.parseColor("#287fcb"));
        
        		//new Color(#174a76));
        
        
        chartLayout.addView(graphView);
        
        
	}
}
