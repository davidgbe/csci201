package davidgbe_CSCI201_Assignment5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JLabel;

public class WorkArea extends Item implements Runnable {
	private ReentrantLock lock = new ReentrantLock();

	public WorkArea(String imagePath, String text) {
		super("Open", imagePath, "", 16);
		this.mainPanel.add(new JLabel(text));
		toGreen();
	}
	
	public void startCountDown(int seconds) {
		lock.lock();
		try {
			countDown(seconds);
		} finally {
			lock.unlock(); 
		}
	}
	
	private void countDown(int seconds) {
		lock.lock();
		System.out.println("Called2");
		try {
			toRed();
			while(seconds != 0) {
				this.updateTitle(seconds + "s");
				try {
					Thread.sleep(1000);
					seconds--;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			this.updateTitle("Open");
			toGreen();
			System.out.println("Done!");
			lock.unlock();
		}
	}

	@Override
	public void run() {
//		while(true) {
//			if(this.seconds != 0) {
//				System.out.println("SECONDS: " + this.seconds);
//			}
//			if(alreadyStarted) {
//				System.out.println("BOOM");
//			}
//			if(this.seconds != 0 && !alreadyStarted) {
//				System.out.println("In");
//				alreadyStarted = true;
//				this.countDown();
//			}
//		}
	}
	
}
