package com.lsn.LoadSensing.faves;

import java.util.ArrayList;

import com.lsn.LoadSensing.R;
import com.lsn.LoadSensing.adapter.LSSensorAdapter;
import com.lsn.LoadSensing.element.LSSensor;

import greendroid.app.GDListActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class LSFavesSensorsActivity extends GDListActivity{
	
	private ProgressDialog       m_ProgressDialog = null;
	private ArrayList<LSSensor> m_sensors = null;
	private LSSensorAdapter       m_adapter;
	private Runnable             viewSensors;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_04_favessensors);
	  
		m_sensors = new ArrayList<LSSensor>();
        this.m_adapter = new LSSensorAdapter(this,R.layout.row_list_network,m_sensors);
        setListAdapter(this.m_adapter);
        
        viewSensors = new Runnable()
        {

			@Override
			public void run() {
				getSensors();
			}
        };
        Thread thread = new Thread(null,viewSensors,"ViewSensors");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(LSFavesSensorsActivity.this, "Please wait...", "Retrieving data...", true);
		
//		ItemAdapter adapter = new ItemAdapter(this);
//		adapter.add(createTextItem(0,"Sensor 1"));
//		adapter.add(createTextItem(1,"Sensor 2"));
//		adapter.add(createTextItem(2,"Sensor 3"));
//		adapter.add(createTextItem(3,"Sensor 4"));
//		adapter.add(createTextItem(4,"Sensor 5"));
//		adapter.add(createTextItem(0,"Sensor 6"));
//		adapter.add(createTextItem(1,"Sensor 7"));
//		adapter.add(createTextItem(2,"Sensor 8"));
//		adapter.add(createTextItem(3,"Sensor 9"));
//		adapter.add(createTextItem(4,"Sensor 10"));
//		setListAdapter(adapter);
	}
	
	private Runnable returnRes = new Runnable() {

    	@Override
    	public void run() {
    		if(m_sensors != null && m_sensors.size() > 0){
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_sensors.size();i++)
                m_adapter.add(m_sensors.get(i));
            }
            m_ProgressDialog.hide();
            m_adapter.notifyDataSetChanged();
    	}
    };
	
	private void getSensors() {

		try{
          m_sensors = new ArrayList<LSSensor>();
          LSSensor o1 = new LSSensor();
          o1.setSensorName("Sensor 1");
          o1.setSensorSituation("lat. XX.XX lon. YY.YY");
          o1.setSensorNetwork("Network 1");
          LSSensor o2 = new LSSensor();
          o2.setSensorName("Sensor 2");
          o2.setSensorSituation("lat. XX.XX lon. YY.YY");
          o2.setSensorNetwork("Network 2");
          LSSensor o3 = new LSSensor();
          o3.setSensorName("Sensor 3");
          o3.setSensorSituation("lat. XX.XX lon. YY.YY");
          o3.setSensorNetwork("Network 3");
          m_sensors.add(o1);
          m_sensors.add(o2);
          m_sensors.add(o3);
          Thread.sleep(1000);
          Log.i("ARRAY", ""+ m_sensors.size());
        } catch (Exception e) { 
          Log.e("BACKGROUND_PROC", e.getMessage());
        }
        runOnUiThread(returnRes);		
	}

	
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
        Toast.makeText(getApplicationContext(), "Has pulsado la posición " + position +", Item " + m_adapter.getSensorName(position), Toast.LENGTH_LONG).show();
    }
	
	
//	private TextItem createTextItem(int stringId, String strItem) {
//		final TextItem textItem = new TextItem(strItem);
//        textItem.setTag(strItem);
//        return textItem;
//    }

}
