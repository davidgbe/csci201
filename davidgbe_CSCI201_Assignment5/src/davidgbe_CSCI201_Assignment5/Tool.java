package davidgbe_CSCI201_Assignment5;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Tool extends Item {
	private int maxQuantity;
	private int currentQuantity;
	private Semaphore toolSemaphore;
	private ReentrantLock lock = new ReentrantLock();
	
	public Tool(String toolName, String imagePath, int max) {
		super(toolName, imagePath, max + "/" + max, 6);
		maxQuantity = max;
		currentQuantity = max;
		toolSemaphore = new Semaphore(max);
	}
	
	public void takeTool() {
		try {
			toolSemaphore.acquire();
			System.out.println("Got past semaphore");
			lock.lock();
			System.out.println("Got past lock");
			currentQuantity--;
			this.updateText(currentQuantity + "/" + maxQuantity);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void returnTool() {
		lock.lock();
		try {
			currentQuantity++;
			this.updateText(currentQuantity + "/" + maxQuantity);
			toolSemaphore.release();
		} finally {
			lock.unlock();
		}
	}
	
	
}
