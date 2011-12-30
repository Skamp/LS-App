package com.lsn.LoadSensing.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lsn.LoadSensing.R;
import com.lsn.LoadSensing.element.LSNetwork;

public class LSNetworkAdapter extends ArrayAdapter<LSNetwork>{

	private ArrayList<LSNetwork> items;
	Context mContext;
	
	public LSNetworkAdapter(Context context, int textViewResourceId, ArrayList<LSNetwork> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_list_network,null);
		}
		
		LSNetwork o = items.get(position);
		if (o != null)
		{
			TextView txtNetName = (TextView) v.findViewById(R.id.networkName);
			TextView txtNetSituation = (TextView) v.findViewById(R.id.networkSituation);
            TextView txtNetSensors = (TextView) v.findViewById(R.id.networkSensors);
            
            txtNetName.setText(o.getNetworkName()); 
            txtNetSituation.setText(o.getNetworkSituation());
            txtNetSensors.setText(o.getNetworkNumSensors().toString());
    
		}
		return v;
	}
	
	public String getNetworkName(int position){
		
		LSNetwork net = items.get(position);
		return net.getNetworkName();
	}
}
