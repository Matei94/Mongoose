package ro.hackzurich.mongoose.settings;

import ro.hackzurich.mongoose.*;

public class Settings {
	private static String username = "Username";

	/* Getters */
	public static String getUsername() {
		return username;
	}
	
	/* Setters */
	public static void setUsername(String username) {
		Settings.username = username;
	}
}
