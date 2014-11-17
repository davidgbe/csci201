package davidgbe_CSCI201_Assignment4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CarType2 extends Car{
	ArrayList<Tile> visitedTiles = new ArrayList<Tile>();
	public CarType2(String c, String ai, String s, String r, int col, Tile t,
			MainWindow mw, int rowInc) {
		super(c, ai, s, r, col, t, mw, rowInc);
		visitedTiles.add(this.getCurrentTile());
	}

	@Override
	public String nextLocation() {
		int decision = 0;
		boolean[] tries = new boolean[4];
		for(int j = 0; j < 4; j++) {
			tries[j] = false;
		}
		while(true) {
			boolean allTried = true;
			for(int i = 0; i < 4; i++) {
				if(!tries[i]) {
					allTried = false;
				} 
			}
			if(allTried) {
				for(int p = 0; p < 4; p++) {
					tries[p] = false;
				}
				visitedTiles.clear();
			}
			Random rn = new Random();
			decision = Math.abs(rn.nextInt() % 4);
			tries[decision] = true;
			if(decision == 0) {
				if(this.getCurrentTile().canGoRight() && !visitedTiles.contains(this.rightTile())) {
					visitedTiles.add(this.rightTile());
					break;
				}
			} else if(decision == 1) {
				if(this.getCurrentTile().canGoDown() && !visitedTiles.contains(this.downTile())) {
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
		return this.directions[decision];
	}
}
