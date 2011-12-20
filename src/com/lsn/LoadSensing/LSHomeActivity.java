package com.lsn.LoadSensing;

//import com.lsn.LoadSensing.ui.CustomDialog;

import com.lsn.LoadSensing.ui.CustomToast;
//import com.readystatesoftware.mapviewballoons.R;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBar;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class LSHomeActivity extends GDActivity {
    
	private final int MORE = 0;
	private final int HELP = 1;
//	private final int LOG_OUT = 2;
	private QuickActionWidget quickActions;
	private String typeMaps = null;
	public static String idSession;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.dshb_home);
        getActionBar().setType(ActionBar.Type.Normal);
        setTitle(R.string.act_lbl_homHome);
        
        ImageButton	mHomeButton = (ImageButton) findViewById(R.id.gd_action_bar_home_item);
		
        mHomeButton.setImageDrawable(getResources().getDrawable(R.drawable.gd_action_bar_exit));
		mHomeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				showLogOutDialog();
			}
		});
        
        initActionBar();
        initQuickActionBar();
        
        Bundle bundle = getIntent().getExtras();
        
        if (bundle != null)
        {
        	idSession = bundle.getString("SESSION");
        	CustomToast.showCustomToast(this,this.getString(R.string.msg_Welcome) +" " + bundle.getString("USER")+" " + idSession,CustomToast.IMG_CORRECT,CustomToast.LENGTH_LONG);
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
        
        PreferenceManager.setDefaultValues(this, R.xml.maps, false);
        
    }
    
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        typeMaps=settings.getString("maps", "");
    }    
    
    @Override
	public void onBackPressed() {
		
    	showLogOutDialog();
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
//    	case LOG_OUT:
//    		showLogOutDialog();
//    		//Toast.makeText(getApplicationContext(), "Has pulsado el boton LOG_OUT", Toast.LENGTH_SHORT).show();
//    		//i = new Intent(LSHomeActivity.this,LSAboutActivity.class);
//    		//i = new Intent(LSHomeActivity.this,LSInfoActivity.class);
//    		break;
    	
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
    	//addActionBarItem(getActionBar()
    	//        .newActionBarItem(NormalActionBarItem.class)
    	//        .setDrawable(R.drawable.gd_action_bar_exit)
    	//        .setContentDescription(R.string.abtxtLogOut), LOG_OUT);
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
				 if (typeMaps.equals("google"))
				 {
					 i = new Intent(LSHomeActivity.this,LSNetMapsActivity.class);
				 }
				 else   
				 {
					 CustomToast.showCustomToast(LSHomeActivity.this,R.string.settings_maps,CustomToast.IMG_EXCLAMATION,CustomToast.LENGTH_SHORT);
				 }
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
				Bundle bundle = new Bundle();
				bundle.putString("SESSION", idSession);
				i.putExtras(bundle);
				startActivity(i);
			}
		}
    }
	
	private void showLogOutDialog() {
		
		AlertDialog.Builder dialogLogOut = new AlertDialog.Builder(this);
		dialogLogOut.setMessage(R.string.dialogLogOut)
		.setPositiveButton(R.string.txtYes, new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int id) {
			// Go to Login Activity erasing all other activities with FLAG_ACTIVITY_CLEAR_TOP
			Intent i = new Intent(LSHomeActivity.this,LSLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		})
		.setNegativeButton(R.string.txtNo, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alertLogOut = dialogLogOut.create();
		alertLogOut.setTitle(R.string.txtExit);
		alertLogOut.show();
//		CustomDialog.Builder dialogLogOut = new CustomDialog.Builder(this);
//		dialogLogOut.setTitle(R.string.txtExit)
//			.setMessage(R.string.dialogLogOut)
//			.setPositiveButton(R.string.txtYes, new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int id) {
//					// Go to Login Activity erasing all other activities with FLAG_ACTIVITY_CLEAR_TOP
//					Intent i = new Intent(LSHomeActivity.this,LSLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					startActivity(i);
//				}
//			})
//			.setNegativeButton(R.string.txtNo, new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int id) {
//					dialog.cancel();
//				}
//			});
//		Dialog alertLogOut = dialogLogOut.create();
//		alertLogOut.show();
	}
	
}