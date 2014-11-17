import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Tables {
	private int numTables;
	private int numOccupiedTables;
	private Table[] tableArray;
	private Lock lock = new ReentrantLock();
	private Condition noTablesAvailable = lock.newCondition();
	private Condition tableNeedsCleaning = lock.newCondition();
	public boolean tableCleaning = false;
	
	public Tables(int numTables) {
		this.numTables = numTables;
		this.tableArray = new Table[numTables];
		this.numOccupiedTables = 0;
	}
	
	public void giveTableBusBoy(BusboyThread bb) throws InterruptedException {
		lock.lock();
		try {
			if(tableCleaning) {
				
			} else {
				tableNeedsCleaning.await();
			}
			int min = Integer.MAX_VALUE;
			int pos = -1;
			for (int i=0; i < this.numTables; i++) {
				int needed = this.tableArray[i].getNumNeededBusboys();
				if(needed <= min && needed != 0) {
					min = this.tableArray[i].getNumNeededBusboys();
					pos = i;
				}
				this.tableArray[pos].addBusboy(bb);
				if(needed == 1) {
					this.tableArray[pos].getCleanedCondition().signalAll();
				}
				
			}
		} finally {
			lock.unlock();
		}
	}
	
	public Table getTable() throws InterruptedException {
		lock.lock();
		Table tableToReturn = null;
		try {
			while(this.numOccupiedTables == this.numTables) {
				Restaurant.addMessage("No tables available.");
				// wait for a table to become available
				noTablesAvailable.await();
				Restaurant.addMessage("Table became available.");
			}
			// when this thread is signalled, find the table that is available
			for (int i=0; i < this.numTables; i++) {
				if (tableArray[i] == null) {
					tableArray[i] = new Table(i, 4, this);
					tableToReturn = tableArray[i];
					numOccupiedTables++;
					break;
				}
			}
		} finally {
			lock.unlock();
		}
		Restaurant.changeTableStatus(tableToReturn.getTableNumber());
		return tableToReturn;
	}
	
	public void cleanTable(Table table) {
		lock.lock();
		try {
			table.getCleaned();
		} finally {
			lock.unlock();
		}
	}
	
	public void returnTable(Table table) {
		lock.lock();
		try {
			for (int i=0; i < this.numTables; i++) {
				if (i == table.getTableNumber()) {
					tableArray[i] = null;
					numOccupiedTables--;
					Restaurant.addMessage("Table " + table.getTableNumber() + " is now available.");
					Restaurant.changeTableStatus(table.getTableNumber());
					noTablesAvailable.signal();
				}
			}
		} finally {
			lock.unlock();
		}
	}
}