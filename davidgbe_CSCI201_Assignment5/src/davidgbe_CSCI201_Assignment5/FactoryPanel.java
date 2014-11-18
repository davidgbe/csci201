package davidgbe_CSCI201_Assignment5;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class FactoryPanel extends JPanel {
	private ArrayList<Worker> allWorks = new ArrayList<Worker>();
	
	public FactoryPanel() {
		super();
	}
	
	public void setWorkers(ArrayList<Worker> all) {
		this.allWorks = all;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(this.allWorks == null) {
			return;
		}
		for(Worker worker : this.allWorks) {
			g.setColor(Color.black);
			g.fillOval(worker.getX(), worker.getY(), 20, 20);
		}
	}
}
