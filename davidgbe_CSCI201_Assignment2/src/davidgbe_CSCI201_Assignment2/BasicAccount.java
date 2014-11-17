package davidgbe_CSCI201_Assignment2;

public class BasicAccount extends SavingsAccount {

	public BasicAccount(double balance) {
		super(balance);
		this.interestRate = .001;
	}
	
	@Override
	public String getAccountType() {
		return "Basic Account";
	}

}
