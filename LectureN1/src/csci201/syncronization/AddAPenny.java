package csci201.syncronization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddAPenny implements Runnable{
	private static PiggyBank piggy = new PiggyBank();
	
	public void run() {
		piggy.deposit(1);
	}
	
	public static void main(String [] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i = 0; i < 100; i++) {
			executor.execute(new AddAPenny());
		}
		executor.shutdown(); //no more threads can be added
		//executor.isTerminated() returns false as long as there are still uncompleted threads
		while(!executor.isTerminated()) {
			Thread.yield();
		}
		System.out.println("Balance = " + piggy.getBalance());
	}
}
