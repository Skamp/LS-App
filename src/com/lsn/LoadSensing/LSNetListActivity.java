package com.lsn.LoadSensing;

//import greendroid.app.GDActivity;
import greendroid.app.GDListActivity;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ItemAdapter;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;
import greendroid.widget.item.TextItem;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

//public class LSNetListActivity extends GDActivity {
public class LSNetListActivity extends GDListActivity {
	
	private final int OPTIONS = 0;
	private final int HELP = 1;
	private QuickActionWidget quickActions;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setActionBarContentView(R.layout.netlist);
        
        initActionBar();
        initQuickActionBar();
        
        ItemAdapter adapter = new ItemAdapter(this);
        adapter.add(createTextItem(0,"Xarxa 1"));
        adapter.add(createTextItem(1,"Xarxa 2"));
        adapter.add(createTextItem(2,"Xarxa 3"));
        adapter.add(createTextItem(3,"Xarxa 4"));
        adapter.add(createTextItem(4,"Xarxa 5"));
        adapter.add(createTextItem(5,"Xarxa 6"));
        adapter.add(createTextItem(6,"Xarxa 7"));
        adapter.add(createTextItem(7,"Xarxa 8"));
        adapter.add(createTextItem(8,"Xarxa 9"));
        adapter.add(createTextItem(9,"Xarxa 10"));
        setListAdapter(adapter);
    }
	
	private TextItem createTextItem(int stringId, String strItem) {
        //final TextItem textItem = new TextItem(getString(stringId));
        final TextItem textItem = new TextItem(strItem);
        textItem.setTag(strItem);
        return textItem;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final TextItem textItem = (TextItem) l.getAdapter().getItem(position);
        
        Toast.makeText(getApplicationContext(), "Has pulsado la posición " + position +", Item " + textItem.getTag(), Toast.LENGTH_LONG).show();
    }
	
	private void initActionBar() {
			
    	addActionBarItem(Type.Add,OPTIONS);
    	addActionBarItem(Type.Help,HELP);
	}
	
	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		
		 switch (item.getItemId()) {
    	
    	case OPTIONS:
    		Toast.makeText(getApplicationContext(), "Has pulsado el boton OPTIONS", Toast.LENGTH_SHORT).show();
    		quickActions.show(item.getItemView());
    		break;
    	case HELP:
    		Toast.makeText(getApplicationContext(), "Has pulsado el boton HELP", Toast.LENGTH_SHORT).show();
    		
    		break;
    	default:
    		return super.onHandleActionBarItemClick(item, position);
    	}
    	
		return true;
	} 
 
	private void initQuickActionBar()
	{
		quickActions = new QuickActionBar(this);
		quickActions.addQuickAction(new QuickAction(this,R.drawable.find,"Cercar"));
		quickActions.addQuickAction(new QuickAction(this,R.drawable.filter,"Filtrar"));
		quickActions.setOnQuickActionClickListener(new OnQuickActionClickListener() {

			@Override
			public void onQuickActionClicked(QuickActionWidget widget, int position) {
				Toast.makeText(LSNetListActivity.this, "Item " + position + " pulsado", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
}
