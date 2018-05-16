import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	
	//ATT
	private String firstName;
	private String lastName;
	private String uuid;
	private byte pinHash[];
	private ArrayList<Account> accounts;
	
	
	//Constructor
	/**
	 * Create new user
	 * @param firstName - the users first name.
	 * @param lastName - the users last name.
	 * @param pin - the users account pin.
	 * @param theBank - the bank object that the user is a costumer of.
	 */
	public User(String firstName, String lastName, String pin, Bank theBank ){
		//Set User Name
		this.firstName = firstName;
		this.lastName = lastName;
		//Store the pin's MD5 hash rather than the original value
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error, caught NSAE (User)");
			e.printStackTrace();
			System.exit(1);
		}
		//Get a new, unique universal ID for the user
		this.uuid = theBank.getNewUserUUID();
		
		//Create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		//Print log message
		System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
		
	}


	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}


	public String getUUID() {
		return this.uuid;
	}


	public boolean validatePin(String aPin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error, caught NSAE (UserPin)");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}



	public String getFirstName() {
		return this.firstName;
	}


	public void printAccountsSummary() {
		
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for (int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("%d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
		}
		
		System.out.println();
	}


	public int numAccounts() {
		return this.accounts.size();
	}


	public void printAcctTransHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransHistory();
		
	}


	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}


	public String getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}


	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);		
	}
	
	
}
