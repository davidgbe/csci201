package csci201.syncronization;

public class PiggyBank {
	private int balance = 0;
	public int getBalance() {
		return this.balance;
	}
	public synchronized void deposit(int amount) {
		int newBalance = this.balance + amount; //this.balance keeps being reset to one by new threads 
		//known as a race condition
		try {
			Thread.sleep(1);
		} catch (InterruptedException ie) {
			System.out.println("ie: " + ie.getMessage());
		}
		this.balance = newBalance; 
		//locks are by instance for non-static methods
		//are by Class for static methods
	}
}
