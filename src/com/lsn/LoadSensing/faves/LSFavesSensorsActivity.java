package com.lsn.LoadSensing.faves;

import greendroid.app.GDListActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ItemAdapter;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;
import greendroid.widget.item.TextItem;

import com.lsn.LoadSensing.R;
import android.os.Bundle;

public class LSFavesSensorsActivity extends GDListActivity{
	
	private final int OPTIONS = 0;
	private final int HELP = 1;
	private QuickActionWidget quickActions;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
      
		initActionBar();
		initQuickActionBar();
      
		ItemAdapter adapter = new ItemAdapter(this);
		adapter.add(createTextItem(0,"Sensor 1"));
		adapter.add(createTextItem(1,"Sensor 2"));
		adapter.add(createTextItem(2,"Sensor 3"));
		adapter.add(createTextItem(3,"Sensor 4"));
		adapter.add(createTextItem(4,"Sensor 5"));
		adapter.add(createTextItem(0,"Sensor 6"));
		adapter.add(createTextItem(1,"Sensor 7"));
		adapter.add(createTextItem(2,"Sensor 8"));
		adapter.add(createTextItem(3,"Sensor 9"));
		adapter.add(createTextItem(4,"Sensor 10"));
		setListAdapter(adapter);
	}
	
	private TextItem createTextItem(int stringId, String strItem) {
		final TextItem textItem = new TextItem(strItem);
        textItem.setTag(strItem);
        return textItem;
    }
	
	private void initActionBar() {
		addActionBarItem(Type.Add,OPTIONS);
		addActionBarItem(Type.Help,HELP);
	}
	
	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
    	case OPTIONS:
    		quickActions.show(item.getItemView());
    		break;
    	case HELP:
    		
    		break;
    	default:
    		return super.onHandleActionBarItemClick(item, position);
    	}
    	
		return true;
	} 
	
	private void initQuickActionBar()
	{
		quickActions = new QuickActionBar(this);
		//quickActions.addQuickAction(new QuickAction(this,R.drawable.find,getString(R.string.find)));
		//quickActions.addQuickAction(new QuickAction(this,R.drawable.filter,getString(R.string.filter)));
		quickActions.addQuickAction(new QuickAction(this,R.drawable.find,"XXX"));
		quickActions.addQuickAction(new QuickAction(this,R.drawable.filter,"YYY"));
		quickActions.setOnQuickActionClickListener(new OnQuickActionClickListener() {

			@Override
			public void onQuickActionClicked(QuickActionWidget widget, int position) {
			}
		});
	}
}
