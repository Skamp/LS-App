package com.lsn.LoadSensing;

//import greendroid.app.GDActivity;
import java.util.ArrayList;
import java.util.List;

import com.lsn.LoadSensing.element.LSNetwork;

import greendroid.app.GDActivity;
import greendroid.app.GDListActivity;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ItemAdapter;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;
import greendroid.widget.item.DescriptionItem;
import greendroid.widget.item.Item;
import greendroid.widget.item.SeparatorItem;
import greendroid.widget.item.TextItem;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//public class LSNetListActivity extends GDActivity {
public class LSNetListActivity extends GDListActivity {
	
	private final int OPTIONS = 0;
	private final int HELP = 1;
	private QuickActionWidget quickActions;
	
	private ProgressDialog     m_ProgressDialog = null;
	private ArrayList<LSNetwork> m_networks = null;
	private NetworkAdapter     m_adapter;
	private Runnable           viewNetworks;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_01_netlist);
        
        initActionBar();
        initQuickActionBar();
                
        m_networks = new ArrayList<LSNetwork>();
        this.m_adapter = new NetworkAdapter(this,R.layout.act_01_netlist_row,m_networks);
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
        m_ProgressDialog = ProgressDialog.show(LSNetListActivity.this, "Please wait...", "Retrieving data...", true);
        
       
        
        
        //ItemAdapter adapter = new ItemAdapter(this);
        //adapter.add(createTextItem(0,"Xarxa 1"));
        //adapter.add(createTextItem(1,"Xarxa 2"));
        //adapter.add(createTextItem(2,"Xarxa 3"));
        //adapter.add(createTextItem(3,"Xarxa 4"));
        //adapter.add(createTextItem(4,"Xarxa 5"));
        //adapter.add(createTextItem(5,"Xarxa 6"));
        //adapter.add(createTextItem(6,"Xarxa 7"));
        //adapter.add(createTextItem(7,"Xarxa 8"));
        //adapter.add(createTextItem(8,"Xarxa 9"));
        //adapter.add(createTextItem(9,"Xarxa 10"));

        //setListAdapter(adapter);
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
          o1.setNetworkName("Network 11111111111111111");
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
          Thread.sleep(5000);
          Log.i("ARRAY", ""+ m_networks.size());
        } catch (Exception e) { 
          Log.e("BACKGROUND_PROC", e.getMessage());
        }
        runOnUiThread(returnRes);		
	}

	private class NetworkAdapter extends ArrayAdapter<LSNetwork>{

		private ArrayList<LSNetwork> items;
		
		public NetworkAdapter(Context context, int textViewResourceId, ArrayList<LSNetwork> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View v = convertView;
			if (v == null)
			{
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.act_01_netlist_row,null);
			}
			
			LSNetwork o = items.get(position);
			if (o != null)
			{
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView mt = (TextView) v.findViewById(R.id.mediumtext);
	            TextView bt = (TextView) v.findViewById(R.id.bottomtext);
	            if (tt != null) {
	                  tt.setText(o.getNetworkName()); 
	            }
	            if(mt != null){
	                  mt.setText("Situation: "+ o.getNetworkSituation());
	            }
	            if(bt != null){
	                  bt.setText("Number of sensors: "+ o.getNetworkNumber());
	            }
			}
			return v;
		}
		
		public String getNetworkName(int position){
			
			LSNetwork net = items.get(position);
			return net.getNetworkName();
		}

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
