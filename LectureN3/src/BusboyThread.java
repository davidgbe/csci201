import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BusboyThread extends Thread {
	private Hostess hostess;
	private int busboyNumber;
	private BusboyFactory factory;
	private Lock busboyLock = new ReentrantLock();
	private Condition busboyFinished = busboyLock.newCondition();
	private Tables tables;
	private int numBusboysPerTable;
	
	public BusboyThread(Hostess ht, int num, BusboyFactory fac, int numBusboysPerTable, Tables tables) {
		this.hostess = ht;
		this.busboyNumber = num;
		this.factory = fac;
		this.numBusboysPerTable = numBusboysPerTable;
		this.tables = tables;
		this.start();
	}

	public void finished() {
		this.busboyFinished.signal();
	}
	
	public int getNum() {
		return this.busboyNumber;
	}
	
	public void setAsWorking() throws InterruptedException {
		this.busboyLock.lock();
		this.busboyFinished.await();
		this.busboyLock.unlock();
		this.sleep((long)(1000.0/this.numBusboysPerTable));
	}
	
	public Hostess getHostess() {
		return this.hostess;
	}
	
	public void run() {
		try {
			while(true) {
				this.tables.giveTableBusBoy(this);
			}
		} catch(InterruptedException ie) {
			
		}
	}
}