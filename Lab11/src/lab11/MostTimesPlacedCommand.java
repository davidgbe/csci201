package lab11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class MostTimesPlacedCommand extends SQLCommand{
	private int place;

	public MostTimesPlacedCommand(ReentrantLock queryLock, int place) {
		super(queryLock);
		this.place = place;
	}

	@Override
	public boolean execute() {
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
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
			PreparedStatement stmt = con.prepareStatement("select * from race_result where place = ?;");
			stmt.setInt(1, this.place);
			results = stmt.executeQuery();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		if(results == null) {
			System.out.println("NO RESULTS");
			return false;
		}  
		try {
			while(results.next()) {
				int id = results.getInt("horse_id");
				if(hm.containsKey(new Integer(id))) {
					hm.put(new Integer(id), new Integer(hm.get(new Integer(id)).intValue() + 1) );
				} else {
					hm.put(new Integer(id), new Integer(1));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		int maxKey = -1;
		int max = -1;
		for(Integer key: hm.keySet()) {
			if(maxKey == -1 || hm.get(key).intValue() > max) {
				maxKey = key.intValue();
				max = hm.get(key).intValue();
			}
		}
		String name = null;
		try {
			PreparedStatement stmt = con.prepareStatement("select * from horse where horse_id = ?");
			stmt.setInt(1, maxKey);
			results = stmt.executeQuery();
			results.next();
			name = results.getString("name");
			System.out.println(name + " finished number " + this.place + " the most at " + max + " times.");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
