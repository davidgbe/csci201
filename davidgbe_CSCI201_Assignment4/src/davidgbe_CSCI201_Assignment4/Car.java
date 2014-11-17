package davidgbe_CSCI201_Assignment4;

import java.util.HashMap;

public abstract class Car extends Thread {
	private String color;
	private String ai;
	private double speed;
	
	protected String row;
	protected int col;
	
	private boolean hidden = false;
	
	protected Tile currTile;
	
	protected MainWindow mw;
	
	protected String[] directions = {"right", "down", "left", "up" };
	
	protected int rowInc;
	
	public Car(String c, String ai, String s, String r, int col, Tile t, MainWindow mw, int rowInc) {
		this.color = c;
		this.ai = ai;
		this.speed = Double.parseDouble(s);
		this.row = r;
		this.col = col;
		this.currTile = t;
		this.mw = mw;
		this.rowInc = rowInc;
	}
	
	public void setRowInc(int inc) {
		this.rowInc = inc;
	}
	
	public int getRowInc() {
		return this.rowInc;
	}
	
	public void setHidden(boolean b) {
		this.hidden = b;
	}
	
	public boolean isHidden() {
		return this.hidden;
	}
	
	public void setCurrentTile(Tile t) {
		this.currTile = t;
	}
	
	public Tile getCurrentTile() {
		return this.currTile;
	}
	
	public void print() {
		System.out.println(this.color + " " + this.ai + " " + this.speed + " " + this.row + " " + this.col);
	}
	
	public String getColor() {
		return this.color;
	}
	
	public String getAi() {
		return this.ai;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public String getRow() {
		return this.row;
	}
	
	public void setRow(String r) {
		this.row = r;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void setCol(int c) {
		this.col = c;
	}
	
	public abstract String nextLocation();
	
	public Tile rightTile() {
		return ((Tile[])(mw.getAllTiles().get(currTile.getRow())))[currTile.getCol()+1];
	}
	
	public Tile leftTile() {
		return ((Tile[])(mw.getAllTiles().get(currTile.getRow())))[currTile.getCol()-1];
	}
	
	public Tile upTile() {
		String newRow = Character.toString(((char)(currTile.getRowAsInt() - 1 + 65)));
		return ((Tile[])(mw.getAllTiles().get(newRow)))[currTile.getCol()];
	}
	
	public Tile downTile() {
		String newRow = Character.toString(((char)(currTile.getRowAsInt() + 1 + 65)));
		return ((Tile[])(mw.getAllTiles().get(newRow)))[currTile.getCol()];
	}
	
	public void run() {
		int counter = 0;
		while(true) {
			if(counter == 3) {
				this.mw.moveCarInstance(this);
				counter = 0;
			}
			try {
				Thread.sleep((long)(1000/(this.speed * 3)));
				this.setHidden(true);
				Thread.sleep((long)100);
				this.setHidden(false);
			} catch (InterruptedException e) {}
			counter++;
		}
	}
}
