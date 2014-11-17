import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;


public class polygonJPanel extends JPanel {
	private JButton button;
	
	public polygonJPanel(JButton b) {
		this.button = b;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int multFactor = 4;
		int xOffset = this.button.getBounds().x;
		int yOffset = this.button.getBounds().y;
		int[] xPoints = {10, 20, 25, 25, 20, 10, 5, 5};
		int[] yPoints = {10, 10, 20, 27, 37, 37, 27, 20};
		for(int i = 0; i < xPoints.length; i++) {
			xPoints[i] = 4 * xPoints[i] + xOffset;
			yPoints[i] = 4 * yPoints[i] + yOffset;
		}
		g.setColor(Color.BLUE);
		
		g.fillPolygon(xPoints, yPoints, 8);
	}
	
	public void setButton(JButton b) {
		this.button = b;
	}
}
