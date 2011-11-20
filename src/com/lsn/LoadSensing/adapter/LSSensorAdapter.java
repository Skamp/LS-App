package com.lsn.LoadSensing.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lsn.LoadSensing.R;
import com.lsn.LoadSensing.element.LSSensor;

public class LSSensorAdapter extends ArrayAdapter<LSSensor>{

	private ArrayList<LSSensor> items;
	Context mContext;
	
	public LSSensorAdapter(Context context, int textViewResourceId, ArrayList<LSSensor> items) {
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
			v = vi.inflate(R.layout.row_list_sensor,null);
		}
		
		LSSensor o = items.get(position);
		if (o != null)
		{
			TextView txtSenName = (TextView) v.findViewById(R.id.sensorName);
			TextView txtSenSituation = (TextView) v.findViewById(R.id.sensorSituation);
            TextView txtSenNetwork = (TextView) v.findViewById(R.id.sensorNetwork);
            
            txtSenName.setText(o.getSensorName()); 
            txtSenSituation.setText(o.getSensorSituation());
            txtSenNetwork.setText(o.getSensorNetwork());
    
		}
		return v;
	}
	
	public String getSensorName(int position){
		
		LSSensor net = items.get(position);
		return net.getSensorName();
	}
}
