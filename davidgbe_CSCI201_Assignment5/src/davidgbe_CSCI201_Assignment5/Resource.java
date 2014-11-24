package davidgbe_CSCI201_Assignment5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Resource extends Item {
	private int currentQuantity;
	private ReentrantLock lock = new ReentrantLock();
	
	public Resource(String title, String imagePath, int quantity) {
		super(title, imagePath, quantity + "", 16);
		this.currentQuantity = quantity;
	}
	
	public boolean takeResource(int quantity) {
		lock.lock();
		try {
			if(currentQuantity - quantity >= 0) {
				currentQuantity -= quantity;
				this.updateText(currentQuantity + "");
				lock.unlock();
				return true;
			} else {
				lock.unlock();
				return false;
			}
		} catch(Exception e) {
			lock.unlock();
			e.printStackTrace();
			return false;
		}
	}

}
