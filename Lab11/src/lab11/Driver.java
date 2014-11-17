package lab11;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Driver {
	
	public Driver() {
		
	}

	public static void main(String[] args) {
		new Driver();
//		ArrayList<AddHorseCommand> allThreads = new ArrayList<AddHorseCommand>();
		ReentrantLock lock = new ReentrantLock();
//		for(int i = 0; i < 8; i++) {
//			allThreads.add(new AddHorseCommand(lock));
//		}
//		for(int p = 0; p < allThreads.size(); p++) {
//			new Thread(allThreads.get(p)).start();
//		}
		RaceCommand newRace = new RaceCommand(lock);
		new Thread(newRace).start();
		for(int i = 0; i < 8; i++) {
			MostTimesPlacedCommand mtpc = new MostTimesPlacedCommand(lock, i+1);
			new Thread(mtpc).start();
		}
	}

}
