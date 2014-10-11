package ro.hackzurich.mongoose.settings;

import ro.hackzurich.mongoose.*;

public class Settings {
	private static String username = "Username";
	private static int score = 42;
	private static String userId = "23";

	/* Getters */
	public static String getUsername() {
		return username;
	}
	
	public static int getScore() {
		return score;
	}
	
	public static String getUserId() {
		return userId;
	}
	
	/* Setters */
	public static void setUsername(String username) {
		Settings.username = username;
	}
	
	public static void setScore(int score) {
		Settings.score = score;
	}
	
	public static void setUserId(String userId) {
		Settings.userId = userId;
	}
}
