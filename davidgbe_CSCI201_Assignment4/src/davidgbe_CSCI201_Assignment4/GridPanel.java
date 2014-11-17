package davidgbe_CSCI201_Assignment4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

public class GridPanel extends JPanel {
	
	public final int SPACING = 50;
	public final int ROWS = 9;
	public final int COLS = 9;
	public final int CIRCLE_DIAMETER = 30;
	
	private String[] rowNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
	
	private HashMap allTiles;
	private ArrayList<Car> allCars;


	public GridPanel(HashMap at, ArrayList<Car> ac) {
		super();
		this.setMinimumSize(new Dimension(450, 450));
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(600,600));
		this.allTiles = at;
		this.allCars = ac;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension s = this.getSize();
		double x = s.getWidth();
		double y = s.getHeight();
		int initialX = (int)((x - 450.0) / 2);
		int initialY = (int)((y - 450.0) / 2);
		
		drawBoard(g, initialX, initialY);
		drawTiles(g, initialX, initialY);
		drawCars(g, initialX, initialY);
	}
	
	private void drawCars(Graphics g, int initialX, int initialY) {
		for(Car c : this.allCars) {
			drawCarInstance(g, initialX, initialY, c);
		}
	}
	
	private void drawCarInstance(Graphics g, int initialX, int initialY, Car c) {
		if(c.isHidden()) {
			return;
		}
		Color color;
		try {
			java.lang.reflect.Field field = Class.forName("java.awt.Color").getField(c.getColor());
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null; // Not defined
		}
		g.setColor(color);
		Tile currTile = c.getCurrentTile();
		g.fillOval(initialX + (int)(SPACING * (currTile.getCol() + .5)) - CIRCLE_DIAMETER/2, initialY + (int)(SPACING * (currTile.getRowAsInt() + .5)) - CIRCLE_DIAMETER/2, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
	}
	
	private void drawBoard(Graphics g, int initialX, int initialY) {
		for(int row = initialY; row <= initialY + ROWS*SPACING; row += SPACING) {
			g.drawLine(initialX, row, initialX + ROWS*SPACING, row);
		}
		for(int col = initialX; col <= initialX + COLS*SPACING; col += SPACING) {
			g.drawLine(col, initialY, col, initialY + COLS*SPACING);
		}	
	}
	
	private void drawTiles(Graphics g, int initialX, int initialY) {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				drawTileBlank(g, i, j, initialX, initialY);
			}
		}
		if(this.allTiles.isEmpty()) {			
			return;
		}
		for(String rowName : this.rowNames) {
			Tile[] rowList = (Tile[]) this.allTiles.get(rowName);
			for(int i = 0; i < 9; i++) {
				if(rowList[i] == null) {
					continue;
				}
				matchToDrawingMethod(g, initialX, initialY, i, ((int)(rowName.charAt(0)) - 65), rowList[i]);
			}
		}
	}
	
	private void matchToDrawingMethod(Graphics g, int initialX, int initialY, int x, int y, Tile t) {
		String type = t.getType();
		String degree = t.getDegree();
		if(type.equals("i")) {
			if(degree.equals("0") || degree.equals("180")) {
				drawTileI0(g, x, y, initialX, initialY);
			}
			else {
				drawTileI90(g, x, y, initialX, initialY);
			}
		} else if(type.equals("l")) {
			if(degree.equals("0")) {
				drawTileL0(g, x, y, initialX, initialY);
			} else if(degree.equals("90")) {
				drawTileL90(g, x, y, initialX, initialY);
			} else if(degree.equals("180")) {
				drawTileL180(g, x, y, initialX, initialY);
			} else {
				drawTileL270(g, x, y, initialX, initialY);
			}			
		} else if(type.equals("t")) {
			if(degree.equals("0")) {
				drawTileT0(g, x, y, initialX, initialY);
			} else if(degree.equals("90")) {
				drawTileT90(g, x, y, initialX, initialY);
			} else if(degree.equals("180")) {
				drawTileT180(g, x, y, initialX, initialY);
			} else {
				drawTileT270(g, x, y, initialX, initialY);
			}		
		} else if(type.equals("+")) {
			drawTileCross(g, x, y, initialX, initialY);
		}
	}
	
	private void drawTileI0(Graphics g, int x, int y, int initialX, int initialY) {
		drawUpperSegment(g, x, y, initialX, initialY);
		drawLowerSegment(g, x, y, initialX, initialY);
	}
	
	private void drawTileI90(Graphics g, int x, int y, int initialX, int initialY) {
		drawRightSegment(g, x, y, initialX, initialY);
		drawLeftSegment(g, x, y, initialX, initialY);
	}
	
	private void drawTileCross(Graphics g, int x, int y, int initialX, int initialY) {
		drawTileI0(g, x, y, initialX, initialY);
		drawTileI90(g, x, y, initialX, initialY);
	}
	
	private void drawTileL0(Graphics g, int x, int y, int initialX, int initialY) {
		drawUpperSegment(g, x, y, initialX, initialY);
		drawRightSegment(g, x, y, initialX, initialY);
	}
	
	private void drawTileL90(Graphics g, int x, int y, int initialX, int initialY) {
		drawUpperSegment(g, x, y, initialX, initialY);
		drawLeftSegment(g, x, y, initialX, initialY);
	}
	
	private void drawTileL180(Graphics g, int x, int y, int initialX, int initialY) {
		drawLowerSegment(g, x, y, initialX, initialY);
		drawLeftSegment(g, x, y, initialX, initialY);
	}
	
	private void drawTileL270(Graphics g, int x, int y, int initialX, int initialY) {
		drawLowerSegment(g, x, y, initialX, initialY);
		drawRightSegment(g, x, y, initialX, initialY);
	}
	
	private void drawTileT0(Graphics g, int x, int y, int initialX, int initialY) {
		drawTileI90(g, x, y, initialX, initialY);
		drawLowerSegment(g, x, y, initialX, initialY);
	}
	
	private void drawTileT90(Graphics g, int x, int y, int initialX, int initialY) {
		drawTileI0(g, x, y, initialX, initialY);
		drawRightSegment(g, x, y, initialX, initialY);
	}
	
	private void drawTileT180(Graphics g, int x, int y, int initialX, int initialY) {
		drawTileI90(g, x, y, initialX, initialY);
		drawUpperSegment(g, x, y, initialX, initialY);
	}
	
	private void drawTileT270(Graphics g, int x, int y, int initialX, int initialY) {
		drawTileI0(g, x, y, initialX, initialY);
		drawLeftSegment(g, x, y, initialX, initialY);
	}
	
	private void drawTileBlank(Graphics g, int x, int y, int initialX, int initialY) {
		g.setColor(Color.GREEN);
		int startX = initialX + x * SPACING + 1;
		int startY = initialY + y * SPACING + 1;
		g.fillRect(startX, startY, SPACING - 1, SPACING - 1);
	}
	
	private void drawUpperSegment(Graphics g, int x, int y, int initialX, int initialY) {
		g.setColor(Color.BLACK);
		int startX = initialX + x * SPACING + 1 + 15;
		int startY = initialY + y * SPACING + 1;
		g.fillRect(startX, startY, SPACING - 1 - 30, SPACING - 1 - 15);
	}
	
	private void drawLowerSegment(Graphics g, int x, int y, int initialX, int initialY) {
		g.setColor(Color.BLACK);
		int startX = initialX + x * SPACING + 1 + 15;
		int startY = initialY + y * SPACING + 1 + 15;
		g.fillRect(startX, startY, SPACING - 1 - 30, SPACING - 1 - 15);
	}
	
	private void drawRightSegment(Graphics g, int x, int y, int initialX, int initialY) {
		g.setColor(Color.BLACK);
		int startX = initialX + x * SPACING + 1 + 15;
		int startY = initialY + y * SPACING + 1 + 15;
		g.fillRect(startX, startY, SPACING - 1 - 15, SPACING - 1 - 30);
	}
	
	private void drawLeftSegment(Graphics g, int x, int y, int initialX, int initialY) {
		g.setColor(Color.BLACK);
		int startX = initialX + x * SPACING + 1;
		int startY = initialY + y * SPACING + 1 + 15;
		g.fillRect(startX, startY, SPACING - 1 - 15, SPACING - 1 - 30);
	}
	
	
}
