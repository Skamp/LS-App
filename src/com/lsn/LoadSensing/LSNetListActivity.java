package com.lsn.LoadSensing;

import java.util.ArrayList;

import com.lsn.LoadSensing.adapter.LSNetworkAdapter;
import com.lsn.LoadSensing.element.LSNetwork;

import greendroid.app.GDListActivity;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.ActionBarItem;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class LSNetListActivity extends GDListActivity {
	
	private final int OPTIONS = 0;
	private final int HELP = 1;
	private QuickActionWidget quickActions;
	
	private ProgressDialog       m_ProgressDialog = null;
	private ArrayList<LSNetwork> m_networks = null;
	private LSNetworkAdapter     m_adapter;
	private Runnable             viewNetworks;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_01_netlist);
        
        initActionBar();
        initQuickActionBar();
                
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
        m_ProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.msg_PleaseWait), getResources().getString(R.string.msg_retrievNetworks), true);
    }
	
	private Runnable returnRes = new Runnable() {

    	@Override
    	public void run() {
    		if(m_networks != null && m_networks.size() > 0){
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_networks.size();i++)
                m_adapter.add(m_networks.get(i));
            }
            m_ProgressDialog.dismiss();
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
				Toast.makeText(LSNetListActivity.this, "Item " + position + " pulsado", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
}
