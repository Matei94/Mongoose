package ro.hackzurich.mongoose;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super(Constants.GCM_SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.i(TAG, "Device registered to GCM with id: " + regId);
		//trimite user-ul la server
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.i(TAG, "Device unregistered from GCM");
		//editeaza user-ul
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Message received");
	}

	@Override
	protected void onError(Context context, String error) {
		Log.i(TAG, "Error occured: " + error);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		Log.i(TAG, "Received recoverable error: " + errorId);
		return super.onRecoverableError(context, errorId);
	}

}
