package davidgbe_CSCI201_Assignment2;

public class PremiumAccount extends SavingsAccount {

	public PremiumAccount(double balance) {
		super(balance);
		this.interestRate = .01;
	}

	@Override
	public String getAccountType() {
		return "Premium Account";
	}

}
