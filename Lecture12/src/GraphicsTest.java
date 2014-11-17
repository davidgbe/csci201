import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GraphicsTest extends JFrame {
	public GraphicsTest() {
		super("Graphics Test");
		setSize(400, 300);
		setLocation(500, 500);
		add(new GraphicsPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public  static void main(String[]  args) {
		new GraphicsTest();
	}
	
	class GraphicsPanel extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("CSCI 201", 50, 100);
		}
	}
}
