package davidgbe_CSCI201_Assignment4;

import java.util.ArrayList;

public class Tile {
	private String type;
	private String degree;
	private String row;
	private int col;
	private boolean right = true;
	private boolean left = true;
	private boolean up = true;
	private boolean down = true;
	
	
	
	public Tile(String t, String d, String row, int col) {
		this.type = t;
		this.degree = d;
		this.row = row;
		this.col = col;
		if(type.equals("i")) {
			if(degree.equals("0") || degree.equals("180")) {
				left = false;
				right = false;
			}
			else {
				up = false;
				down = false;
			}
		} else if(type.equals("l")) {
			if(degree.equals("0")) {
				left = false;
				down = false;
			} else if(degree.equals("90")) {
				right = false;
				down = false;
			} else if(degree.equals("180")) {
				up = false;
				right = false;
			} else {
				up = false;
				left = false;
			}			
		} else if(type.equals("t")) {
			if(degree.equals("0")) {
				up = false;
			} else if(degree.equals("90")) {
				left = false;
			} else if(degree.equals("180")) {
				down = false;
			} else {
				right = false;
			}		
		}
	}
	
	public boolean canGoRight() {
		return this.right;
	}
	
	public boolean canGoLeft() {
		return this.left;
	}
	
	public boolean canGoUp() {
		return this.up;
	}
	
	public boolean canGoDown() {
		return this.down;
	}
	
	public String getRow() {
		return this.row;
	}
	
	public int getRowAsInt() {
		return ((int)this.row.charAt(0)) - 65;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public String getType() {
		return type;
	}
	
	public String getDegree() {
		return degree;
	}
	
	public void print() {
		System.out.println(this.type + " " + this.degree);
	}
}
