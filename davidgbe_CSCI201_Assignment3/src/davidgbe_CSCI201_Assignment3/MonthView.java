package davidgbe_CSCI201_Assignment3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MonthView extends JPanel {
	
	private CalendarApp thisApp;
	
	private Month thisMonth;
	private int monthNum;
	private int yearNum;
	private String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};
	
	private JButton left;
	private JButton right;
	
	private int numButtonsNotInMonth;
	
	private ArrayList<Day> daysForPage;
	private ArrayList<DayButton> dayButtonsForPage;
	
	public MonthView(Calendar cal, int month, int year, CalendarApp app) {
		
		Year thisYear = cal.getYear(year);
		this.thisApp = app;
		this.thisMonth = thisYear.getMonth(month);
		this.monthNum = month;
		this.yearNum = year;
		
		this.daysForPage = findDaysForPage(cal);
		
		setSize(300, 300);
		setLayout(new BorderLayout());
		add(createHeader(this.thisMonth.getName(), year, app), BorderLayout.NORTH);
		add(createCalendarPage(this.daysForPage), BorderLayout.SOUTH);
		
	}
	
	private JPanel createHeader(String month, int year, CalendarApp app) {
		JPanel header = new JPanel(new BorderLayout());
		
		JPanel banner = new JPanel(new BorderLayout());
		banner.setOpaque(true);
		banner.setBackground(Color.GRAY);
		banner.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		banner.setLayout(new BorderLayout());
		
		JPanel monthAndYear = new JPanel();
		JLabel monthBanner = new JLabel("<html><div style=\"font-size:20px;\">" + month + "</div></html>");
		JLabel yearBanner = new JLabel("<html><div style=\"font-size:11px;\">" + year + "</div></html>");
		monthBanner.setHorizontalAlignment(SwingConstants.CENTER);
		yearBanner.setHorizontalAlignment(SwingConstants.CENTER);
		monthAndYear.add(monthBanner);
		monthAndYear.add(yearBanner);
		monthAndYear.setOpaque(true);
		monthAndYear.setBackground(Color.GRAY);
		banner.add(monthAndYear, BorderLayout.CENTER);
		
		this.right = new JButton(">");
		this.right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				app.nextMonth();
			}
		});
		this.left = new JButton("<");
		this.left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				app.prevMonth();
			}
		});
		banner.add(right, BorderLayout.EAST);
		banner.add(left, BorderLayout.WEST);
		
		JPanel daysOfWeekPanel = new JPanel(new GridLayout());
		for(int i = 0; i < 7; i++) {
			JLabel dayLabel = new JLabel(this.daysOfWeek[i]);
			dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
			daysOfWeekPanel.add(dayLabel);
		}
		
		header.add(banner, BorderLayout.NORTH);
		header.add(daysOfWeekPanel, BorderLayout.SOUTH);
		header.setVisible(true);
		return header;
	}
	
	private JPanel createCalendarPage(ArrayList<Day> days) {
		this.dayButtonsForPage = new ArrayList<DayButton>();
		int rows = (int) Math.ceil(((( days.size() + days.get(0).getDayOfWeek() ) / 7.0)));
		JPanel calendarPagePanel = new JPanel(new GridLayout(rows, 7));
		int index = 0;
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < 7; c++) {
				Day thisDay = days.get(index);
				DayButton newButton = new DayButton(thisDay.getDayOfMonthWithSuffix(), index, this.thisApp, thisDay);
				if(index < this.numButtonsNotInMonth || index >= this.numButtonsNotInMonth + thisMonth.getNumDays()) {
					newButton.setEnabled(false);
				}
				newButton.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						setCurrDay(newButton.getDayNum());
					}
				});
				newButton.setMargin(new Insets(15, 1, 15, 1));
				dayButtonsForPage.add(newButton);
				calendarPagePanel.add(newButton);
				index++;
			}
		}
		for(int i = 0; i < dayButtonsForPage.size(); i++) {
			System.out.println(dayButtonsForPage.get(i));
		}
		return calendarPagePanel;
	}
	
	private void setCurrDay(int day) {
		if(day != thisApp.getCurrDay()) {
			dayButtonsForPage.get(this.thisApp.getCurrDay() + this.numButtonsNotInMonth).unselect();
			this.thisApp.setCurrDay(day - this.numButtonsNotInMonth);
		}
		dayButtonsForPage.get(this.thisApp.getCurrDay() + this.numButtonsNotInMonth).select();
		
	}
	
	private ArrayList<Day> findDaysForPage(Calendar cal) {
		ArrayList<Day> pageDays = thisMonth.getAllDays();
		int firstDayOfMonth = pageDays.get(0).getDayOfWeek();
		this.numButtonsNotInMonth = firstDayOfMonth;
		System.out.println(this.numButtonsNotInMonth);
		if(firstDayOfMonth != 0) {
			Month prevMonth;
			if(this.monthNum == 0) {
				prevMonth = cal.getYear(this.yearNum - 1).getMonth(11);
			} else {
				prevMonth = cal.getYear(this.yearNum).getMonth(this.monthNum - 1);
			}
			for(int i = 0; i < firstDayOfMonth; i++) {
				pageDays.add(0, prevMonth.getDay(prevMonth.getNumDays() - i - 1));
			}
		}
		Month nextMonth;
		if(this.monthNum == 11) {
			nextMonth = cal.getYear(this.yearNum + 1).getMonth(0);
		} else {
			nextMonth = cal.getYear(this.yearNum).getMonth(this.monthNum + 1);
		}
		int i = 0;
		while(pageDays.size() % 7 != 0) {
			pageDays.add(nextMonth.getDay(i));
			i++;
		}
		return pageDays;
	}
	
	public ArrayList<Day> getDaysForPage() {
		return this.daysForPage;
	}

}
