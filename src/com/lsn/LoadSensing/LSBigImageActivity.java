package com.lsn.LoadSensing;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsn.LoadSensing.element.LSImage;
import com.readystatesoftware.mapviewballoons.R;

import greendroid.app.GDActivity;

public class LSBigImageActivity extends GDActivity {

	private LSImage imageObj;
	private String strTitle;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.act_bigimage);
        
        Log.i("BIGIMAGE", "On BigImage");
        
        Bundle bundle = getIntent().getExtras();
        
        if (bundle != null)
        {
        	strTitle = bundle.getString("TITLE");
        	imageObj = new LSImage();
        	imageObj = bundle.getParcelable("IMAGE_OBJ");
    	}  
        
        this.setTitle(strTitle);
        TextView txtNetName = (TextView) findViewById(R.id.netName);
        txtNetName.setText(imageObj.getImageNetwork());
        ImageView imgNetwork = (ImageView) findViewById(R.id.imageView);
        imgNetwork.setImageBitmap(imageObj.getImageBitmap());
        
	}
}
