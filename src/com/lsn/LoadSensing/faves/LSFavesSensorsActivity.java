package com.lsn.LoadSensing.faves;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.lsn.LoadSensing.LSFavesActivity;
import com.lsn.LoadSensing.LSSensorInfoActivity;
import com.lsn.LoadSensing.R;
import com.lsn.LoadSensing.SQLite.LSNSQLiteHelper;
import com.lsn.LoadSensing.adapter.LSSensorAdapter;
import com.lsn.LoadSensing.element.LSSensor;
import com.lsn.LoadSensing.func.LSFunctions;

import greendroid.app.GDListActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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

public class LSFavesSensorsActivity extends GDListActivity{
	
	private ProgressDialog       m_ProgressDialog = null;
	private ArrayList<LSSensor> m_sensors = null;
	private LSSensorAdapter       m_adapter;
	private Runnable             viewSensors;
	
	private Bitmap imgSensor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_04_favessensors);
	  
		m_sensors = new ArrayList<LSSensor>();
        this.m_adapter = new LSSensorAdapter(this,R.layout.row_list_sensor,m_sensors);
        setListAdapter(this.m_adapter);
        
        viewSensors = new Runnable()
        {

			@Override
			public void run() {
				getSensors();
			}
        };
        Thread thread = new Thread(null,viewSensors,"ViewSensors");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.msg_PleaseWait), getResources().getString(R.string.msg_retrievSensors), true);
		
        registerForContextMenu(getListView());
        
//		ItemAdapter adapter = new ItemAdapter(this);
//		adapter.add(createTextItem(0,"Sensor 1"));
//		adapter.add(createTextItem(1,"Sensor 2"));
//		adapter.add(createTextItem(2,"Sensor 3"));
//		adapter.add(createTextItem(3,"Sensor 4"));
//		adapter.add(createTextItem(4,"Sensor 5"));
//		adapter.add(createTextItem(0,"Sensor 6"));
//		adapter.add(createTextItem(1,"Sensor 7"));
//		adapter.add(createTextItem(2,"Sensor 8"));
//		adapter.add(createTextItem(3,"Sensor 9"));
//		adapter.add(createTextItem(4,"Sensor 10"));
//		setListAdapter(adapter);
	}
	
	private Runnable returnRes = new Runnable() {

    	@Override
    	public void run() {
    		if(m_sensors != null && m_sensors.size() > 0){
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_sensors.size();i++)
                m_adapter.add(m_sensors.get(i));
            }
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
    	}
    };
	
	private void getSensors() {

		m_sensors = new ArrayList<LSSensor>();
		LSNSQLiteHelper lsndbh = new LSNSQLiteHelper(this, "DBLSN", null, 1);
		SQLiteDatabase db = lsndbh.getReadableDatabase();
		
		Log.i("INFO", "Faves getSensors");
		if (db != null) {
			Cursor c = db.rawQuery("SELECT * FROM Sensor", null);
			c.moveToFirst();
			if (c != null) {
				while (!c.isAfterLast()) {
					String name = c.getString(c.getColumnIndex("name"));
					String idSensor = c.getString(c.getColumnIndex("idSensor"));
					String idNetwork = c.getString(c
							.getColumnIndex("idNetwork"));
					String type = c.getString(c.getColumnIndex("type"));
					String description = c.getString(c
							.getColumnIndex("description"));
					String channel = c.getString(c.getColumnIndex("channel"));
					String city = c.getString(c.getColumnIndex("poblacio"));
					String image = c.getString(c.getColumnIndex("image"));
					int faves = c.getInt(c.getColumnIndex("faves"));

					Log.i("INFO", "Obtaining image " + image);
					try {
						Log.i("INFO", image);
						imgSensor = LSFunctions.getRemoteImage(new URL("http://viuterrassa.com/Android/Imatges/"+image));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}

					LSSensor o1 = new LSSensor();
					o1.setSensorName(name);
					o1.setSensorId(idSensor);
					o1.setSensorNetwork(idNetwork);
					o1.setSensorType(type);
					o1.setSensorDesc(description);
					o1.setSensorChannel(channel);
					o1.setSensorSituation(city);
					o1.setSensorImage(imgSensor);
					o1.setSensorImageName(image);
					o1.setSensorFaves(faves);

					m_sensors.add(o1);

					c.move(1);
				}
			}
			try {
				Thread.sleep(1000);
				Log.i("ARRAY", "" + m_sensors.size());
			} catch (Exception e) {
				Log.e("BACKGROUND_PROC", e.getMessage());
			}
			Log.i("INFO", "Close cursor");
			c.close();
			db.close();
			runOnUiThread(returnRes);
		}		
	}

	
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
        Toast.makeText(getApplicationContext(), "Has pulsado la posici�n " + position +", Item " + m_adapter.getSensorName(position), Toast.LENGTH_LONG).show();
        Intent i = null;
		i = new Intent(this, LSSensorInfoActivity.class);

		if (i != null) {	
			Bundle bundle = new Bundle();

			bundle.putParcelable("SENSOR_OBJ", m_sensors.get(position));

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
			LSSensor sen1 = new LSSensor();
			sen1 = m_sensors.get(info.position);
			if (db != null) {
				db.execSQL("DELETE FROM Sensor WHERE idSensor ='"
						+ sen1.getSensorId() + "'");
				db.close();
				Bundle bundle = new Bundle();
				bundle.putInt("par", 1);
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
