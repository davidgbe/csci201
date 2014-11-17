package davidgbe_CSCI201_Assignment2;

public class CheckingAccount extends BaseAccount {

	public CheckingAccount(double balance) {
		super(balance);
	}
	
	@Override
	protected double getBalanceAfterNumYears(int numYears) {
		return this.getBalance();
	}

	@Override
	public String getAccountType() {
		return "Checking Account";
	}

}
