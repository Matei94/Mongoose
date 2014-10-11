package ro.hackzurich.mongoose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		
		MyArrayAdapter adapter = new MyArrayAdapter(activity,
				new String[] {
					"first challenge",
					"second challenge"
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

	private void setUpNotificationsFragment() {
		ListView lstvwChallenges = 
				(ListView) activity.findViewById(R.id.lstvwNotifications);
		
		MyArrayAdapter adapter = new MyArrayAdapter(activity,
				new String[] {
					"first notification",
					"second notification"
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
		tv.setText("Ranking Programatically");
		
		sendGet();
	}

	// HTTP GET request
	private void sendGet() {
		String urlString = "http://172.27.5.129:8080/mongoose/webresources/" + 
				"ro.hackzurich.mongoose.entity.challenge";
		URL url = null;
		
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		new AsyncTask<URL, Integer, Long>() {

			@Override
			protected Long doInBackground(URL... params) {
				URL url = params[0];
				HttpURLConnection con;
				
				try {
					con = (HttpURLConnection) url.openConnection();
					
					// optional default is GET
					con.setRequestMethod("GET");
			 
					//add request header
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
			 
					//print result
					Log.e("MONGOOSE", "response = " + response);
				} catch (IOException e) {
					Log.e("MONGOOSE", "Connection error: " + e);
					e.printStackTrace();
				}
				 
				return null;
			}
		}.execute(url);
	}
	
}
