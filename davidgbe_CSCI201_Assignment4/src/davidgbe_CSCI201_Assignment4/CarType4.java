package davidgbe_CSCI201_Assignment4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CarType4 extends Car{
	private HashMap record = new HashMap(); 
	
	public CarType4(String c, String ai, String s, String r, int col, Tile t,
			MainWindow mw, int rowInc) {
		super(c, ai, s, r, col, t, mw, rowInc);
		this.record.put("left", new Integer(0));
		this.record.put("right", new Integer(0));
		this.record.put("up", new Integer(0));
		this.record.put("down", new Integer(0));
	}

	@Override
	public String nextLocation() {
		ArrayList<String> options = new ArrayList<String>();
		int notStarted = 0;
		Set<String> keys = record.keySet();
		Object[] dirs = keys.toArray();
		Integer zero = new Integer(0);
		for(int i = 0; i < dirs.length; i++) {
			if(record.get(dirs[i]).equals(zero)) {
				notStarted++;
				options.add((String)dirs[i]);
			}
		}
		if(notStarted > 1) {
			String decisionName = randomFromOptions(options);
			for(int i = 0; i < dirs.length; i++) {
				if(!record.get(dirs[i]).equals(decisionName)) {
					record.put(dirs[i], new Integer(((Integer) record.get(dirs[i])).intValue() + 1) );
				}
			}
			return decisionName;
		} else {
			ArrayList<String> otherOptions = new ArrayList<String>();
			otherOptions.add("down");
			otherOptions.add("left");
			otherOptions.add("right");
			otherOptions.add("up");
			String max = null;
			while(true) {
				int maxInt = 0;
				Integer maxNum = null;
				for(int p = 0; p < otherOptions.size(); p++) {
					Integer num = (Integer)record.get(otherOptions.get(p));
					if(maxNum == null) {
						maxNum = num;
						max = otherOptions.get(p);
					} else if(num > maxNum) {
						max = otherOptions.get(p);
						maxNum = num;
						maxInt = p;
					}
				}
				if(max.equals("right")) {
					if(this.getCurrentTile().canGoRight()) {
						break;
					}
				} else if(max.equals("down")) {
					if(this.getCurrentTile().canGoDown()) {
						break;
					}
				} else if(max.equals("left")) {
					if(this.getCurrentTile().canGoLeft()) {
						break;
					}
				} else if(max.equals("up")) {
					if(this.getCurrentTile().canGoUp()) {
						break;
					}
				}
				otherOptions.remove(maxInt);
			}
			System.out.println(max);
			for(int i = 0; i < dirs.length; i++) {
				if(!dirs[i].equals(max)) {
					System.out.println(dirs[i]);
					record.put(dirs[i], new Integer(((Integer) record.get(dirs[i])).intValue() + 1) );
				} else {
					record.put(max, new Integer(0) );
				}
			}
			return max;
		}
		
	}
	
	private String randomFromOptions(ArrayList<String> options) {
		int decision = 0;
		String decisionName = null;
		while(true) {
			Random rn = new Random();
			decision = Math.abs(rn.nextInt() % options.size());
			decisionName = options.get(decision);
			if(decisionName.equals("right")) {
				if(this.getCurrentTile().canGoRight()) {
					break;
				}
			} else if(decisionName.equals("down")) {
				if(this.getCurrentTile().canGoDown()) {
					break;
				}
			} else if(decisionName.equals("left")) {
				if(this.getCurrentTile().canGoLeft()) {
					break;
				}
			} else if(decisionName.equals("up")) {
				if(this.getCurrentTile().canGoUp()) {
					break;
				}
			}
		}
		return decisionName;
	}
}
