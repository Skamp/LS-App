package com.lsn.LoadSensing;
import com.lsn.LoadSensing.R;
import com.lsn.LoadSensing.ui.CustomToast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LSLoginActivity extends Activity {

	private String strUser;
	private String strPass;
	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		prefs = getSharedPreferences("LSLogin",Context.MODE_PRIVATE);
		
		Button btnLogin = (Button)findViewById(R.id.btnLogin);
		EditText edtLogin = (EditText)findViewById(R.id.edtLogin);
		EditText edtPassword = (EditText)findViewById(R.id.edtPassword);
		
		edtLogin.setText(prefs.getString("user", ""));
		edtPassword.setText(prefs.getString("pass", ""));
		
		btnLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				EditText edtLogin = (EditText)findViewById(R.id.edtLogin);
				EditText edtPassword = (EditText)findViewById(R.id.edtPassword);
				strUser = edtLogin.getText().toString();
				strPass = edtPassword.getText().toString();
				
				if ((strUser.equals("sergio")) && (strPass.equals("1234"))) {
					
					Intent intent = new Intent(LSLoginActivity.this,LSHomeActivity.class);
					
					Bundle bundle = new Bundle();
					bundle.putString("USER", strUser);
					bundle.putString("PASS", strPass);
					intent.putExtras(bundle);
					
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("user", strUser);
					editor.putString("pass", strPass);
					editor.commit();
					
					startActivity(intent);
				}
				else
				{
					CustomToast.showCustomToast(LSLoginActivity.this,R.string.msg_BadLoginPass,CustomToast.IMG_AWARE,CustomToast.LENGTH_SHORT);
				}
			}
		});
	}

	
	
}
