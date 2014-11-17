package davidgbe_CSCI201_Assignment3;

public class CalEvent {
	private String startTime;
	private String endTime;
	private String location;
	private String title;
	
	public CalEvent(String start, String end, String loc, String title) {
		this.startTime = start;
		this.endTime = end;
		this.location = loc;
		this.title = title;
	}
	
	public String getStartTime() {
		return this.startTime;
	}
	
	public String getEndTime() {
		return this.endTime;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String asString() {
		return this.title + " - " + this.location + " from " + this.startTime + " - " + this.endTime; 
	}
}
