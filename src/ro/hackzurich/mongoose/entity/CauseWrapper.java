package ro.hackzurich.mongoose.entity;

import java.util.List;
import com.google.gson.Gson;

public class CauseWrapper {
	public List<Cause> causeList;

	public static CauseWrapper fromJson(String s) {
		return new Gson().fromJson(s, CauseWrapper.class);
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}

