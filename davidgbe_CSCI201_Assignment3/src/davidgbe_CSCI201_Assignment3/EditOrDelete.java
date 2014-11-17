package davidgbe_CSCI201_Assignment3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditOrDelete extends JFrame {
	
	private JButton delete;
	private JButton edit;
	private JButton cancel;
	
	private CalEvent thisEvent;
	private CalendarApp thisApp;
	private CalEventButton thisEventButton;
	private Day thisDay;
	
	public EditOrDelete(CalEvent thisEvent, CalendarApp thisApp, CalEventButton thisButton, Day day) {
		super("Edit or Delete");
		this.setSize(400, 400);
		this.thisEvent = thisEvent;
		this.thisEventButton = thisButton;
		this.thisApp = thisApp;
		this.thisDay = day;
		
		add(new JLabel(new ImageIcon()));
		JPanel rightSide = new JPanel();
		rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
		JLabel prompt = new JLabel("Would you like to edit or delete this event?");
		JPanel suggestions = new JPanel();
		deleteButton();
		editButton();
		cancelButton();
		suggestions.add(delete);
		suggestions.add(edit);
		suggestions.add(cancel);
		rightSide.add(prompt);
		rightSide.add(suggestions);
		add(rightSide);
		setVisible(true);
	}
	
	public void deleteButton() {
		delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				deleteEvent();
				dispose();
			}
		});
	}
	
	public void editButton() {
		edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				editEvent();
				dispose();
			}
		});
	}

	public void cancelButton() {
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
	}
	
	public void cancel() {
		
	}
	
	public void editEvent() {
		new EventManager(this.thisApp, thisEvent, thisEventButton, thisDay, "Edit", "Save");
	}
	
	public void deleteEvent() {
		this.thisApp.removeEvent(thisEvent, thisEventButton);
	}
}
