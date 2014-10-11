package ro.hackzurich.mongoose.entity;

import java.util.List;

import com.google.gson.Gson;

public class PendingWrapper {
	public List<Pending> pendingList;

	public static PendingWrapper fromJson(String s) {
		return new Gson().fromJson(s, PendingWrapper.class);
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
