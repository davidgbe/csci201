package davidgbe_CSCI201_Assignment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	public static PrintWriter pw;

	public static void main(String[] args) {
		ArrayList<User> usersArray = new ArrayList<User>();
		loadUsers("output.txt", usersArray);
		Scanner scan = new Scanner(System.in);
		mainMenu(scan, usersArray);
	}
	
	public static void mainMenu(Scanner scan, ArrayList<User> users) {
		int decision;
		while(true) {
			decision = getMenuSelection(scan);
			if(decision != 0) {
				break;
			}
			scan.nextLine();
			System.out.println("Invalid input. Please try again.");
		}
		if(decision == 2) {
			createNewUser(scan, users);
		}
		else {
			login(scan, users);
		}
	}
	
	public static int getMenuSelection(Scanner scan) {
		System.out.println("Welcome to the bank.");
		System.out.println("  1) Existing Account Holder");
		System.out.println("  2) Open a New Account");
		System.out.print("What would you like to do? ");
		int result;
		try {
			result = scan.nextInt();
		}
		catch(InputMismatchException e) {
			return 0;
		}
		if(result == 1 || result == 2) {
			scan.nextLine();
			return result;
		}
		return 0;
	}
	
	public static void loadUsers(String dbFile, ArrayList<User> users) {
		FileWriter fw;
		try {
			fw = new FileWriter(dbFile, true);
		}
		catch(IOException e) {
			System.out.println("Could not write to file");
			return;
		}
		pw = new PrintWriter(fw);
		FileReader fr;
		try {
			fr = new FileReader(dbFile);
		}
		catch(Exception e) {
			System.out.println("Input file was invalid");
			return;
		}
		BufferedReader br = new BufferedReader(fr);
		String line;
		while(true) {
			try {
				line = br.readLine();
				if(line == null) {
					break;
				}
				String[] userData = line.split(",");
				users.add(createUser(
						userData[0],
						userData[1],
						Double.parseDouble(userData[2]),
						Double.parseDouble(userData[3])
				));
				
			}
			catch(IOException e) {
				System.out.println("There was an error reading one user from the database");
				break;
			}
		}
	}
	
	public static User createUser(String name, String password, double checking, double savings) {
		double total = checking + savings;
		CheckingAccount cAccount = new CheckingAccount(checking);
		SavingsAccount sAccount;
		if(total < 1000.0) {
			sAccount = new BasicAccount(savings);
		}
		else if (total < 10000.0) {
			sAccount = new PremiumAccount(savings);
		}
		else {
			sAccount = new DeluxeAccount(savings);
		}
		return new User(name, password, cAccount, sAccount);
	}
	
	public static void createNewUser(Scanner scan, ArrayList<User> users) {
		String userName;
		while(true) {
			System.out.print("Username: ");
			try{
				userName = scan.nextLine();
			}
			catch(Exception e) {
				System.out.println("An error occured reading given user name. Please try again.");
				continue;
			}
			if(userName.equals("q")){
				mainMenu(scan, users);
				break;
			}
			boolean validName = true;
			for(int i = 0; i < users.size(); i++) {
				if(userName.equals(users.get(i).name())) {
					validName = false;
					break;
				}
			}
			if(validName) {
				System.out.print("Password: ");
				String pass = scan.nextLine();
				double checking = nextValidPositiveDouble("How much would you like to deposit in checking? ", scan);
				double savings = nextValidPositiveDouble("How much would you like to deposit in savings? ", scan);
				User newUser = createUser(userName, pass, checking, savings);
				users.add(newUser);
				pw.write(newUser.name() + "," + newUser.password() + "," + newUser.checkingAccount().getBalance() + "," + newUser.savingsAccount().getBalance() + "\r\n");
				pw.flush();
				System.out.println();
				mainMenu(scan, users);
				break;
				
			}
			else{
				System.out.println("I’m sorry, but the username " 
						+ userName
						+ " is already associated with an account. " 
						+ "Please try again (or enter ‘q’ to return to the main menu).");
				continue;
			}
		}
	}
	
	public static double nextValidPositiveDouble(String message, Scanner scan) {
		double result;
		while(true) {
			System.out.print(message);
			try{
				result = scan.nextDouble();
				if(result < 0) {
					System.out.println("Input was invalid. Please try again.");
					scan.nextLine();
					continue;
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Input was invalid. Please try again.");
				scan.nextLine();
				continue;
			}
			return result;
		}
	}
	
	public static void login(Scanner scan, ArrayList<User> users) {
		while(true) {
			System.out.print("Username: ");
			String userName = scan.nextLine();
			if(userName.equals("q")) {
				mainMenu(scan, users);
				break;
			}
			int pos = checkUserNameValidity(userName, users);
			if(pos == -1) {
				System.out.println("I’m sorry, but that username and password does not match any at our bank."
						+ " Please try again (or enter ‘q’ to return to the main menu).");
				continue;
			}
			User targetedUser = users.get(pos);
			System.out.print("Password: ");
			String pass = scan.nextLine();
			if(!pass.equals(targetedUser.password())) {
				System.out.println("I’m sorry, but that username and password does not match any at our bank."
						+ " Please try again (or enter ‘q’ to return to the main menu).");
				continue;
			}
			userMenu(scan, targetedUser, users);
			break;
		}
	}
	
	public static int checkUserNameValidity(String userName, ArrayList<User> users) {
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).name().equals(userName)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void userMenu(Scanner scan, User currUser, ArrayList<User> users) {
		boolean logout = false;
		int choice;
		while(true) {
			System.out.println("Welcome to your accounts, " + currUser.name() + " .");
			System.out.println("  1) View Account Information");
			System.out.println("  2) Make a Deposit");
			System.out.println("  3) Make a Withdrawal");
			System.out.println("  4) Determine Balance in x Years");
			System.out.println("  5) Logout");
			System.out.print("What would you like to do? ");
			
			try {
				choice = scan.nextInt();
				break;
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid input. Please try again.");
				scan.nextLine();
				continue;
			}
		}
		switch(choice) {
			case 1: System.out.println("You have a checking account with a balance of $" + currUser.checkingAccount().getBalance());
					System.out.println("You have a " + currUser.savingsAccount().getAccountType()+ " with a balance of $" + currUser.savingsAccount().getBalance());
					break;
			case 2: withdrawOrDeposit(scan, currUser, "deposit");
					break;
			case 3: withdrawOrDeposit(scan, currUser, "withdraw");
					break;
			case 4: interestInXYears(scan, currUser);
					break;
			case 5: logout = true;
					logout(users);
					break;
		}
		if(!logout) {
			userMenu(scan, currUser, users);
		}
	}
	
	public static void logout(ArrayList<User> users) {
		pw.close();
		FileWriter fw;
		try {
			fw = new FileWriter("output.txt");
		}
		catch(IOException e) {
			System.out.println(e.getCause());
			return;
		}
		pw = new PrintWriter(fw);
		for(int i = 0; i < users.size(); i++) {
			User newUser = users.get(i);
			pw.write(newUser.name() + "," + newUser.password() + "," + newUser.checkingAccount().getBalance() + "," + newUser.savingsAccount().getBalance() + "\r\n");
			pw.flush();
		}
		pw.close();
		System.out.println("Thanks you for coming to the bank!");
	}
	
	public static void withdrawOrDeposit(Scanner scan, User user, String method) {
		int choice; 
		while(true) {
			System.out.println("Here are the accounts you have: ");
			System.out.println("  1) Checking");
			System.out.println("  2) " + user.savingsAccount().getAccountType());
			
			if(method.equals("withdraw")) {
				System.out.print("From which account would you like to withdraw? ");
			}
			else {
				System.out.println("Into which account would you like to deposit? ");
			}
			try {
				choice = scan.nextInt();
				if(choice != 1 && choice != 2) {
					System.out.println("Invalid input. Please try again.");
					scan.nextLine();
					continue;
				}
				else {
					break;
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid input. Please try again.");
				scan.nextLine();
				continue;
			}
		}
		String message;
		if(choice == 1) {
			message = createmessage(method, "Checking Account");
			
		}
		else {
			message = createmessage(method, user.savingsAccount().getAccountType());
		}
		double amount = nextValidPositiveDouble(message, scan);
		if(method.equals("withdraw")) {
			if(choice == 1) {
				user.checkingAccount().withdraw(amount);
				System.out.println("You have withdrawn $" + amount + " from your Checking Account." );
			} else {
				user.savingsAccount().withdraw(amount);
				System.out.println("You have withdrawn $" + amount + " from your " + user.savingsAccount().getAccountType() );
			}
		}
		else {
			if(choice == 1) {
				user.checkingAccount().deposit(amount);
				System.out.println("You have deposited $" + amount + " into your Checking Account." );
			} else {
				user.savingsAccount().deposit(amount);
				System.out.println("You have deposited $" + amount + " into your " + user.savingsAccount().getAccountType() );
			}
		}
	}
	
	public static String createmessage(String method, String type) {
		return "How much would you like to " + method + " from your " + type + "? ";
	}
	
	public static void interestInXYears(Scanner scan, User user) {
		int years;
		while(true) {
			System.out.print("In how many years? ");
			try {
				years = scan.nextInt();
				if(years < 0) {
					scan.nextLine();
					System.out.println("Number of years must be positive");
					continue;
				}
				else {
					break;
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Years must be a integer. Please try again.");
				scan.nextLine();
				continue;
			}
		}
		System.out.println("Your " + user.savingsAccount().getAccountType() + " will have the following: ");
		System.out.println("Year                Amount               Interest            ");
		System.out.println("----                ------               --------       ");
		for(int i = 1; i <= years; i++) {
			System.out.print(i);
			createSpaces(20 - String.valueOf(i).length());
			double balance = user.savingsAccount().getBalanceAfterNumYears(i);
			double interest = balance - user.savingsAccount().getBalance();
			balance = (int)(balance * 100) / 100.0;
			interest = (int)(interest * 100) / 100.0;
			System.out.print("$" + balance);
			createSpaces(20 - String.valueOf(balance).length());
			System.out.print("$" + interest);
			createSpaces(19 - String.valueOf(interest).length());
			System.out.println("");	
		}
	}
	
	public static void createSpaces(int i) {
		String toPrint = "";
		for(int j = 0; j < i; j++) {
			toPrint += " ";
		}
		System.out.print(toPrint);
	}
}
