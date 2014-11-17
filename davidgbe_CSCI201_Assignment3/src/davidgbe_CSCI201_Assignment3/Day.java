package davidgbe_CSCI201_Assignment3;

public class Day {
	private int dayOfWeek;
	private int dayOfMonth;
	private int year;
	private int month;
	
	public Day(int dayOfWeek, int dayOfMonth, int year, int month) {
		this.dayOfWeek = dayOfWeek;
		this.dayOfMonth = dayOfMonth;
		this.year = year;
		this.month = month;
		
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public int getDayOfWeek() {
		return this.dayOfWeek;
	}
	
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public int getDayOfMonth() {
		return this.dayOfMonth;
	}
	
	public String getDayOfMonthWithSuffix() {
		int lastNum = this.dayOfMonth % 10;
		if(lastNum == 1) {
			return this.dayOfMonth + "st";
		}
		else if (lastNum == 2) {
			return this.dayOfMonth + "nd";
		}
		else if (lastNum == 3) {
			return this.dayOfMonth + "rd";
		}
		else {
			return this.dayOfMonth + "th";
		}
	}
}
