import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;


public class dontClickButton extends JButton {
	
	public dontClickButton() {
		super("Don't click me");
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle bounds= super.getBounds();
		System.out.println(bounds.x + " " + bounds.y + " " + bounds.width + " " + bounds.height);
		g.setColor(Color.red);
		g.drawLine(0, 0, bounds.width, bounds.height);
		g.drawLine(0, bounds.height,bounds.width, bounds.y);
	}
}
