package com.lsn.LoadSensing.faves;


import java.util.ArrayList;

import com.lsn.LoadSensing.R;
import com.lsn.LoadSensing.adapter.LSNetworkAdapter;
import com.lsn.LoadSensing.element.LSNetwork;

import greendroid.app.GDListActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class LSFavesNetworksActivity extends GDListActivity{

	
	private ProgressDialog       m_ProgressDialog = null;
	private ArrayList<LSNetwork> m_networks = null;
	private LSNetworkAdapter       m_adapter;
	private Runnable             viewNetworks;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_04_favesnetworks);
      
		m_networks = new ArrayList<LSNetwork>();
        this.m_adapter = new LSNetworkAdapter(this,R.layout.row_list_network,m_networks);
        setListAdapter(this.m_adapter);
        
        viewNetworks = new Runnable()
        {

			@Override
			public void run() {
				getNetworks();
			}
        };
        Thread thread = new Thread(null,viewNetworks,"ViewNetworks");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(LSFavesNetworksActivity.this, "Please wait...", "Retrieving data...", true);
		
//		ItemAdapter adapter = new ItemAdapter(this);
//		adapter.add(createTextItem(0,"Xarxa 1"));
//		adapter.add(createTextItem(1,"Xarxa 2"));
//		adapter.add(createTextItem(2,"Xarxa 3"));
//		adapter.add(createTextItem(3,"Xarxa 4"));
//		adapter.add(createTextItem(4,"Xarxa 5"));
//		setListAdapter(adapter);
	}
	
	private Runnable returnRes = new Runnable() {

    	@Override
    	public void run() {
    		if(m_networks != null && m_networks.size() > 0){
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_networks.size();i++)
                m_adapter.add(m_networks.get(i));
            }
            m_ProgressDialog.hide();
            m_adapter.notifyDataSetChanged();
    	}
    };
	
	private void getNetworks() {

		try{
          m_networks = new ArrayList<LSNetwork>();
          LSNetwork o1 = new LSNetwork();
          o1.setNetworkName("Network 1");
          o1.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o1.setNetworkNumber("XX");
          LSNetwork o2 = new LSNetwork();
          o2.setNetworkName("Network 2");
          o2.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o2.setNetworkNumber("YY");
          LSNetwork o3 = new LSNetwork();
          o3.setNetworkName("Network 3");
          o3.setNetworkSituation("lat. XX.XX lon. YY.YY");
          o3.setNetworkNumber("YY");
          m_networks.add(o1);
          m_networks.add(o2);
          m_networks.add(o3);
          Thread.sleep(1000);
          Log.i("ARRAY", ""+ m_networks.size());
        } catch (Exception e) { 
          Log.e("BACKGROUND_PROC", e.getMessage());
        }
        runOnUiThread(returnRes);		
	}

	
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
        Toast.makeText(getApplicationContext(), "Has pulsado la posición " + position +", Item " + m_adapter.getNetworkName(position), Toast.LENGTH_LONG).show();
    }
	
	
	
	
	
	
	
	
//	private TextItem createTextItem(int stringId, String strItem) {
//		final TextItem textItem = new TextItem(strItem);
//        textItem.setTag(strItem);
//        return textItem;
//    }
}
