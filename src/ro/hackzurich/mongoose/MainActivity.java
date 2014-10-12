package ro.hackzurich.mongoose;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.json.JSONException;

import ro.hackzurich.mongoose.settings.CameraSettings;
import ro.hackzurich.mongoose.settings.Settings;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gcm.GCMRegistrar;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	
	HttpURLConnection connection;

	/**
	 * Used to store the unique registration id for the GCM service.
	 */
	private String regId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// GCM
		GCMRegistrar.checkDevice(this);
		regId = GCMRegistrar.getRegistrationId(this);
		if (regId != null && regId.length() == 0) {
			GCMRegistrar.register(this, Constants.GCM_SENDER_ID);
		}

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}
	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(
						R.id.container,
						PlaceholderFragment.newInstance(position + 1,
								FragmentsController.getFragmentIds()[position]))
				.commit();
	}

	public void onSectionAttached(int number) {
		/*
		 * Don't delete this, maybe we'll need it later switch (number) { case
		 * 1: mTitle = getString(R.string.title_section1); break; case 2: mTitle
		 * = getString(R.string.title_section2); break; case 3: mTitle =
		 * getString(R.string.title_section3); break; }
		 */
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(Settings.getUsername());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		private static int fragmentId;

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber,
				int fragmentId) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);

			PlaceholderFragment.fragmentId = fragmentId;

			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(fragmentId, container, false);
			return rootView;
		}

		@Override
		public void onActivityCreated(@Nullable Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			new FragmentsController(getActivity(), fragmentId);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == CameraSettings.TAKE_PICTURE) {
			if(resultCode == Activity.RESULT_OK) {

			} else if ( resultCode == Activity.RESULT_CANCELED ) {
				File pic = CameraSettings.getOutFile();
				pic.delete();
			}
			CameraSettings.setActivityThis();
		} else if (requestCode == 42) { //Photo 
			if (resultCode == Activity.RESULT_OK) {
		        PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
		        if (confirm != null) {
		            try {
		                Log.i("MONGOOSE", confirm.toJSONObject().toString(4));
	
		                // TODO: send 'confirm' to your server for verification.
		                // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
		                // for more details.
	
		            } catch (JSONException e) {
		                Log.e("MONGOOSE", "an extremely unlikely failure occurred: ", e);
		            }
		        }
		    }
		    else if (resultCode == Activity.RESULT_CANCELED) {
		        Log.i("MONGOOSE", "The user canceled.");
		    }
		    else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
		        Log.i("MONGOOSE", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
		    }
		} else if (requestCode == 128) { // VIDEO
			
		}
	}

}
