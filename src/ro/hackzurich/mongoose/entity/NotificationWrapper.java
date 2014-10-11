package ro.hackzurich.mongoose.entity;

import java.util.List;

import com.google.gson.Gson;

public class NotificationWrapper {
	public List<Notification> notificationList;

	public static NotificationWrapper fromJson(String s) {
		return new Gson().fromJson(s, NotificationWrapper.class);
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
