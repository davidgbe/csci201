package lab11;

import java.sql.ResultSet;
import java.util.concurrent.locks.ReentrantLock;

public abstract class SQLCommand implements Runnable
{
  public static final String DB_ADDRESS = "jdbc:mysql://localhost/";
  public static final String DB_NAME = "lab11";
  public static final String DRIVER = "com.mysql.jdbc.Driver";
  public static final String USER = "root";
  public static final String PASSWORD = "";
  protected ReentrantLock queryLock;
  public SQLCommand(ReentrantLock queryLock)
  {
    this.queryLock = queryLock;
  }
  @Override
  public void run() {
	this.queryLock.lock();
	try{
		System.out.print("Executing... ");
	    execute();
	    System.out.println("Done");
	} finally {
		this.queryLock.unlock();
	}
  }
  public abstract boolean execute();
}
