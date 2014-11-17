package davidgbe_CSCI201_Assignment3;

import java.awt.Color;

import javax.swing.JButton;

public class DayButton extends JButton {
	
	private int dayNum;
	private boolean alreadyClicked;
	private CalendarApp thisApp;
	private Day day;
	
	public DayButton(String label, int dayNum, CalendarApp thisApp, Day day) {
		super(label);
		this.dayNum = dayNum;
		this.thisApp = thisApp;
		this.day = day;
	}
	
	public int getDayNum() {
		return this.dayNum;
	}
	
	public void select() {
		this.setOpaque(true);
		this.setBackground(Color.BLUE);
		if(alreadyClicked) {
			thisApp.createEvent(this.day);
		}
		alreadyClicked=true;
	}
	
	public void unselect() {
		this.setOpaque(false);
		this.setBackground(null);
		alreadyClicked=false;
	}

}
