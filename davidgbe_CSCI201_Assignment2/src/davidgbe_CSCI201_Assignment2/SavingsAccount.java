package davidgbe_CSCI201_Assignment2;

public abstract class SavingsAccount extends BaseAccount {
	protected double interestRate;

	public SavingsAccount(double balance) {
		super(balance);
	}
	
	protected double getBalanceAfterNumYears(int numYears) {
		return this.getBalance() * Math.pow( (1.0 + this.interestRate), (double)(numYears) );
	}
	
	protected double getInterestAfterNumYears(int numYears) {
		return this.getBalance() * Math.pow(this.interestRate, (double)(numYears));
	}
	
	public abstract String getAccountType();

}
