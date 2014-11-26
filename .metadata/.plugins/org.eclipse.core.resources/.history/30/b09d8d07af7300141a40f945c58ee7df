import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public boolean login(String username, String pass, Connection con) {
		lock.lock();
		if(con == null) {
			con = establishConnection();
		}
		if(con == null) {
			//null;
			lock.unlock();
			return false;
		}
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT id FROM users WHERE username = ? AND password = ?;");
			stmt.setString(1, username);
			stmt.setString(2, pass);
			ResultSet results = stmt.executeQuery();
			if(results.next()) {
				this.setID(results.getInt("id"));
				this.setUsername(username);
				this.fetch();
				lock.unlock();
				return true;
			} 
			lock.unlock();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			lock.unlock();
			return false;
		}
	}
	
	public boolean createUser(String username, String pass) {
		Connection con = establishConnection();
		if(con == null) {
			//null;
			lock.unlock();
			return false;
		}
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT id FROM users WHERE username = ? AND password = ?;");
			stmt.setString(1, username);
			stmt.setString(2, pass);
			ResultSet results = stmt.executeQuery();
			if(!results.next()) {
				//throw existing user
				lock.unlock();
				return false;
			}
			PreparedStatement stmt2 = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?);");
			stmt2.setString(1, username);
			stmt2.setString(2, pass);
			return login(username, pass, con);
		} catch (SQLException e) {
			e.printStackTrace();
			lock.unlock();
			return false;
		}
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
				return false;
			}
			PreparedStatement stmt = con.prepareStatement("INSERT INTO users (wins, total_games, money, steroids, morphine, epinephrine) VALUES (?, ?, ?, ?, ?, ?) WHERE id = ?;");
			stmt.setInt(1, this.getWins());
			stmt.setInt(2, this.getWins() + this.getLosses());
			stmt.setInt(3, this.getMoney());
			stmt.setInt(4, this.getItems().get("steroids").intValue());
			stmt.setInt(5, this.getItems().get("morphine").intValue());
			stmt.setInt(6, this.getItems().get("epinephrine").intValue());
			stmt.setInt(7, this.getID());
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
		lock.lock();
		Connection con = establishConnection();
		if(con == null) {
			//badddd
			lock.unlock();
			return false;
		}
		try {
			Statement stmt = con.createStatement();
			if(con == null) {
				System.out.println("Unable to establish connection");
				lock.unlock();
				return false;
			}
			ResultSet results = stmt.executeQuery("SELECT wins, total_games, money, steroids, morphine, epinephrine FROM users WHERE id = " + this.getID() + ";");
			if(results == null) {
				System.out.println("There were no results");
				lock.unlock();
				return false;
			}
			results.next();
			this.setWins( results.getInt("wins") );
			this.setLosses( results.getInt("total_games") - this.getWins() );
			this.setMoney( results.getInt("money") );
			this.getItems().put("steroids", results.getInt("steroids"));
			this.getItems().put("morphine", results.getInt("morphine"));
			this.getItems().put("epinephrine", results.getInt("epinephrine"));
		} catch(Exception e) {
			e.printStackTrace();
			lock.unlock();
			return false;
		} finally {
			lock.unlock();
			return false;
		}
	}
	
	private void parse(Message msg) {
		
	}
	
	public void run() {
		try {
			ObjectInputStream in  = new ObjectInputStream(userSocket.getInputStream());
			while(true){
				Object objectReceived = in.readObject();
				if(objectReceived instanceof Message){
					Message messageReceived = (Message)objectReceived;
					// do stuff with message
				}
			}
			
		} catch (Exception ioe) {
			System.out.println("IOException in ServerUser run method: " + ioe.getMessage());
		}
	}

}
