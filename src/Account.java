import java.util.ArrayList;

public class Account {

	//ATT
	private String name;
	private String uuid;
	private User holder;
	private ArrayList<Transaction> transactions;
	
	//Constructor
	/**
	 * Create a new account
	 * @param name - name of account.
	 * @param holder - User object that holds this account.
	 * @param theBank - bank that issues the account.
	 */
	public Account(String name, User holder, Bank theBank) {
		
		this.name = name;
		this.holder = holder;
		
		//get new account UUID
		this.uuid = theBank.getNewAccountUUID();
		
		//init transactions
		this.transactions = new ArrayList<Transaction>();
		
	}

	public String getUUID() {
		return this.uuid;
	}

	public String getSummaryLine() {
		//Get the balance
		double balance = this.getBalance();
		
		//format the summary line, depending on whether the balance is negative.
		if (balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		} else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
		}
	}
	
	public double getBalance() {
		
		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}

	
	public void printTransHistory() {
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size()-1; t >= 0; t--) {
			System.out.printf(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}

	public void addTransaction(double amount, String memo) {
		
		//create new transaction and add it to our list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
		
	}
	
	
	
}
