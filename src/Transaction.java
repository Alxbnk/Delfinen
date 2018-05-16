import java.util.Date;

public class Transaction {

	//ATT
	private double amount;
	private Date timestamp;
	private String memo;
	private Account inAccount;
	
	//Constructor
	public Transaction(double amount, Account inAccount) {
		
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}
	
	public Transaction(double amount, String memo, Account inAccount) {
		//Call the two arg constructor
		this(amount, inAccount);
		//memo
		this.memo = memo;
	}

	public double getAmount() {
		return this.amount;
	}

	public String getSummaryLine() {
		
		if (this.amount >= 0) {
			return String.format("%s : $%.02f : %s", this.timestamp.toString(),
					this.amount, this.memo);
		} else {
			return String.format("%s : $(%.02f) : %s", this.timestamp.toString(),
					-this.amount, this.memo);
		}
	}
	
}
