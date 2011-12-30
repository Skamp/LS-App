package com.lsn.LoadSensing.faves;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.lsn.LoadSensing.LSBigImageActivity;
import com.lsn.LoadSensing.LSFavesActivity;
import com.lsn.LoadSensing.R;
import com.lsn.LoadSensing.SQLite.LSNSQLiteHelper;
import com.lsn.LoadSensing.adapter.LSImageAdapter;
import com.lsn.LoadSensing.element.LSImage;
import com.lsn.LoadSensing.func.LSFunctions;

import greendroid.app.GDListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

public class LSFavesImagesActivity extends GDListActivity{

	private ProgressDialog       m_ProgressDialog = null;
	private ArrayList<LSImage> m_images = null;
	private LSImageAdapter       m_adapter;
	private Runnable             viewImages;
	
	private Bitmap imgSensor;
	
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
        m_ProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.msg_PleaseWait), getResources().getString(R.string.msg_retrievImages), true);
        
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
        registerForContextMenu(getListView());
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

		m_images = new ArrayList<LSImage>();
		LSNSQLiteHelper lsndbh = new LSNSQLiteHelper(this, "DBLSN", null, 1);
		SQLiteDatabase db = lsndbh.getReadableDatabase();

		if (db != null) {
			Cursor c = db.rawQuery("SELECT * FROM Image", null);
			c.moveToFirst();
			if (c != null) {
				while (!c.isAfterLast()) {
					String name = c.getString(c.getColumnIndex("name"));
					String idNetwork = c.getString(c
							.getColumnIndex("idNetwork"));
					String city = c.getString(c.getColumnIndex("poblacio"));
					String image = c.getString(c.getColumnIndex("imageFile"));
					try {
						imgSensor = LSFunctions.getRemoteImage(new URL("http://viuterrassa.com/Android/Imatges/"+image));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}

					LSImage i1 = new LSImage();
					i1.setImageName(name);
					i1.setImageNetwork(idNetwork);
					i1.setImageSituation(city);
					i1.setImageBitmap(imgSensor);
					i1.setImageNameFile(image);

					m_images.add(i1);

					c.move(1);
				}
			}
			try {
				Thread.sleep(1000);
				Log.i("ARRAY", "" + m_images.size());
			} catch (Exception e) {
				Log.e("BACKGROUND_PROC", e.getMessage());
			}
			c.close();
			db.close();
			runOnUiThread(returnRes);
		}	
	}

	
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
        Toast.makeText(getApplicationContext(), "Has pulsado la posición " + position +", Item " + m_adapter.getImageName(position), Toast.LENGTH_LONG).show();
        Intent i = null;
        i = new Intent(this,LSBigImageActivity.class);
        
        if (i!=null){
			Bundle bundle = new Bundle();

			String strTitleFormat = getResources().getString(R.string.act_lbl_BigImage);
		    String strTitle = String.format(strTitleFormat, position+1,position+1);

			bundle.putString("TITLE", strTitle);
			bundle.putParcelable("IMAGE_OBJ", m_images.get(position));
			i.putExtras(bundle);
        	
			startActivity(i);
        }
    }
	
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu_del, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		LSNSQLiteHelper lsndbh = new LSNSQLiteHelper(this, "DBLSN", null, 1);
		SQLiteDatabase db = lsndbh.getWritableDatabase();

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.del_faves:
			LSImage ima1 = new LSImage();
			ima1 = m_images.get(info.position);
			if (db != null) {
				db.execSQL("DELETE FROM Image WHERE name ='"
						+ ima1.getImageName() + "'"); 
				db.close();
				Bundle bundle = new Bundle();
				bundle.putInt("par", 2);
				Intent i = new Intent(this, LSFavesActivity.class);
				i.putExtras(bundle);
				startActivity(i);
				this.finish();
			}

			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
//	private TextItem createTextItem(int stringId, String strItem) {
//		final TextItem textItem = new TextItem(strItem);
//        textItem.setTag(strItem);
//        return textItem;
//    }

}
