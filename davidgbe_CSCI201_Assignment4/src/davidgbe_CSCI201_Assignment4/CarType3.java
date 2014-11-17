package davidgbe_CSCI201_Assignment4;

import java.util.HashMap;
import java.util.Random;

public class CarType3 extends Car{
	private boolean lookingWest = true;
	public CarType3(String c, String ai, String s, String r, int col, Tile t,
			MainWindow mw, int rowInc) {
		super(c, ai, s, r, col, t, mw, rowInc);
	}

	@Override
	public String nextLocation() {
		if(lookingWest) {
			if(this.getCurrentTile().canGoLeft()) {
				if(this.leftTile().getRow().equals(this.mw.getFarthestWest())) {
					lookingWest = false;
				}
				return "left";
			} else {
				return randomDirection();
			}
		} else {
			if(this.getCurrentTile().canGoRight()) {
				return "right";
			} else {
				return randomDirection();
			}		
		}
	}
	
	private String randomDirection() {
		int decision = 0;
		while(true) {
			Random rn = new Random();
			decision = Math.abs(rn.nextInt() % 4);
			if(decision == 0) {
				if(this.getCurrentTile().canGoRight()) {
					break;
				}
			} else if(decision == 1) {
				if(this.getCurrentTile().canGoDown()) {
					break;
				}
			} else if(decision == 2) {
				if(this.getCurrentTile().canGoLeft()) {
					break;
				}
			} else if(decision == 3) {
				if(this.getCurrentTile().canGoUp()) {
					break;
				}
			}
		}
		return this.directions[decision];
	}
}
