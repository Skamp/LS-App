package com.lsn.LoadSensing;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsn.LoadSensing.element.LSImage;
import com.readystatesoftware.mapviewballoons.R;

import greendroid.app.GDActivity;

public class LSBigImageActivity extends GDActivity implements OnGestureListener {

	private LSImage imageObj;
	private Integer position;
	
	private GestureDetector gestureScanner;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.act_bigimage);
        
        Log.i("BIGIMAGE", "On BigImage");
        gestureScanner = new GestureDetector(this);
        
        Bundle bundle = getIntent().getExtras();
        
        if (bundle != null)
        {
//        	strTitle = bundle.getString("TITLE");
//        	imageObj = new LSImage();
//        	imageObj = bundle.getParcelable("IMAGE_OBJ");
        	
//        	m_images = new ArrayList<LSImage>();
//        	m_images = bundle.getParcelableArrayList("IMAGE_ARRAY");
        	position = bundle.getInt("POSITION");
        	imageObj = new LSImage();
        	//imageObj = LSNetImagesActivity.m_images.get(position);
    	}  
        
        updateTitle();
        updateImage();
        
        TextView txtNetName = (TextView) findViewById(R.id.netName);
        txtNetName.setText(imageObj.getImageNetwork());
        //ImageView imgNetwork = (ImageView) findViewById(R.id.imageView);
        //imgNetwork.setImageBitmap(imageObj.getImageBitmap());
        
	}

	@Override
    public boolean onTouchEvent(MotionEvent me)
    {
 
        return gestureScanner.onTouchEvent(me);
 
    }
	
	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {

		if (distanceX > 0) //Scroll right
		{
			if (position<LSNetImagesActivity.m_images.size()-1)
			{
				++position;
			}
		}
		else //Scroll right
		{
			if (position>0)
			{
				--position;
			}
		}
		
        updateTitle();
        updateImage();
        
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	
	private void updateTitle()
	{
		String strTitleFormat = getResources().getString(R.string.act_lbl_BigImage);
	    String strTitle = String.format(strTitleFormat, position+1,LSNetImagesActivity.m_images.size());
        
        this.setTitle(strTitle);
	}
	
	private void updateImage()
	{
		imageObj = LSNetImagesActivity.m_images.get(position);
		ImageView imgNetwork = (ImageView) findViewById(R.id.imageView);
        imgNetwork.setImageBitmap(imageObj.getImageBitmap());
	}
	
}
