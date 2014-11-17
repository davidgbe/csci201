package lab11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class RaceCommand extends SQLCommand {

	public RaceCommand(ReentrantLock queryLock) {
		super(queryLock);
	}

	@Override
	public boolean execute() {
		Connection con = null;
		try {
			con =  DriverManager.getConnection(this.DB_ADDRESS + this.DB_NAME, this.USER, this.PASSWORD);
		}
		catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		if(con == null) {
			System.out.println("Error");
			return false;
		}
		ResultSet results = null;
		ResultSet max = null;
		int maxRaceNum = 0;
		try {
			Statement findMax = con.createStatement();
			max = findMax.executeQuery("select MAX(race_number) max from race_result;");
			try {
				if(max != null) {
					max.next();
					maxRaceNum = max.getInt("max");
				}
			} catch(Exception e) {
				System.out.println("Not setting max num");
				e.printStackTrace();
			}
			Statement stmt = con.createStatement();
			results = stmt.executeQuery("select * from horse order by RAND() limit 8;");
		} catch(Exception e) {
			System.out.println("Error1");
			e.printStackTrace();
			return false;
		}
		if(results == null) {
			System.out.println("Error2");
			return false;
		}  
		try {
			PreparedStatement stmt = con.prepareStatement("insert into race_result (race_number, horse_id, place) values (?, ?, ?);");
			try {
				int i = 1;
				while(results.next()) {
					stmt.setInt(1, maxRaceNum + 1);
					stmt.setInt(2, results.getInt("horse_id"));
					stmt.setInt(3, i);
					stmt.execute();
					i++;
				}
			} catch(Exception e) {
				System.out.println("Error3");
				return false;
			}
			stmt.execute();
			return true; 
		} catch(Exception e) {
			System.out.println("Error4");
			return false;
		}
	}

}
