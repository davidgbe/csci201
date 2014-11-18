package davidgbe_CSCI201_Assignment5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Item extends JPanel {
	private JLabel title;
	private ImageWithText icon;
	protected JPanel mainPanel;
	
	public Item(String title, String imagePath, String text, int offset) {
		super();
		this.setSize(new Dimension(100,100));
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
		this.icon = new ImageWithText(imagePath, text, offset);
		this.title = new JLabel(title);
		mainPanel.add(this.title);
		mainPanel.add(this.icon);
		this.add(mainPanel);
		this.setOpaque(false);
		this.setVisible(true);
	}
	
	public boolean isFinished() {
		if(title.getText() != "Open") {
			return false;
		} else { return false; }
	}
	
	public void updateText(String newText) {
		this.icon.updateText(newText);
	}
	
	public void updateTitle(String newTitle) {
		this.title.setText(newTitle);
		this.repaint();
	}
	
	public void toGreen() {
		this.title.setForeground(Color.GREEN);
	}
	
	public void toRed() {
		this.title.setForeground(Color.RED);
	}
	
}
