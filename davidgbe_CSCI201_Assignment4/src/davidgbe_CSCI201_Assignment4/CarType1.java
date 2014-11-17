package davidgbe_CSCI201_Assignment4;

import java.util.ArrayList;
import java.util.HashMap;

public class CarType1 extends Car {
	private int lastDirection = 1;
	ArrayList<Tile> visitedTiles = new ArrayList<Tile>();
	
	public CarType1(String c, String ai, String s, String r, int col, Tile t,
			MainWindow mw, int rowInc) {
		super(c, ai, s, r, col, t, mw, rowInc);
		visitedTiles.add(this.getCurrentTile());
	}

	@Override
	public String nextLocation() {
		int decision = lastDirection;
		while(true) {
			decision = (decision + 1) % 4;
			if(decision == 0) {
				if(this.getCurrentTile().canGoRight() && !visitedTiles.contains(this.rightTile())) {
					visitedTiles.add(this.rightTile());
					break;
				}
			} else if(decision == 1) {
				if(this.getCurrentTile().canGoDown() &&!visitedTiles.contains(this.downTile())) {
					visitedTiles.add(this.downTile());
					break;
				}
			} else if(decision == 2) {
				if(this.getCurrentTile().canGoLeft() && !visitedTiles.contains(this.leftTile())) {
					visitedTiles.add(this.leftTile());
					break;
				}
			} else if(decision == 3) {
				if(this.getCurrentTile().canGoUp() && !visitedTiles.contains(this.upTile())) {
					visitedTiles.add(this.upTile());
					break;
				}
			}
		}
		lastDirection = decision;
		return this.directions[decision];
	}

}
