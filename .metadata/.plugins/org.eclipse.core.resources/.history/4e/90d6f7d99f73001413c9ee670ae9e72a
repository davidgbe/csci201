import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;


public class ServerUser extends User implements Runnable {
	
	private Socket userSocket;
	
	public static final String DB_ADDRESS = "jdbc:mysql://localhost/";
	public static final String DB_NAME = "group_db";
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	private static ReentrantLock lock = new ReentrantLock();
	
	public ServerUser(Socket s) {
		super(-1, "", 0, 0, 0, new HashMap<String, Integer>());
		this.userSocket = s;
	}
	
	public boolean login(String username, String pass) {
		PreparedStatement stmt = con.prepareStatement("SELECT id FROM users WHERE username = ? AND password = ?;");
		stmt.setString(1, username);
		stmt.setString(2, pass);
		ResultSet results = stmt.executeQuery();
		if(results.next()) {
			this.setID(results.getInt("id"));
			this.setUsername(username);
			this.fetch();
			return true;
		} 
		return false;
	}
	
	public boolean createUser(String username, String pass) {
		PreparedStatement stmt = con.prepareStatement("SELECT id FROM users WHERE username = ? AND password = ?;");
		stmt.setString(1, username);
		stmt.setString(2, pass);
		ResultSet results = stmt.executeQuery();
		if(!results.next()) {
			//throw existing user
			return false;
		}
		PreparedStatement stmt = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?);");
		stmt.setString(1, username);
		stmt.setString(2, pass);
		return login(username, pass);
	}
	
	private Connection establishConnection() {
		Connection con = null;
		try {
			con =  DriverManager.getConnection(this.DB_ADDRESS + this.DB_NAME, this.USER, this.PASSWORD);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public boolean update () {
		lock.lock();
		try {
			Connection con = establishConnection();
			if(con == null) {
				System.out.println("Unable to establish connection");
				return;
			}
			PreparedStatement stmt = con.prepareStatement("INSERT INTO users (wins, total_games, money, steroids, morphine, epinephrine) VALUES (?, ?, ?, ?, ?, ?) WHERE id = ?;");
			stmt.setInt(1, this.wins);
			stmt.setInt(2, this.wins + this.losses);
			stmt.setInt(3, this.money);
			stmt.setInt(4, this.items.get('steroids').intValue());
			stmt.setInt(5, this.items.get('morphine').intValue());
			stmt.setInt(6, this.items.get('epinephrine').intValue());
			stmt.setInt(7, this.id);
			stmt.execute();
		} catch(Exception e) {
			e.printStackTrace();
			lock.unlock();
			return false;
		} finally {
			lock.unlock();
			return true;
		}
	}
	
	public boolean fetch() {
		lock.lock()
		try {
			Statement stmt = con.createStatement();
			if(con == null) {
				System.out.println("Unable to establish connection");
				return;
			}
			ResultSet results = stmt.executeQuery("SELECT wins, total_games, money, steroids, morphine, epinephrine FROM users WHERE id = " + this.id + ";");
			if(results == null) {
				System.out.println("There were no results")
				return;
			}
			results.next();
			this.wins = results.getInt("wins");
			this.losses = results.getInt("total_games") - this.wins;
			this.money = results.getInt("money");
			this.items.put("steroids", results.getInt("steroids"));
			this.items.put("morphine", results.getInt("morphine"));
			this.items.put("epinephrine", results.getInt("epinephrine"));
		} catch(Exception e) {
			e.printStackTrace();
			lock.unlock();
			return false;
		} finally {
			lock.unlock();
			return false;
		}
	}
	
	private void setID(int id) {
		this.id = id;
	}
	
	public void run() {
		
	}

}
