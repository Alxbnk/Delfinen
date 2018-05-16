import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private String name;
	private ArrayList<User> users;
	private ArrayList<Account> accounts;
	
	//Constructor
	/**
	 * Generate a new universal id for a user.
	 * @return the uuid.
	 */
	public String getNewUserUUID() {
		//Inits
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		do {
			//Generate the number
			uuid = "";
			for (int i = 0; i<len; i++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			//Check to make sure it's unique
			nonUnique = false;
			for (User u : this.users) {
				if (uuid.compareTo(u.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while (nonUnique);
		
		return uuid;
	}

	public String getNewAccountUUID() {
		//Inits
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique;
		
		do {
			//Generate the number
			uuid = "";
			for (int i = 0; i<len; i++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			//Check to make sure it's unique
			nonUnique = false;
			for (Account a : this.accounts) {
				if (uuid.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while (nonUnique);
		
		return uuid;
	}
	
	public Bank(String name) {
		
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
	
	public User addUser(String firstName, String lastName, String pin) {
		//Create new user and add it to our list
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		//Create savings account
		Account newAccount = new Account("Savings", newUser, this);

		//Add to holder and bank list
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		
		return newUser;
	}
	/**
	 * Get the User object associated with a particular userID and pin if they are valid.
	 * @param UserID - the UUID of the user to log in.
	 * @param pin - the pin of the user.
	 * @return - the user object, if the login is successful, or null if it's not.
	 */
	public User userLogin(String userID, String pin) {
		
		//First we search through the list of users
		for(User u : this.users) {
			
			//Check user ID is correct.
			if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		return null;
	}

	public String getName() {
		return this.name;
	}
}
