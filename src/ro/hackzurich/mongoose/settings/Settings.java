package ro.hackzurich.mongoose.settings;

import ro.hackzurich.mongoose.*;

public class Settings {
	private static String username = "";
	private static int score = 42;
	private static String userId = "23";
	
	private static int challengeId = 0;
	private static String challengeDesc = "No description available";

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
	
	public static int getChallengeId() {
		return challengeId;
	}
	
	public static String getChallengeDesc() {
		return challengeDesc;
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
	
	public static void setChallengeId(int challengeId) {
		Settings.challengeId = challengeId;
	}
	
	public static void setChallengeDesc(String challengeDesc) {
		Settings.challengeDesc = challengeDesc;
	}
}
