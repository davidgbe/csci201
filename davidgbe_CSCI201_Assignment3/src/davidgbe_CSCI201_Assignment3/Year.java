package davidgbe_CSCI201_Assignment3;

public class Year {
	
	private Month[] months = new Month[12];
	private int year;
	
	public Year(int startDay, int[] months, String[] monthNames, int year) {
		this.year = year;
		int currDay = startDay;
		for(int i = 0; i < 12; i++) {
			this.months[i] = new Month(months[i], currDay, monthNames[i], year, i);
			currDay += months[i];
			currDay = currDay % 7;
		}
	}
	
	public Month getMonth(int pos) {
		return months[pos];
	}
}
