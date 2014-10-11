package ro.hackzurich.mongoose;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

public class FragmentsController {
	/* Needed for findViewById */
	private Activity activity;

	public FragmentsController(Activity activity, int fragmentId) {
		this.activity = activity;
		
		switch (fragmentId) {
		case R.layout.fragment_challenges:
			setUpChallengesFragment();
			break;
		case R.layout.fragment_notifications:
			setUpNotificationsFragment();
			break;
		case R.layout.fragment_pendings:
			setUpPendingsFragment();
			break;
		case R.layout.fragment_logout:
			setUpLogOutFragment();
			break;
		default:
			Log.d("MONGOOSE", "Unexpected behaviour. fragmentId = " + 
					fragmentId);
			break;
		}
	}
	
	private void setUpChallengesFragment() {
		TextView tv = (TextView) activity.findViewById(R.id.txtvwChallenges);
		tv.setText("Challenges Programatically");
	}

	private void setUpNotificationsFragment() {
		TextView tv = (TextView) activity.findViewById(R.id.txtvwNotifications);
		tv.setText("Notifications Programatically");
	}
	
	private void setUpPendingsFragment() {
		TextView tv = (TextView) activity.findViewById(R.id.txtvwPendings);
		tv.setText("Pendings Programatically");
	}
	
	private void setUpLogOutFragment() {
		TextView tv = (TextView) activity.findViewById(R.id.txtvwLogout);
		tv.setText("LogOut Programatically");
	}
	
}
