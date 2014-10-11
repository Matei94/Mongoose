package ro.hackzurich.mongoose.entity;

public class Pending {
	private int causeId;
	private String fromId;
	private int id;
	private String imageUrl;
	private int status;
	private String toId;
	private String videoUrl;
	
	@Override
	public String toString() {
		return causeId + ", " + fromId + ", " + id + ", " + imageUrl + ", " +
				status + ", " + toId + ", " + videoUrl;
	}
	
	public int getCauseId() {
		return causeId;
	}
	public void setCauseId(int causeId) {
		this.causeId = causeId;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	
}
