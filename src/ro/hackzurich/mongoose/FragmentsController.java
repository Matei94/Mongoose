package ro.hackzurich.mongoose;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

public class FragmentsController {
	/* Needed for findViewById */
	private Activity activity;
	
	private static int[] fragmentIds = {
		R.layout.fragment_challenges,
		R.layout.fragment_notifications,
		R.layout.fragment_pendings,
		R.layout.fragment_logout };

	private static String[] fragmentNames = {
		"Challenges",
		"Notifications",
		"Pendings",
		"Log out" };

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
		case R.layout.ranking:
			setUpRanking();
			break;
		default:
			Log.d("MONGOOSE", "Unexpected behaviour. fragmentId = " + 
					fragmentId);
			break;
		}
	}

	/* Getters */
	public static int[] getFragmentIds() {
		return fragmentIds;
	}

	public static String[] getFragmentNames() {
		return fragmentNames;
	}
	
	/* Set ups */
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
	
	private void setUpRanking() {
		TextView tv = (TextView) activity.findViewById(R.id.txtvwRanking);
		tv.setText("Ranking Programatically");
	}

}
