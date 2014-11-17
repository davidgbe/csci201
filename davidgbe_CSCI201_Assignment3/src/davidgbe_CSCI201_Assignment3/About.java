package davidgbe_CSCI201_Assignment3;

import java.awt.Color;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class About extends JFrame {
	public About() {
		super("About: David Bell");
		setSize(300, 300);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(new JLabel(new ImageIcon("david.jpg")));
		mainPanel.add(new JLabel("This is David Bell"));
		mainPanel.add(new JLabel("He just went to CalHacks"));
		mainPanel.add(new JLabel("He is exhausted and sick of looking at code."));
		add(mainPanel);
		mainPanel.setBackground(Color.YELLOW);
		setVisible(true);
	}
}
