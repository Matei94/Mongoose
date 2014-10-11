package ro.hackzurich.mongoose.settings;

public class Settings {
	private static String username = "Username";
	private static int score = 42;

	/* Getters */
	public static String getUsername() {
		return username;
	}
	
	public static int getScore() {
		return score;
	}
	
	/* Setters */
	public static void setUsername(String username) {
		Settings.username = username;
	}
	
	public static void setScore(int score) {
		Settings.score = score;
	}
}
