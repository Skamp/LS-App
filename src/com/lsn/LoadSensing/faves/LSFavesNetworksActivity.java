package com.lsn.LoadSensing.faves;


import greendroid.app.GDListActivity;
import greendroid.widget.ItemAdapter;
import greendroid.widget.item.TextItem;
import android.os.Bundle;

public class LSFavesNetworksActivity extends GDListActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
      
		ItemAdapter adapter = new ItemAdapter(this);
		adapter.add(createTextItem(0,"Xarxa 1"));
		adapter.add(createTextItem(1,"Xarxa 2"));
		adapter.add(createTextItem(2,"Xarxa 3"));
		adapter.add(createTextItem(3,"Xarxa 4"));
		adapter.add(createTextItem(4,"Xarxa 5"));
		setListAdapter(adapter);
	}
	
	private TextItem createTextItem(int stringId, String strItem) {
		final TextItem textItem = new TextItem(strItem);
        textItem.setTag(strItem);
        return textItem;
    }
}
