import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class MySQLTest {

	public static void main(String[] args) {
		Connection con = null;
		try {
			con =  DriverManager.getConnection("jdbc:mysql://localhost/java_lab_db", "root", "");
			System.out.println("Worked");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if(con != null) {
			Scanner scan = new Scanner(System.in);
			System.out.println("Please enter a first name:");
			String first = scan.nextLine();
			System.out.println("Please enter a last name:");
			String last = scan.nextLine();
			MySQLTest.insertVals(con, first, last);
			
		}
		MySQLTest.retrieveAllUsers(con);
		
	}
	
	public static void insertVals(Connection con, String first, String last) {
		try {
			PreparedStatement stmt = con.prepareStatement("insert into users (first_name, last_name) values (?, ?);");
			stmt.setString(1, first);
			stmt.setString(2, last);
			stmt.execute();
		}
		catch(Exception e) {
			System.out.println("Failed to execute statment");
			e.printStackTrace();
		}
	}
	
	public static void retrieveAllUsers(Connection con) {
		try {
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery("select * from users;");
			while(results.next()) {
				System.out.print(results.getInt("key_id") + " ");
				System.out.print(results.getString("first_name") + " ");
				System.out.println(results.getString("last_name") + " ");

			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
