package davidgbe_CSCI201_Assignment3;

import java.util.ArrayList;

public class Month {
	private int numDays;
	private Day[] days;
	private String name;
	private int year;
	private int monthNum;
	
	public Month(int numDays, int startDay, String name, int year, int month) {
		this.year = year;
		this.monthNum = month;
		this.name = name;
		this.numDays = numDays;
		this.days = new Day[numDays];
		for(int i = 0; i < numDays; i++) {
			setDay(i, (startDay + i) % 7);
		}
	}
	
	public void setDay(int num, int dayOfWeek) {
		this.days[num] = new Day(dayOfWeek, num + 1, this.year, this.monthNum);
	}
	
	public Day getDay(int num) {
		return this.days[num];
	}
	
	public int getNumDays() {
		return this.numDays;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Day> getAllDays() {
		ArrayList<Day> days = new ArrayList<Day>();
		for(int i = 0; i < numDays; i++) {
			days.add(this.days[i]);
		}
		return days;
	}
}
