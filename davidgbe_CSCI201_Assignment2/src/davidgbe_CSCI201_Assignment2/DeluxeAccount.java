package davidgbe_CSCI201_Assignment2;

public class DeluxeAccount extends SavingsAccount {

	public DeluxeAccount(double balance) {
		super(balance);
		this.interestRate = .05;
	}

	@Override
	public String getAccountType() {
		return "Deluxe Account";
	}

}
