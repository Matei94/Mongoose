package ro.hackzurich.mongoose;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
		ListView lstvwChallenges = 
				(ListView) activity.findViewById(R.id.lstvwChallenges);
		
		ChallengeArrayAdapter adapter = new ChallengeArrayAdapter(activity,
				new String[] {
					"first challenge",
					"second challenge"
				});
		lstvwChallenges.setAdapter(adapter);
		
		lstvwChallenges.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(activity, "pressed", Toast.LENGTH_SHORT).show();
			}
		});
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
