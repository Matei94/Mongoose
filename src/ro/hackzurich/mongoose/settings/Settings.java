package ro.hackzurich.mongoose.settings;

import ro.hackzurich.mongoose.*;

public class Settings {
	private static String username = "Username";
	
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

	/* Getters */
	public static int[] getFragmentIds() {
		return fragmentIds;
	}

	public static String[] getFragmentNames() {
		return fragmentNames;
	}
	
	public static String getUsername() {
		return username;
	}
	
	/* Setters */
	public static void setUsername(String username) {
		Settings.username = username;
	}
}
