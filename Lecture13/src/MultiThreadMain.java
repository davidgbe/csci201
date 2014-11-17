
public class MultiThreadMain {
	
	public static void main(String []  args) {
		System.out.println("First Line");
		TestThread tt = new TestThread(1);
//		tt.start();
		Thread t = new Thread(tt);
		t.start();
		System.out.println("Last line");
		
	}
}

class TestThread implements Runnable { //extends Thread
	private int num;
	
	public TestThread(int num) {
		this.num = num;
	}
	
	public void run() {
		System.out.println(num + "Hello");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			return; //kills thread
		}
		System.out.println(num + "World");
	}
}
