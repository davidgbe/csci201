package davidgbe_CSCI201_Assignment3;

import javax.swing.JButton;

public class CalEventButton extends JButton {
	
	private CalEvent event;
	
	public CalEventButton(String text, CalEvent event) {
		super(text);
		this.event = event;
	}
	
	public CalEvent getEvent() {
		return this.event;
	}
}
