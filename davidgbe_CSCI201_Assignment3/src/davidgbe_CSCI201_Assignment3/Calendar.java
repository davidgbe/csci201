package davidgbe_CSCI201_Assignment3;

import java.util.ArrayList;

public class Calendar {

	private String[] monthNames = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
	private int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	private ArrayList<Year> years = new ArrayList<Year>();
	private int startYear;
	private int endYear;

	public Calendar(int startDay, int startYear, int endYear) {
		this.startYear = startYear;
		this.endYear = endYear;
		for(int year = startYear; year <= endYear; year++) {
			Year thisYear;
			if(year % 4 == 0) {
				months[1] += 1;
				thisYear = new Year(startDay, this.months, this.monthNames, year);
				startDay = (startDay + 366) % 7;
				months[1] -= 1;
			} else {
				thisYear = new Year(startDay, this.months, this.monthNames, year);
				startDay = (startDay + 365) % 7;
			}
			years.add(thisYear);
		}
	}
	
	public Year getYear(int year) {
		if(year < startYear || year > endYear) {
			//throw
			System.out.println("Inappropriate year");
		}
		return years.get(year - startYear);
	}
	
	public int[] getMonths() {
		return this.months;
	}
	
	public String[] getMonthNames() {
		return this.monthNames;
	}
	
	public int getStartYear() {
		return this.startYear;
	}
	
	public int getEndYear() {
		return this.endYear;
	}
	
 }
