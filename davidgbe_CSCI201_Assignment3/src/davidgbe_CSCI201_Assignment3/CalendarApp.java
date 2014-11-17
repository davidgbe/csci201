package davidgbe_CSCI201_Assignment3;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.html.HTMLDocument.Iterator;

public class CalendarApp extends JFrame {
	
	private Calendar calendar;
	
	private int currYear;
	private int currMonth;
	private int currDay;
	
	private CardLayout currCardLayout;
	private JPanel currYearPanel;
	private JPanel mainPanel;
	private MonthView currView;
	private JPanel eventView;
	
	private HashMap< Day, ArrayList<CalEvent> > allEvents = new HashMap< Day, ArrayList<CalEvent> >();
	
	private int startYear;
	private int endYear;

	public CalendarApp(){
		super("Calendar App");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.startYear = 2000;
		this.endYear = 2200;
		this.calendar = new Calendar(6, this.startYear, this.endYear);
		
		setSize(1000, 500);
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
		this.mainPanel.add(createHeader());
		
		this.mainPanel.add(createCalendar(2014, 10, 15));
		this.mainPanel.add(createEventView());
		add(this.mainPanel);
		setVisible(true);
	}
	
	private JPanel createHeader() {
		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton eventManagerButton = new JButton("Event Manager");
		eventManagerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				createEventManager();
			}
		});
		JButton exportButton = new JButton("Export");
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				export();
			}
		});
		JButton aboutButton = new JButton("About");
		aboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				new About();
			}
		});
		header.add(eventManagerButton);
		header.add(exportButton);
		header.add(aboutButton);
		header.setBorder(BorderFactory.createBevelBorder(EtchedBorder.RAISED));
		return header;
	}
	
	private void createEventManager() {
		new EventManager(this, null, null, null, "Event Manager", "Create");
	}
	
	private JPanel createCalendar(int year, int month, int day) {
		this.currYear = year;
		this.currMonth = month;
		this.currDay = day;
		this.currCardLayout = new CardLayout();
		this.currYearPanel = new JPanel(this.currCardLayout);
		MonthView thisMonth = new MonthView(this.calendar, month, year, this);
		this.currYearPanel.add(year + "" + month, thisMonth);
		this.currCardLayout.show(this.currYearPanel, this.currYear + "" + this.currMonth);
		this.currView = thisMonth;
		thisMonth.setVisible(false);
		return this.currYearPanel;
	}
	
	private JPanel createEventView() {
		this.eventView = new JPanel();
		eventView.setLayout(new BoxLayout(eventView, BoxLayout.Y_AXIS));
		eventView.add(new JLabel("No events"));
		eventView.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		return eventView;
	}
	
	private void updateEventView() {
		System.out.println("OUT");
		Day currDay = this.calendar.getYear(this.currYear).getMonth(this.currMonth).getDay(this.currDay);
		System.out.println(currDay.toString());
		this.eventView.removeAll();
		if(this.allEvents.containsKey(currDay)) {
			ArrayList<CalEvent> eventsForDay = this.allEvents.get(currDay);
			for(int i = 0; i < eventsForDay.size(); i++) {
				CalEventButton thisEventButton = new CalEventButton(eventsForDay.get(i).asString(), eventsForDay.get(i));
				thisEventButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						editEvent(thisEventButton.getEvent(), thisEventButton);
					}
				});
				this.eventView.add(thisEventButton);
			}
			this.eventView.revalidate();
		} else {
			this.eventView.add(new JLabel("No events"));
			this.eventView.revalidate();

		}
	}
	
	public void nextMonth() {
		if(this.currYear == this.calendar.getEndYear()) {
			return;
		}
		if(this.currMonth != 11) {
			this.currMonth++;
		} else {
			this.currYear++;
			this.currMonth = 0;
		}
		this.currYearPanel.remove(this.currView);
		this.currView = new MonthView(this.calendar, this.currMonth, this.currYear, this);
		this.currYearPanel.add(this.currView, this.currYear + "" + this.currMonth);
		this.currYearPanel.revalidate();
	}
	public void prevMonth() {
		if(this.currYear == this.calendar.getStartYear()) {
			return;
		}
		if(this.currMonth != 0) {
			this.currMonth--;
		} else {
			this.currYear--;
			this.currMonth = 11;
		}
		this.currYearPanel.remove(this.currView);
		this.currView = new MonthView(this.calendar, this.currMonth, this.currYear, this);
		this.currYearPanel.add(this.currView, this.currYear + "" + this.currMonth);
		this.currYearPanel.revalidate();
	}
	
	public int getStartYear() {
		return this.startYear;
	}
	
	public int getEndYear() {
		return this.endYear;
	}
	
	public Calendar getCalendar() {
		return this.calendar;
	}
	
	public void addEvent(Day day, CalEvent event) {
		if(!this.allEvents.containsKey(day)) {
			ArrayList<CalEvent> newEventList = new ArrayList<CalEvent>();
			newEventList.add(event);
			this.allEvents.put(day, newEventList);
		} else {
			this.allEvents.get(day).add(event);
		}
		updateEventView();
	}
	
	public void removeEvent(CalEvent event, CalEventButton eventButton) {
		Collection<Day> allDays = this.allEvents.keySet();
		for(Day day : allDays) {
			ArrayList<CalEvent> eventsForDay = allEvents.get(day);
			if(eventsForDay.contains(event)) {
				eventsForDay.remove(event);
				if(eventsForDay.isEmpty()) {
					allEvents.remove(day);
				}
			}
		}
		if(eventButton != null) {
			this.eventView.remove(eventButton);
			this.eventView.revalidate();
		}
		//if empty, no events
	}
	
	public void setCurrDay(int day) {
		this.currDay = day;
		updateEventView();
	}
	
	public int getCurrDay() {
		return this.currDay;
	}
	
	public void editEvent(CalEvent event, CalEventButton thisButton) {
		new EditOrDelete(event, this, thisButton, this.calendar.getYear(this.currYear).getMonth(this.currMonth).getDay(this.currDay));
	}
	
	public void createEvent(Day day) {
		new EventManager(this, null, null, day, "Create", "Create");
	}
	
	public JPanel getEventView() {
		return this.eventView;
	}
	
	public void export() {
		FileWriter fw = null;
		try {
			fw = new FileWriter("events.csv");
		} catch(Exception e) {
			
		}
		PrintWriter pw = new PrintWriter(fw);
		Collection<Day> allDays = this.allEvents.keySet();
		for(Day day : allDays) {
			ArrayList<CalEvent> eventsForDay = allEvents.get(day);
			for(int i = 0; i < eventsForDay.size(); i++) {
				CalEvent event = eventsForDay.get(i);
				String line = day.getYear() + "," + this.calendar.getMonthNames()[day.getMonth()] + "," + day.getDayOfMonth();
				line += "," + event.getTitle() + "," + event.getLocation() + "," + event.getStartTime() + ',' + event.getEndTime();
				pw.println(line);
			}
		}
		pw.close();

	}
	
	public static void main(String[] args) {
		new CalendarApp();
	}
	
}
