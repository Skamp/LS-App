package com.lsn.LoadSensing.faves;

import java.util.ArrayList;

import com.lsn.LoadSensing.R;
import com.lsn.LoadSensing.adapter.LSImageAdapter;
import com.lsn.LoadSensing.element.LSImage;
import greendroid.app.GDListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class LSFavesImagesActivity extends GDListActivity{

	private ProgressDialog       m_ProgressDialog = null;
	private ArrayList<LSImage> m_images = null;
	private LSImageAdapter       m_adapter;
	private Runnable             viewImages;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_04_favesimages);
		
		m_images = new ArrayList<LSImage>();
        this.m_adapter = new LSImageAdapter(this,R.layout.row_list_image,m_images);
        setListAdapter(this.m_adapter);
        
        viewImages = new Runnable()
        {

			@Override
			public void run() {
				getImages();
			}
        };
        Thread thread = new Thread(null,viewImages,"ViewImages");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(LSFavesImagesActivity.this, "Please wait...", "Retrieving data...", true);
        
//		ItemAdapter adapter = new ItemAdapter(this);
//		adapter.add(createTextItem(0,"Imatge 1"));
//		adapter.add(createTextItem(1,"Imatge 2"));
//		adapter.add(createTextItem(2,"Imatge 3"));
//		adapter.add(createTextItem(3,"Imatge 4"));
//		adapter.add(createTextItem(4,"Imatge 5"));
//		adapter.add(createTextItem(0,"Imatge 6"));
//		adapter.add(createTextItem(1,"Imatge 7"));
//		adapter.add(createTextItem(2,"Imatge 8"));
//		adapter.add(createTextItem(3,"Imatge 9"));
//		adapter.add(createTextItem(4,"Imatge 10"));
//		setListAdapter(adapter);
	}
	
	private Runnable returnRes = new Runnable() {

    	@Override
    	public void run() {
    		if(m_images != null && m_images.size() > 0){
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_images.size();i++)
                m_adapter.add(m_images.get(i));
            }
            m_ProgressDialog.hide();
            m_adapter.notifyDataSetChanged();
    	}
    };
	
	private void getImages() {

		try{
          m_images = new ArrayList<LSImage>();
          LSImage o1 = new LSImage();
          o1.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
          o1.setImageName("Image 1");
          o1.setImageSituation("lat. XX.XX lon. YY.YY");
          o1.setImageNetwork("Network 1");
          LSImage o2 = new LSImage();
          o2.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
          o2.setImageName("Image 2");
          o2.setImageSituation("lat. XX.XX lon. YY.YY");
          o2.setImageNetwork("Network 2");
          LSImage o3 = new LSImage();
          o3.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
          o3.setImageName("Image 3");
          o3.setImageSituation("lat. XX.XX lon. YY.YY");
          o3.setImageNetwork("Network 3");
          m_images.add(o1);
          m_images.add(o2);
          m_images.add(o3);
          Thread.sleep(1000);
          Log.i("ARRAY", ""+ m_images.size());
        } catch (Exception e) { 
          Log.e("BACKGROUND_PROC", e.getMessage());
        }
        runOnUiThread(returnRes);		
	}

	
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
        Toast.makeText(getApplicationContext(), "Has pulsado la posición " + position +", Item " + m_adapter.getImageName(position), Toast.LENGTH_LONG).show();
    }
	
	
	
//	private TextItem createTextItem(int stringId, String strItem) {
//		final TextItem textItem = new TextItem(strItem);
//        textItem.setTag(strItem);
//        return textItem;
//    }

}
