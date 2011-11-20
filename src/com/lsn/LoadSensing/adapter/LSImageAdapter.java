package com.lsn.LoadSensing.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsn.LoadSensing.R;
import com.lsn.LoadSensing.element.LSImage;


public class LSImageAdapter extends ArrayAdapter<LSImage>{

	private ArrayList<LSImage> items;
	Context mContext;
	
	public LSImageAdapter(Context context, int textViewResourceId, ArrayList<LSImage> items) {
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
			v = vi.inflate(R.layout.row_list_image,null);
		}
		
		LSImage o = items.get(position);
		if (o != null)
		{
			ImageView imgView = (ImageView) v.findViewById(R.id.imageBitmap);
			TextView txtImgName = (TextView) v.findViewById(R.id.imageName);
			TextView txtImgSituation = (TextView) v.findViewById(R.id.imageSituation);
            TextView txtImgNetwork = (TextView) v.findViewById(R.id.imageNetwork);
            
            imgView.setImageDrawable(o.getImageDrawable());
            txtImgName.setText(o.getImageName()); 
            txtImgSituation.setText(o.getImageSituation());
            txtImgNetwork.setText(o.getImageNetwork());
    
		}
		return v;
	}
	
	public String getImageName(int position){
		
		LSImage net = items.get(position);
		return net.getImageName();
	}
}
