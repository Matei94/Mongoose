package ro.hackzurich.mongoose;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Login extends Activity {
	private Button btnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent goToMainActivityIntent = new Intent(Login.this, 
						MainActivity.class);
				startActivity(goToMainActivityIntent);
			}
		});
	}
}
