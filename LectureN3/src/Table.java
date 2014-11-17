import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Table {
	
	private int tableNumber;

	private CustomerThread ct;
	private WaiterThread wt;
	private Lock lock = new ReentrantLock();
	private Condition readyCondition = lock.newCondition();
	private Condition cleanedCondition = lock.newCondition();
	private Tables tables;
	private Semaphore numBusboysSemaphore;
	private ArrayList<BusboyThread> allBusboys;
	private int numBusboysPerTable;
	
	public Table(int tableNumber, int numBusboysPerTable, Tables tables) {
		this.tableNumber = tableNumber;
		this.numBusboysSemaphore = new Semaphore(numBusboysPerTable);
		this.numBusboysPerTable = numBusboysPerTable;
		this.tables = tables;
	}
	
	public int getTableNumber() {
		return this.tableNumber;
	}

	public CustomerThread getCustomer() {
		return ct;
	}
	
	public WaiterThread getWaiterThread() {
		return wt;
	}
	
	public Lock getLock() {
		return lock;
	}
	
	public Condition getReadyCondition() {
		return readyCondition;
	}
	
	public Condition getCleanedCondition() {
		return cleanedCondition;
	}

	public void seatTable(CustomerThread ct, WaiterThread wt) { //, BusboyThread bbt) {
		this.ct = ct;
		this.wt = wt;
//		this.bbt = bbt;
	}
	
	public int getNumNeededBusboys() {
		return this.numBusboysSemaphore.availablePermits();
	}
	
	public void addBusboy(BusboyThread bb) throws InterruptedException {
		numBusboysSemaphore.acquire();
		this.allBusboys.add(bb);
		bb.setAsWorking();
	}
	
	private void releaseBusboys() {
		for(int i = 0; i < allBusboys.size(); i++) {
			allBusboys.get(i).finished();
		}
		this.allBusboys.clear();
	}
	
	public void getCleaned() {
		lock.lock();
		try {
			this.cleanedCondition.await();
			this.releaseBusboys();
			this.tables.returnTable(this);
		} catch(InterruptedException ie) {
			
		}
		lock.unlock();
	}


}