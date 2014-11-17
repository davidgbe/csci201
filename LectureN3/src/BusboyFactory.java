import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class BusboyFactory {
	private int numBusboysPerTable;
	private int numBusboys;
	private Hostess hostess;
	private Lock lock = new ReentrantLock();
	private Condition busboyAssigned = lock.newCondition();
	private Tables tables;
	private ArrayList<BusboyThread> allBusboys = new ArrayList<BusboyThread>();
	
	public BusboyFactory(Hostess ht, int numBusboys, int numTablesPerBusboy, Tables tables2) {
		this.hostess = ht;
		this.numBusboys = numBusboys;
		this.numBusboysPerTable = numTablesPerBusboy;
		this.tables = tables2;
		for(int i = 0; i < numBusboys; i++) {
			allBusboys.add(new BusboyThread(ht, i, this, numTablesPerBusboy, tables));
		}
	}
}
