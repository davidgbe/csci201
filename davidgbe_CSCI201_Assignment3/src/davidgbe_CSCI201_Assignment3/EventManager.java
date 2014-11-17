package davidgbe_CSCI201_Assignment3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class EventManager extends JFrame {
	
	private String[] months = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
	
	private CalendarApp thisApp;
	
	private JTextField titleField;
	private JTextField locationField;
	
	private JComboBox yearBox;
	private JComboBox monthBox;
	private JComboBox dayBox;
	
	private JComboBox startHour;
	private JComboBox startMinute;
	private JComboBox startAmPm;
	
	private JComboBox endHour;
	private JComboBox endMinute;
	private JComboBox endAmPm;
	
	private boolean editOnly;
	private CalEvent event;
	private CalEventButton eventButton;
	private Day thisDay;
	
	public EventManager(CalendarApp thisApp, CalEvent event, CalEventButton eventButton, Day day, String title, String confirm) {	
		super(title);
		
		if(day != null) {
			editOnly = true;
		}
		if(event != null) {
			this.event = event;
			this.eventButton = eventButton;
		}
		this.thisDay = day;
		this.thisApp = thisApp;
		setSize(500, 500);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		titlePanel.add(new JLabel("Title: "));
		this.titleField = new JTextField("", 20);
		titlePanel.add(this.titleField);
		
		JPanel locationPanel = new JPanel();
		locationPanel.add(new JLabel("Location: "));
		this.locationField = new JTextField("", 20);
		locationPanel.add(this.locationField);
		
		JPanel timePanel = new JPanel();
		
		if(!editOnly) {
			JPanel datePanel = new JPanel();
			datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
			String[] years = new String[thisApp.getEndYear() + 1 - thisApp.getStartYear() - 1];
			for(int y = thisApp.getStartYear(); y <= thisApp.getEndYear() - 2; y++) {
				years[y - thisApp.getStartYear()] = Integer.toString(y);
			}
			JPanel yearPanel = new JPanel();
			yearPanel.add(new JLabel("Year: "));
			yearBox = new JComboBox(years);
			yearPanel.add(yearBox);
			JPanel	monthPanel = new JPanel();
			monthPanel.add(new JLabel("Month: "));
			monthBox = new JComboBox(months);
			monthPanel.add(monthBox);
			String[] days = new String[31];
			for(int d = 0; d < 31; d++) {
				days[d] = Integer.toString(d+1);
			}
			JPanel dayPanel = new JPanel();
			dayPanel.add(new JLabel("Day: "));
			dayBox = new JComboBox(days);
			dayPanel.add(dayBox);
			datePanel.add(yearPanel);
			datePanel.add(monthPanel);
			datePanel.add(dayPanel);
			
			timePanel.add(datePanel);
		}
		
		JPanel hourPanel = new JPanel();
		hourPanel.setLayout(new BoxLayout(hourPanel, BoxLayout.Y_AXIS));
		
		JPanel startPanel = new JPanel();
		startPanel.add(new JLabel("Start: "));
		String[] startHours = new String[12];
		for(int sh = 0; sh < 12; sh++) {
			startHours[sh] = addZeros(Integer.toString(sh+1));
		}
		startHour = new JComboBox(startHours);
		startPanel.add(startHour);
		String[] startMinutes = new String[60];
		for(int sm = 0; sm < 60; sm++) {
			startMinutes[sm] = addZeros(Integer.toString(sm));
		}
		startMinute = new JComboBox(startMinutes);
		startPanel.add(startMinute);
		String[] startAmPms = {"AM", "PM"};
		startAmPm = new JComboBox(startAmPms);
		startPanel.add(startAmPm);
		hourPanel.add(startPanel);
		
		JPanel	endPanel = new JPanel();
		endPanel.add(new JLabel("End: "));
		endHour = new JComboBox(startHours);
		endPanel.add(endHour);
		endMinute = new JComboBox(startMinutes);
		endPanel.add(endMinute);
		endAmPm = new JComboBox(startAmPms);
		endPanel.add(endAmPm);
		hourPanel.add(endPanel);
		
		timePanel.add(hourPanel);

		
		
		JPanel buttonsPanel = new JPanel();
		JButton create = new JButton(confirm);
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				createEvent();
				dispose();
			}
		});
		buttonsPanel.add(create);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		buttonsPanel.add(cancel);
		
		
		mainPanel.add(titlePanel);
		mainPanel.add(locationPanel);
		mainPanel.add(timePanel);
		mainPanel.add(buttonsPanel);
		
		add(mainPanel);
		
		setVisible(true);
	}
	
	private int getMonthNum(String month) {
		for(int i = 0; i < 12; i++) {
			if(month.equals(this.months[i])) {
				return i;
			}
		}
		return -1;
	}
	
	private String addZeros(String s) {
		if(s.length() == 1) {
			return "0" + s;
		}
		return s;
	}
	
	public void createEvent() {
		int year;
		int month;
		int day;
		if(!editOnly) {
			year = Integer.parseInt((String) yearBox.getSelectedItem());
			month = getMonthNum((String) monthBox.getSelectedItem());
			day = Integer.parseInt((String) dayBox.getSelectedItem());
		} else {
			year = this.thisDay.getYear();
			month = this.thisDay.getMonth();
			day = this.thisDay.getDayOfMonth();
			if(event != null) {
				this.thisApp.removeEvent(event, eventButton);
			}
		}

		Day dayForEvent = thisApp.getCalendar().getYear(year).getMonth(month).getDay(day-1);
		System.out.println(dayForEvent);
		
		String start = (String)startHour.getSelectedItem() + ":" +  (String)startMinute.getSelectedItem() + " " + startAmPm.getSelectedItem();
		String end = (String)endHour.getSelectedItem() + ":" + (String)endMinute.getSelectedItem() + " " + endAmPm.getSelectedItem();

		CalEvent newEvent = new CalEvent(start, end, (String)locationField.getText(), (String)titleField.getText());
		System.out.println(newEvent);
		thisApp.addEvent(dayForEvent, newEvent);
	}
}
