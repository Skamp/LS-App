package com.lsn.LoadSensing;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;



import com.lsn.LoadSensing.adapter.LSSensorAdapter;
import com.lsn.LoadSensing.element.LSNetwork;
import com.lsn.LoadSensing.element.LSSensor;
import com.lsn.LoadSensing.func.LSFunctions;
import com.readystatesoftware.mapviewballoons.R;

import greendroid.app.GDListActivity;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.ActionBarItem;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LSSensorListActivity extends GDListActivity {
	
	private final int OPTIONS = 0;
	private final int HELP = 1;
	private QuickActionWidget quickActions;
	
	private ProgressDialog       m_ProgressDialog = null;
	private ArrayList<LSSensor>  m_sensors = null;
	private LSSensorAdapter      m_adapter;
	private Runnable             viewSensors;
	private String idSession;
	private LSNetwork networkObj;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sensorlist);
        
        initActionBar();
        initQuickActionBar();
        
        Bundle bundle = getIntent().getExtras();
        
        if (bundle != null)
        {
        	idSession = bundle.getString("SESSION");
        	
        	networkObj = new LSNetwork();
        	networkObj = bundle.getParcelable("NETWORK_OBJ");
    	}  
        
        TextView txtNetwork = (TextView)findViewById(R.id.txtNetwork);
        txtNetwork.setText(networkObj.getNetworkName());
        
        m_sensors = new ArrayList<LSSensor>();
        this.m_adapter = new LSSensorAdapter(this,R.layout.row_list_sensor,m_sensors);
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
        m_ProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.msg_PleaseWait), getResources().getString(R.string.msg_retrievSensors), true);
    }
	
	private Runnable returnRes = new Runnable() {

    	@Override
    	public void run() {
    		if(m_sensors != null && m_sensors.size() > 0){
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_sensors.size();i++)
                m_adapter.add(m_sensors.get(i));
            }
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
    	}
    };
	
	private void getSensors() {

		try{
			m_sensors = new ArrayList<LSSensor>();
          
			// Server Request Ini
			Map<String, String> params = new HashMap<String, String>();
			params.put("session", idSession);
			params.put("IdXarxa", networkObj.getNetworkId());
			JSONArray jArray = LSFunctions.urlRequestJSONArray("http://viuterrassa.com/Android/getLlistaSensors.php",params);

			//JSONArray jArray = new JSONArray(response.toString());
			
			for (int i = 0; i<jArray.length(); i++)
			{
				JSONObject jsonData = jArray.getJSONObject(i);
				LSSensor s1 = new LSSensor();
				s1.setSensorId(jsonData.getString("id"));
				s1.setSensorName(jsonData.getString("sensor"));
				s1.setSensorChannel(jsonData.getString("canal"));
				s1.setSensorType(jsonData.getString("tipus"));
				String image = jsonData.getString("imatge");
				Bitmap bitmap = LSFunctions.getRemoteImage(new URL("http://viuterrassa.com/Android/Imatges/"+image));
				s1.setSensorImage(bitmap);
				s1.setSensorDesc(jsonData.getString("Descripcio"));
				s1.setSensorSituation(jsonData.getString("Poblacio"));
				s1.setSensorNetwork(jsonData.getString("Nom"));
		        m_sensors.add(s1);
			}
			
			// Server Request End  
          
          
          
          
          
          
          
          
          
	        //LSNetwork o1 = new LSNetwork();
			//o1.setNetworkName("Network 1");
			//o1.setNetworkSituation("lat. XX.XX lon. YY.YY");
			//o1.setNetworkNumSensors(3);
			//LSNetwork o2 = new LSNetwork();
			//o2.setNetworkName("Network 2");
			//o2.setNetworkSituation("lat. XX.XX lon. YY.YY");
			//o2.setNetworkNumSensors(2);
			//LSNetwork o3 = new LSNetwork();
			//o3.setNetworkName("Network 3");
			//o3.setNetworkSituation("lat. XX.XX lon. YY.YY");
			//o3.setNetworkNumSensors(4);
			//m_networks.add(o1);
			//m_networks.add(o2);
			//m_networks.add(o3);
			//Thread.sleep(1000);
	        Log.i("ARRAY", ""+ m_sensors.size());
        } catch (Exception e) { 
        	Log.e("BACKGROUND_PROC", e.getMessage());
        }
        runOnUiThread(returnRes);		
	}
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
        Toast.makeText(getApplicationContext(), "Has pulsado la posición " + position +", Item " + m_adapter.getSensorName(position), Toast.LENGTH_LONG).show();
//        Intent i = null;
//        i = new Intent(LSSensorListActivity.this,LSNetInfoActivity.class);
//        
//        if (i!=null){
//			Bundle bundle = new Bundle();
//			
//			bundle.putParcelable("SENSOR_OBJ", m_sensors.get(position));
//			
//			i.putExtras(bundle);
//
//			startActivity(i);
//		}
        
    }
	
	private void initActionBar() {
			
    	addActionBarItem(Type.Add,OPTIONS);
    	addActionBarItem(Type.Help,HELP);
	}
	
	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		
		 switch (item.getItemId()) {
    	
    	case OPTIONS:
    		Toast.makeText(getApplicationContext(), "Has pulsado el boton OPTIONS", Toast.LENGTH_SHORT).show();
    		quickActions.show(item.getItemView());
    		break;
    	case HELP:
    		Toast.makeText(getApplicationContext(), "Has pulsado el boton HELP", Toast.LENGTH_SHORT).show();
    		
    		break;
    	default:
    		return super.onHandleActionBarItemClick(item, position);
    	}
    	
		return true;
	} 
 
	private void initQuickActionBar()
	{
		quickActions = new QuickActionBar(this);
		quickActions.addQuickAction(new QuickAction(this,R.drawable.ic_menu_search,R.string.strSearch));
		quickActions.addQuickAction(new QuickAction(this,R.drawable.ic_menu_filter,R.string.strFilter));
		quickActions.setOnQuickActionClickListener(new OnQuickActionClickListener() {

			@Override
			public void onQuickActionClicked(QuickActionWidget widget, int position) {
				Toast.makeText(LSSensorListActivity.this, "Item " + position + " pulsado", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
}
