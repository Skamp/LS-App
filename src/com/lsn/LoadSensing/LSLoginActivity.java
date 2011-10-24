package com.lsn.LoadSensing;
import com.lsn.LoadSensing.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LSLoginActivity extends Activity {

	private String strUser;
	private String strPass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		Button btnLogin = (Button)findViewById(R.id.btnLogin);
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
					
					startActivity(intent);
				}
				else
				{
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_BadLoginPass), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	
	
}
