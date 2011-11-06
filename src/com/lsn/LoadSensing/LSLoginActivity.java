package com.lsn.LoadSensing;
import com.lsn.LoadSensing.R;
import com.lsn.LoadSensing.ui.CustomToast;
import com.lsn.LoadSensing.encript.LSSecurity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class LSLoginActivity extends Activity {
	// user
	private String strUser;
	// pass
	private String strPass;
	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		prefs = getSharedPreferences("LSLogin",Context.MODE_PRIVATE);
		
		PreferenceManager.setDefaultValues(this, R.xml.maps, false);

		
		Button btnLogin = (Button)findViewById(R.id.btnLogin);
		EditText edtLogin = (EditText)findViewById(R.id.edtLogin);
		EditText edtPassword = (EditText)findViewById(R.id.edtPassword);
		
		edtLogin.setText(LSSecurity.rot13Decode(prefs.getString("user", "")));
		edtPassword.setText(LSSecurity.rot13Decode(prefs.getString("pass", "")));
		
		btnLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				EditText edtLogin = (EditText)findViewById(R.id.edtLogin);
				EditText edtPassword = (EditText)findViewById(R.id.edtPassword);
				strUser = edtLogin.getText().toString();
				strPass = edtPassword.getText().toString();
				
				// valid user
				if ((strUser.equals("sergio")) && (strPass.equals("1234"))) {
					
					Intent intent = new Intent(LSLoginActivity.this,LSHomeActivity.class);
					
					Bundle bundle = new Bundle();
					bundle.putString("USER", strUser);
					bundle.putString("PASS", strPass);
					intent.putExtras(bundle);
					
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("user", LSSecurity.rot13Encode(strUser));
					editor.putString("pass", LSSecurity.rot13Encode(strPass));
					editor.commit();
					
					startActivity(intent);
				}
				else
				{ // user don't exist
					CustomToast.showCustomToast(LSLoginActivity.this,R.string.msg_BadLoginPass,CustomToast.IMG_AWARE,CustomToast.LENGTH_SHORT);
					edtLogin.setText("");
					edtPassword.setText("");
				}
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();          
 
        // default: google maps
        editor.putString("maps", "google");
        editor.commit();

	}  
}
