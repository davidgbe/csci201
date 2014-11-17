package lab11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class AddHorseCommand extends SQLCommand {
	
	public AddHorseCommand(ReentrantLock queryLock) {
		super(queryLock);
	}

	public boolean execute() {
		Connection con = null;
		try {
			con =  DriverManager.getConnection(this.DB_ADDRESS + this.DB_NAME, this.USER, this.PASSWORD);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if(con == null) {
			return false;
		}
		ResultSet results = null;
		try {
			Statement stmt = con.createStatement();
			results = stmt.executeQuery("select * from words order by RAND() limit 2;");
		} catch(Exception e) {
			return false;
		}
		if(results == null) {
			return false;
		}  
		ArrayList<String> words = new ArrayList<String>();
		try {
			while(results.next()) {
				System.out.print(results.getString("word") + " ");
				words.add(results.getString("word"));
			}
		} catch(Exception e) {
			return false;
		}
		if(words.size() != 2) {
			return false;
		} 
		try {
			PreparedStatement stmt = con.prepareStatement("insert into horse (name) values (?);");
			stmt.setString(1, words.get(0) + " " + words.get(1));
			stmt.execute();
		}
		catch(Exception e) {
			return false;
		}
		return true; 
	}
}
