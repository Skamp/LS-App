package com.lsn.LoadSensing;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lsn.LoadSensing.element.LSImage;
import com.lsn.LoadSensing.element.LSSensor;
import com.lsn.LoadSensing.func.LSFunctions;
import com.lsn.LoadSensing.ui.CustomToast;
import com.readystatesoftware.mapviewballoons.R;

import greendroid.app.GDActivity;

public class LSBigImageActivity extends GDActivity implements OnGestureListener {

	private LSImage imageObj;
	private Integer position;
	
	private GestureDetector gestureScanner;
	private boolean moveLeft;
	
	Integer imgHeight = 0;
	Integer imgWidth = 0;
	
	private int currentX;
	private int currentY;
	
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
		 	
		if (moveLeft)
		{
			if (position<LSNetImagesActivity.m_images.size()-1)
			{
				++position;
			}
		}
		else
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
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {

		if (distanceX > 0) //Scroll left
		{
			moveLeft = true;
		}
		else //Scroll right
		{
			moveLeft = false;
		}
		 
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
		final ImageView imgNetwork = (ImageView) findViewById(R.id.imageView);
		
		Bitmap image = imageObj.getImageBitmap();
		Integer bmpHeight = image.getHeight();
		Integer bmpWidth = image.getWidth();
		
        imgNetwork.setImageBitmap(imageObj.getImageBitmap());
        
//        imgNetwork.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY),
//                MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY));
//        ViewTreeObserver vto = imgNetwork.getViewTreeObserver();
//        vto.addOnPreDrawListener(new OnPreDrawListener() {
//
//			@Override
//			public boolean onPreDraw() {
//				// TODO Auto-generated method stub
//				imgHeight = imgNetwork.getMeasuredHeight();
//		        imgWidth = imgNetwork.getMeasuredWidth();
//				return true;
//			}
//        	
//        });
       
        
        CustomToast.showCustomToast(this,"BMP height:" + bmpHeight +" BMP width:" + bmpWidth + 
        		" IMG height:" + imgHeight +" IMG width:" + imgWidth,CustomToast.IMG_INFORMATION,CustomToast.LENGTH_LONG);
        
        setSensors();
	}
	
	public void setSensors() {
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.relative);
		RelativeLayout.LayoutParams params1;

		try {
			// Server Request Ini
			Map<String, String> params = new HashMap<String, String>();
			params.put("session", LSHomeActivity.idSession);
			params.put("IdImatge", imageObj.getImageId());
			JSONArray jArray = LSFunctions.urlRequestJSONArray(
					"http://viuterrassa.com/Android/getLlistaSensorsImatges.php",
					params);
			ImageButton[] imageButtonArray = new ImageButton[jArray.length()];

			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonData = jArray.getJSONObject(i);
				String x = jsonData.getString("x");
				String y = jsonData.getString("y");

				final LSSensor s1 = new LSSensor();
				s1.setSensorId(jsonData.getString("id"));
				s1.setSensorName(jsonData.getString("sensor"));
				s1.setSensorChannel(jsonData.getString("canal"));
				s1.setSensorType(jsonData.getString("tipus"));
				s1.setSensorImageName(jsonData.getString("imatge"));
				s1.setSensorDesc(jsonData.getString("Descripcio"));
				s1.setSensorSituation(jsonData.getString("Poblacio"));
				s1.setSensorNetwork(jsonData.getString("Nom"));			

				params1 = new RelativeLayout.LayoutParams(15, 15); 
				params1.leftMargin = Integer.parseInt(x)+5;
				params1.topMargin = Integer.parseInt(y)+60; 
				imageButtonArray[i] = new ImageButton(this);
				imageButtonArray[i].setImageResource(R.drawable.loc_icon);
				imageButtonArray[i].setOnClickListener(new OnClickListener() {

			        public void onClick(View v) {
			        	 Intent i = null;
			             i = new Intent(LSBigImageActivity.this,LSSensorInfoActivity.class);

			             if (i!=null){
			     			Bundle bundle = new Bundle();

			     			bundle.putParcelable("SENSOR_OBJ", s1);

			     			i.putExtras(bundle);
 
			     			startActivity(i);
			     		} 
			        }
				});


				rl.addView(imageButtonArray[i], params1); 
			}
			// Server Request End

		} catch (Exception e) {
			Log.e("BACKGROUND_PROC", e.getMessage());
		}


	}
	
	
	
}
