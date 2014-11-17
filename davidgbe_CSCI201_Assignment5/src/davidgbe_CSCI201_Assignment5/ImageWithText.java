package davidgbe_CSCI201_Assignment5;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageWithText extends JLabel {
	private String text;
	private int offset;
	
	public ImageWithText(String imagePath, String text, int offset) {
		super(new ImageIcon(imagePath));
		this.text = text;
		this.offset = offset;
		this.repaint();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString(this.text, this.offset, 30);
	}
	
	public void updateText(String newText) {
		this.text = newText;
		this.repaint();
	}
}
