package davidgbe_CSCI201_Assignment2;

public class User {
	private String name;
	private String password;
	private CheckingAccount checking;
	private SavingsAccount savings;
	
	public User(String name, String pass, CheckingAccount cAccount, SavingsAccount sAccount) {
		this.name = name;
		this.password = pass;
		this.checking = cAccount;
		this.savings = sAccount;
	}
	
	public String name() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String password() {
		return this.password;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public CheckingAccount checkingAccount() {
		return this.checking;
	}
	
	public void setCheckingAccount(CheckingAccount account) {
		this.checking = account;
	}
	
	public SavingsAccount savingsAccount() {
		return this.savings;
	}
	
	public void setSavingsAccount(SavingsAccount account) {
		this.savings = account;
	}
	
}
