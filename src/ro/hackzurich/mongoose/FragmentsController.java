package ro.hackzurich.mongoose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ro.hackzurich.mongoose.MainActivity.PlaceholderFragment;
import ro.hackzurich.mongoose.entity.Cause;
import ro.hackzurich.mongoose.entity.CauseWrapper;
import ro.hackzurich.mongoose.entity.Notification;
import ro.hackzurich.mongoose.entity.NotificationWrapper;
import ro.hackzurich.mongoose.settings.Settings;
import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentsController {
	private List<String> challengesList = new ArrayList<String>();
	private List<String> notificationsList = new ArrayList<String>();
	
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
	
	private void setUpPendingsFragment() {
		ListView lstvwChallenges = 
				(ListView) activity.findViewById(R.id.lstvwPendings);
		
		MyArrayAdapter adapter = new MyArrayAdapter(activity,
				new String[] {
					"first pending",
					"second pending"
				});
		
		lstvwChallenges.setAdapter(adapter);
		
		lstvwChallenges.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(activity, "selected " + arg2, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void setUpLogOutFragment() {
		TextView tv = (TextView) activity.findViewById(R.id.txtvwLogout);
		tv.setText("LogOut Programatically");
	}
	
	private void setUpRanking() {
		TextView tv = (TextView) activity.findViewById(R.id.txtvwRanking);
		Button btnAux = (Button) activity.findViewById(R.id.btnAux);
		
		btnAux.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
				fragmentManager
						.beginTransaction()
						.replace(
								R.id.container,
								PlaceholderFragment.newInstance(0,
										R.layout.camera))
						.commit();
			}
		});
		
		
		tv.setText("Ranking Programatically");
	}

	private void setUpChallengesFragment() {
		String urlString = "http://172.27.5.129:8080/mongoose/webresources/" + 
				"ro.hackzurich.mongoose.entity.cause";
		URL url = null;
		
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		new AsyncTask<URL, Integer, Long>() {

			@Override
			protected Long doInBackground(URL... params) {
				URL url = params[0];
				HttpURLConnection con;
				
				try {
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					con.setRequestProperty("User-Agent", "Mozilla/5.0");
			 
					int responseCode = con.getResponseCode();
			 
					BufferedReader in = new BufferedReader(
					        new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();
			 
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();
					
					CauseWrapper cw = CauseWrapper.fromJson(
							"{\"causeList\":" + response + "}");
					
					Log.e("MONGOOSE", "response: " + cw);
			 
					List<Cause> causeList = cw.causeList;
					
					for(Cause c : causeList) {
						challengesList.add(c.toString());
					}
				} catch (IOException e) {
					Log.e("MONGOOSE", "Connection error: " + e);
					e.printStackTrace();
				}
				 
				return null;
			}
			
			protected void onPostExecute(Long result) {
				ListView lstvwChallenges = 
						(ListView) activity.findViewById(R.id.lstvwChallenges);
				
				if (lstvwChallenges == null) return;
				
				MyArrayAdapter adapter = new MyArrayAdapter(activity, 
						challengesList.toArray(new String[0]));
				
				lstvwChallenges.setAdapter(adapter);
				
				lstvwChallenges.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {
						Toast.makeText(activity, "selected " + arg2, Toast.LENGTH_SHORT).show();
					}
				});
			};
		}.execute(url);
	}
	
	private void setUpNotificationsFragment() {
		String urlString = "http://172.27.5.129:8080/mongoose/webresources/" + 
				"ro.hackzurich.mongoose.entity.notification/" + 
				Settings.getUserId() + "/notifications";
		URL url = null;
		
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		new AsyncTask<URL, Integer, Long>() {

			@Override
			protected Long doInBackground(URL... params) {
				URL url = params[0];
				HttpURLConnection con;
				
				try {
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					con.setRequestProperty("User-Agent", "Mozilla/5.0");
			 
					int responseCode = con.getResponseCode();
			 
					BufferedReader in = new BufferedReader(
					        new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();
			 
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();
					
					NotificationWrapper nw;
					try {
						nw = NotificationWrapper.fromJson(
							"{\"notificationList\":" + response + "}");
					} catch (Exception e) {
						Log.e("MONGOOSE", "catch " + response);
						return 1L;
					}
					
					Log.e("MONGOOSE", "response: " + nw);
			 
					List<Notification> notificationList = nw.notificationList;
					
					for(Notification n : notificationList) {
						notificationsList.add(n.toString());
					}
				} catch (IOException e) {
					Log.e("MONGOOSE", "Connection error: " + e);
					e.printStackTrace();
				}
				 
				return null;
			}
			
			protected void onPostExecute(Long result) {
				ListView lstvwNotifications = 
						(ListView) activity.findViewById(R.id.lstvwNotifications);
				
				if (lstvwNotifications == null) return;
				
				MyArrayAdapter adapter = new MyArrayAdapter(activity, 
						notificationsList.toArray(new String[0]));
				
				lstvwNotifications.setAdapter(adapter);
				
				lstvwNotifications.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {
						Toast.makeText(activity, "selected " + arg2, Toast.LENGTH_SHORT).show();
					}
				});
			};
		}.execute(url);
	}
}
