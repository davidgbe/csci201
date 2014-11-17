package csci201.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CSCI201Window extends JFrame{
	
	public CSCI201Window() {
		super("My First GUI");
		setSize(500, 300);
		setLocation(100, 100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//default layout manager is border layout --> only 5 elements --> north, east, south, west, center
		JButton verifyButton = new JButton("Verify");
		JButton submitButton = new JButton("Submit");
		
		JTextField nameText = new JTextField(25);
		JTextField submitText = new JTextField(25);
		
		JLabel nameLabel = new JLabel("Name");
		JLabel emailLabel = new JLabel("Email");

		add(nameLabel);
		add(nameText);
		add(verifyButton);
		add(submitText);
		add(emailLabel);
		add(submitButton);
		
		setVisible(true);
		
	}

	public static void main(String[] args) {
		new CSCI201Window();

	}

}
