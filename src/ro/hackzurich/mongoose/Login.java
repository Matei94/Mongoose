package ro.hackzurich.mongoose;

import ro.hackzurich.mongoose.settings.Settings;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.UserSettingsFragment;

public class Login extends FragmentActivity {
	 private UserSettingsFragment userSettingsFragment;
	 private final String TAG = "LOGIN";

	 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.login);
         FragmentManager fragmentManager = getSupportFragmentManager();
         userSettingsFragment = (UserSettingsFragment) fragmentManager.findFragmentById(R.id.login_fragment);
         userSettingsFragment.setSessionStatusCallback(new Session.StatusCallback() {
             @Override
             public void call(Session session, SessionState state, Exception exception) {
                 Log.d("LoginUsingLoginFragmentActivity", String.format("New session state: %s", state.toString()));
             }
         });
	 }
	 
	 

	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
         userSettingsFragment.onActivityResult(requestCode, resultCode, data);
         super.onActivityResult(requestCode, resultCode, data);
         Intent mainActivityIntent = new Intent(this, MainActivity.class);
         
         Request.newMeRequest(Session.getActiveSession(), new com.facebook.Request.GraphUserCallback() {
			
        	 	@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						Settings.setUsername(user.getName());
					}
				}
		}).executeAsync();
         
        startActivity(mainActivityIntent);
	 }
}
