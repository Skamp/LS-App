package com.lsn.LoadSensing;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.NormalActionBarItem;

public class LSHelpActivity extends GDActivity { 
	private final int BACK = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.help_menu);
          
        /* Help 2
        setActionBarContentView(R.layout.help_menu_2);
        DashboardClickListener dbClickListener = new DashboardClickListener();
        findViewById(R.id.dsh_btn_netList).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_netMaps).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_QRCode).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_Faves).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_AR).setOnClickListener(dbClickListener);
        findViewById(R.id.dsh_btn_netCloser).setOnClickListener(dbClickListener);
		*/
        
        
        initActionBar();
    }
	
	private void initActionBar() {
		
    	addActionBarItem(getActionBar()
				.newActionBarItem(NormalActionBarItem.class)
				.setDrawable(R.drawable.gd_action_bar_back)
				.setContentDescription("Back"), BACK);
	}
	
	 @Override
		public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
			
	    	
	    	
	    	switch (item.getItemId()) {
	    	
	    	case BACK:
	    		Toast.makeText(getApplicationContext(), "Has pulsado el boton BACK", Toast.LENGTH_SHORT).show();
	    	
	    		break;
	    	
	    	default:
	    		return super.onHandleActionBarItemClick(item, position);
	    	}
	    	
			return true;
		} 
	 
	 /* Help 2
	 private class DashboardClickListener implements OnClickListener {

		 TextView fld = (TextView)findViewById(R.id.textView);
		 

		 @Override
		 public void onClick(View v) {
			 switch (v.getId()) {
			 case R.id.dsh_btn_netList:
				 fld.setText("Dona la possibilitat de mostrar el llistat " +
				 		"de totes les xarxes registrades al sistema on l'usuari " +
				 		"té accés.");
				 break;
			 case R.id.dsh_btn_netMaps:
				 fld.setText("Dona la possibilitat de visualitzar totes " +
						 "les xarxes registrades al sistema on l'usuari " +
						 "té accés.");	
				 break;
			 case R.id.dsh_btn_QRCode:
				 fld.setText("Es pot accedir directament al dispositiu " +
						 "llegint el bidicode/QR Code i accedir a la " +
						 "informació del node.");				
				 break;
			 case R.id.dsh_btn_Faves:
				 fld.setText("Mostra les xarxes, sensors e imatges que " +
				 		"l'usuari té com a preferides per poder tenir-les " +
				 		"accessible més ràpidament.");			 
				 break;
			 case R.id.dsh_btn_AR:
				 fld.setText("Estant in situ en la mateixa zona on es troba la " +
					 		"xarxa a analitzar, es pot geoposicionar cada node " +
					 		"de la xarxa observant en temps real la posició " +
					 		"dels nodes.");	
				 break;
			 case R.id.dsh_btn_netCloser:
				 fld.setText("Permet, mitjançant el GPS del dispositiu, detectar " +
					 		"les xarxes properes a la ubicació en la que es troba");	
				 break;
			 default:
				 break;
			 }
		 }
	 }
	 */
}
//.setDrawable(new ActionBarDrawable(getResources(),R.drawable.map)),
//.setContentDescription("Direction"), R.id.action_bar_direction);
