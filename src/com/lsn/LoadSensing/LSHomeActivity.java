package com.lsn.LoadSensing;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBar;
import greendroid.widget.ActionBarItem;
import greendroid.widget.NormalActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class LSHomeActivity extends GDActivity {
    
	private final int MORE = 0;
	private final int HELP = 1;
	private final int LOG_OUT = 2;
	private QuickActionWidget quickActions;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.dshb_home);
        getActionBar().setType(ActionBar.Type.Empty);
        
        initActionBar();
        initQuickActionBar();
        
        Bundle bundle = getIntent().getExtras();
        
        if (bundle != null)
        {
        	Toast.makeText(getApplicationContext(), "Bienvenido usuario "+ bundle.getString("USER") +" con password "+ bundle.getString("PASS"), Toast.LENGTH_LONG).show();
    	}       
        
        
//        TextView txtMessage = (TextView)findViewById(R.id.txtHomeMessage);
//        txtMessage.setText("Hello World");
//        txtMessage.setVisibility(TextView.VISIBLE);
        
        
        DashboardClickListener dbClickListener = new DashboardClickListener();
        findViewById(R.id.dsh_btn_netList).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_netMaps).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_QRCode).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_Faves).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_AR).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_netCloser).setOnClickListener(dbClickListener);
    }

    @Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		
    	Intent i = null;
    	
    	switch (item.getItemId()) {
    	
    	case MORE:
    		quickActions.show(item.getItemView());
    		//Toast.makeText(getApplicationContext(), "Has pulsado el boton MORE", Toast.LENGTH_SHORT).show();
    		//i = new Intent(LSHomeActivity.this,LSConfigActivity.class);
    		break;
    	case HELP:
    		Toast.makeText(getApplicationContext(), "Has pulsado el boton HELP", Toast.LENGTH_SHORT).show();
    		i = new Intent(LSHomeActivity.this,LSHelpActivity.class);
    		break;
    	case LOG_OUT:
    		showLogOutDialog();
    		//Toast.makeText(getApplicationContext(), "Has pulsado el boton LOG_OUT", Toast.LENGTH_SHORT).show();
    		//i = new Intent(LSHomeActivity.this,LSAboutActivity.class);
    		//i = new Intent(LSHomeActivity.this,LSInfoActivity.class);
    		break;
    	
    	default:
    		return super.onHandleActionBarItemClick(item, position);
    	}
    	if (i!=null){
			startActivity(i);
		}
		return true;
	}

	private void initActionBar() {
		
		addActionBarItem(Type.Add,MORE);
    	addActionBarItem(Type.Help,HELP);
    	addActionBarItem(getActionBar()
                .newActionBarItem(NormalActionBarItem.class)
                .setDrawable(R.drawable.gd_action_bar_exit)
                .setContentDescription(R.string.abtxtLogOut), LOG_OUT);
	}

	private void initQuickActionBar()
	{
		quickActions = new QuickActionBar(this); 
		quickActions.addQuickAction(new QuickAction(this,android.R.drawable.ic_menu_preferences,getString(R.string.abtxtConfiguration)));
		quickActions.addQuickAction(new QuickAction(this,android.R.drawable.ic_menu_info_details,getString(R.string.abtxtInformation)));
		quickActions.setOnQuickActionClickListener(new OnQuickActionClickListener() {

			@Override
			public void onQuickActionClicked(QuickActionWidget widget, int position) {
				
				Intent i = null;
				
				switch (position)
				{
				case 0:
					i = new Intent(LSHomeActivity.this,LSConfigActivity.class);
					break;
				case 1:
					i = new Intent(LSHomeActivity.this,LSInfoActivity.class);
					break;
				}
				
				if (i!=null){
					startActivity(i);
				}
			}
		});
	}
	
	
	
	private class DashboardClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent i = null;
			switch (v.getId()) {
			case R.id.dsh_btn_netList:
				i = new Intent(LSHomeActivity.this,LSNetListActivity.class);
				break;
			case R.id.dsh_btn_netMaps:
				i = new Intent(LSHomeActivity.this,LSNetMapsActivity.class);
				break;
			case R.id.dsh_btn_QRCode:
				i = new Intent(LSHomeActivity.this,LSQRCodeActivity.class);
				break;
			case R.id.dsh_btn_Faves:
				i = new Intent(LSHomeActivity.this,LSFavesActivity.class);
				break;
			case R.id.dsh_btn_AR:
				i = new Intent(LSHomeActivity.this,LSAugRealActivity.class);
				break;
			case R.id.dsh_btn_netCloser:
				i = new Intent(LSHomeActivity.this,LSNetCloserActivity.class);
				break;
			default:
				break;
			}
			if (i!=null){
				startActivity(i);
			}
		}
    }
	
	private void showLogOutDialog() {
		
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage(R.string.dialogLogOut)
		.setPositiveButton(R.string.txtYes, new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int id) {
			Intent i = new Intent(LSHomeActivity.this,LSLoginActivity.class);
			startActivity(i);
		}
		})
		.setNegativeButton(R.string.txtNo, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = alt_bld.create();
		alert.setTitle(R.string.txtExit);
		alert.show();
	}
	
}