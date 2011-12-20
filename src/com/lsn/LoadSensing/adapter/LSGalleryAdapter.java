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

public class LSGalleryAdapter extends ArrayAdapter<LSImage> {

	private ArrayList<LSImage> items;
	Context mContext;
	
	public LSGalleryAdapter(Context context, int textViewResourceId, ArrayList<LSImage> items) {
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
			v = vi.inflate(R.layout.image_gallery,null);
		}
		
		LSImage o = items.get(position);
		if (o != null)
		{
			ImageView imgView = (ImageView) v.findViewById(R.id.thumbImage);
			TextView txtName = (TextView) v.findViewById(R.id.thumbName);
			
            imgView.setImageBitmap(o.getImageThumb(200));
            txtName.setText(o.getImageName()); 
		}
		return v;
	}
}
