package com.lsn.LoadSensing.faves;

import greendroid.app.GDListActivity;
import greendroid.widget.ItemAdapter;
import greendroid.widget.item.TextItem;
import android.os.Bundle;

public class LSFavesImagesActivity extends GDListActivity{

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
      
		//initActionBar();
		//initQuickActionBar();
      
		ItemAdapter adapter = new ItemAdapter(this);
		adapter.add(createTextItem(0,"Imatge 1"));
		adapter.add(createTextItem(1,"Imatge 2"));
		adapter.add(createTextItem(2,"Imatge 3"));
		adapter.add(createTextItem(3,"Imatge 4"));
		adapter.add(createTextItem(4,"Imatge 5"));
		adapter.add(createTextItem(0,"Imatge 6"));
		adapter.add(createTextItem(1,"Imatge 7"));
		adapter.add(createTextItem(2,"Imatge 8"));
		adapter.add(createTextItem(3,"Imatge 9"));
		adapter.add(createTextItem(4,"Imatge 10"));
		setListAdapter(adapter);
	}
	
	private TextItem createTextItem(int stringId, String strItem) {
		final TextItem textItem = new TextItem(strItem);
        textItem.setTag(strItem);
        return textItem;
    }

}
