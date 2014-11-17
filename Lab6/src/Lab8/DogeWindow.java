package Lab8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class DogeWindow extends JFrame {

	public DogeWindow() {
		super("Doge Window");
		setSize(500, 500);
		setLocation(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel borderLayoutPanel = new JPanel();
		borderLayoutPanel.setLayout(new BorderLayout());
		JPanel northPanel = new JPanel();
		northPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JButton leftButton = new JButton("Button 1");
		JButton rightButton = new JButton("Button 2");
		rightButton.setOpaque(true);
		rightButton.setBackground(Color.RED);
		rightButton.setBorderPainted(false);
		northPanel.add(leftButton);
		northPanel.add(rightButton);
		borderLayoutPanel.add(northPanel, BorderLayout.NORTH);
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		JLabel eastLabel = new JLabel("East");
		JLabel westLabel = new JLabel("West");
		JLabel centerLabel = new JLabel("This is the south quadrant");
		centerLabel.setBorder(BorderFactory.createRaisedBevelBorder());
		southPanel.add(westLabel, BorderLayout.WEST);
		southPanel.add(centerLabel, BorderLayout.CENTER);
		southPanel.add(eastLabel, BorderLayout.EAST);
		borderLayoutPanel.add(southPanel, BorderLayout.SOUTH);
		
		JPanel dogePanel = new JPanel();
		ImageIcon doge = new ImageIcon("doge.jpg");
		JLabel dogeLabel = new JLabel(doge);
		dogePanel.add(dogeLabel);		
		
		tabbedPane.add(borderLayoutPanel, "First");
		tabbedPane.add(dogePanel, "Doge");
		
		add(tabbedPane);
		
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new DogeWindow();
	}

}
