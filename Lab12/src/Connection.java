import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Connection implements Runnable {
	private NumberServer server;
	private Semaphore semaphore;
	//private Semaphore lock;
	private List<Integer> results;

	public Connection(NumberServer ns, Semaphore s, List<Integer> r, Semaphore l) {
		this.server = ns;
		this.semaphore = s;
		//this.lock = l;
		this.results = r;
	}
	
	public void run() {
		while(true) {
			try {
				this.semaphore.acquire();
				int num = this.server.getNumber();
				//lock.acquire();
				results.add(new Integer(num));
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				//lock.release();
				semaphore.release();
			}
		}
	}

}
