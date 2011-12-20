package com.lsn.LoadSensing;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.lsn.LoadSensing.adapter.LSGalleryAdapter;
import com.lsn.LoadSensing.element.LSImage;
import com.lsn.LoadSensing.element.LSNetwork;
import com.lsn.LoadSensing.func.LSFunctions;
import com.readystatesoftware.mapviewballoons.R;

import greendroid.app.GDActivity;

public class LSNetImagesActivity extends GDActivity {

	private ProgressDialog       m_ProgressDialog = null;
	private ArrayList<LSImage>	 m_images = null;
	private LSGalleryAdapter     m_adapter;
	private Runnable             viewImages;
	private static HashMap<String,Bitmap> hashImages = new HashMap<String,Bitmap>();
	private Bitmap imgNetwork;
	private LSNetwork networkObj;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.act_netimages);
        
        GridView imagegrid = (GridView) findViewById(R.id.gridView);

        imagegrid.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				Toast.makeText(getApplicationContext(), "Has pulsado la imagen "+ position +" id " + id, Toast.LENGTH_SHORT).show();
				Intent i = null;
		        i = new Intent(LSNetImagesActivity.this,LSBigImageActivity.class);
		        
		        if (i!=null){
					Bundle bundle = new Bundle();
					
					String strTitleFormat = getResources().getString(R.string.act_lbl_BigImage);
				    String strTitle = String.format(strTitleFormat, position+1);
					
					bundle.putString("TITLE", strTitle);
					bundle.putParcelable("IMAGE_OBJ", m_images.get(position));
					i.putExtras(bundle);
		        	
					startActivity(i);
				}
				
				
			}
        	
        	
        	
        });
        
        Bundle bundle = getIntent().getExtras();
        
        if (bundle != null)
        {
        	//idSession = bundle.getString("SESSION");
        	networkObj = bundle.getParcelable("NETWORK_OBJ");
    	}  
        
        m_images = new ArrayList<LSImage>();
        m_adapter = new LSGalleryAdapter(this,R.layout.image_gallery,m_images);
        imagegrid.setAdapter(m_adapter);
        
        TextView txtNetName = (TextView) findViewById(R.id.netName);
        txtNetName.setText(networkObj.getNetworkName());
        
        viewImages = new Runnable()
        {

			@Override
			public void run() {
				getImages();
			}
        };
        Thread thread = new Thread(null,viewImages,"ViewNetworks");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.msg_PleaseWait), getResources().getString(R.string.msg_retrievImages), true);
	 }
	
	 private Runnable returnRes = new Runnable() {

    	@Override
    	public void run() {
    		if(m_images != null && m_images.size() > 0){
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_images.size();i++)
                m_adapter.add(m_images.get(i));
            }
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
    	}
     };
	
	 private void getImages() {

		try{
			m_images = new ArrayList<LSImage>();
          
			// Server Request Ini
			Map<String, String> params = new HashMap<String, String>();
			params.put("session", LSHomeActivity.idSession);
			params.put("IdXarxa", networkObj.getNetworkId());
			JSONArray jArray = LSFunctions.urlRequestJSONArray("http://viuterrassa.com/Android/getLlistaImatges.php",params);

			for (int i = 0; i<jArray.length(); i++)
			{
				JSONObject jsonData = jArray.getJSONObject(i);
				LSImage i1 = new LSImage();
				i1.setImageId(jsonData.getString("IdImatge"));
				String image = jsonData.getString("imatge");
				if (hashImages.containsKey(image))
				{
					imgNetwork = hashImages.get(image);
				}
				else
				{
					imgNetwork = LSFunctions.getRemoteImage(new URL("http://viuterrassa.com/Android/Imatges/"+image));
					hashImages.put(image, imgNetwork);
				}
				i1.setImageBitmap(imgNetwork);
				i1.setImageSituation(jsonData.getString("Poblacio"));
				i1.setImageName(jsonData.getString("Nom"));
				i1.setImageNetwork(jsonData.getString("Nom"));
				m_images.add(i1);
			}
			
			// Server Request End  
          
          
          
          
          
          
          
          
          
	        //LSNetwork o1 = new LSNetwork();
			//o1.setNetworkName("Network 1");
			//o1.setNetworkSituation("lat. XX.XX lon. YY.YY");
			//o1.setNetworkNumSensors(3);
			//LSNetwork o2 = new LSNetwork();
			//o2.setNetworkName("Network 2");
			//o2.setNetworkSituation("lat. XX.XX lon. YY.YY");
			//o2.setNetworkNumSensors(2);
			//LSNetwork o3 = new LSNetwork();
			//o3.setNetworkName("Network 3");
			//o3.setNetworkSituation("lat. XX.XX lon. YY.YY");
			//o3.setNetworkNumSensors(4);
			//m_networks.add(o1);
			//m_networks.add(o2);
			//m_networks.add(o3);
			//Thread.sleep(1000);
	        Log.i("ARRAY", ""+ m_images.size());
        } catch (Exception e) { 
        	Log.e("BACKGROUND_PROC", e.getMessage());
        }
        runOnUiThread(returnRes);		
	 }
	
	 
}
