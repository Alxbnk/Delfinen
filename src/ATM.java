import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		
		//init Scanner
		Scanner sc = new Scanner(System.in);
		
		//init Bank
		Bank theBank = new Bank("Bank of Banke");
		
		//Add user with savings account
		User aUser = theBank.addUser("Alex", "Banke", "1337");
		
		//Add checking account
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while (true) {
			//Stay in login prompt until successful
			curUser = ATM.mainMenuPrompt(theBank, sc);
			
			//Stay in main manu untill user quits.
			ATM.printUserMenu(curUser, sc);
		}
		
	}
	/**
	 * Print ATM's login menu
	 * @param theBank - the bank objects whose accounts to use
	 * @param sc - the scanner object
	 * @return - the authenticated User object
	 */
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {
		
		//inits
		String userID;
		String pin;
		User authUser;
		
		//Prompt the user for ID/Pin combo until correct one is reached
		do {
			
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();
			
			//try to get the user object corresponding to the id and pin combo.
			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				System.out.println("Incorrect user ID/Pin combination. Please try again.");
			}
			
		} while(authUser == null);
		
		return authUser;
	}
	
	public static void printUserMenu(User theUser, Scanner sc) {
		
		//print a summary of the user's account
		theUser.printAccountsSummary();
		
		//init
		int choice;
		
		//user menu
		do {
			System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
			System.out.println(" 1. Show Transaction History");
			System.out.println(" 2. Withdraw");
			System.out.println(" 3. Deposit");
			System.out.println(" 4. Transfer");
			System.out.println(" 5. Quit");
			System.out.println();
			System.out.println("Enter choice: ");
			choice = sc.nextInt();
			
			if (choice < 1 || choice > 5) {
				System.out.println("Invalid choice. Please choose 1-5");
			}
			
		} while(choice < 1 || choice > 5);
		
		//Process the choice
		switch (choice) {
			
			case 1:
				ATM.showTransHistory(theUser, sc);
				break;
			case 2:
				ATM.withdrawFunds(theUser, sc);
				break;
			case 3:
				ATM.depositFunds(theUser, sc);
				break;
			case 4:
				ATM.transferFunds(theUser, sc);
				break;
			case 5:
				//Gobble up rest of previous input
				sc.nextLine();
				break;
		}
		
		//redisplay this menu unless the user wants to Quit
		if (choice != 5) {
			ATM.printUserMenu(theUser, sc);
		}
		
	}


	public static void showTransHistory(User theUser, Scanner sc) {
		
		int theAcct;
		
		do {
			System.out.printf("Enter the number (1-%d) of the account, whose transactions you want to see", theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts());
		
		//Print the transaction history
		theUser.printAcctTransHistory(theAcct);
		
	}
	
	public static void withdrawFunds(User theUser, Scanner sc) {
		
		//init
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		
		//Get the account to  transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account to tranfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//Get the amount to transfer
		do {
			System.out.printf("Enter the amount to tranfer (max $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0){
				System.out.println("Amount must be greater than 0.");
			} else if (amount > acctBal) {
				System.out.printf("Insufficient Funds Fucker (max $%.02f)", acctBal);
			}
		} while (amount < 0 || amount > acctBal);
		
		//Gobble up rest of previous input
		sc.nextLine();
		
		//Get the memo
		System.out.println("Enter a memo: ");
		memo = sc.nextLine();
		
		//Do the withdrawal
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);

	}
	
	public static void depositFunds(User theUser, Scanner sc) {
		
		//init
		int toAcct;
		double amount;
		double acctBal;
		String memo;
		
		//Get the account to  transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account to deposit in: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(toAcct);
		
		//Get the amount to deposit
		do {
			System.out.printf("Enter the amount to deposit (Balance: $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0){
				System.out.println("Amount must be greater than 0.");
			} 
		} while (amount < 0);
		
		//Gobble up rest of previous input
		sc.nextLine();
		
		//Get the memo
		System.out.println("Enter a memo: ");
		memo = sc.nextLine();
		
		//Do the withdrawal
		theUser.addAcctTransaction(toAcct, amount, memo);

		
	}
	
	public static void transferFunds(User theUser, Scanner sc) {
		
		//init
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		//Get the account to  transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account to tranfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		
		acctBal = theUser.getAcctBalance(fromAcct);
		
		//Get the account to transfer to
		do {
			System.out.printf("Enter the number (1-%d) of the account to tranfer to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		//Get the amount to transfer
		do {
			System.out.printf("Enter the amount to tranfer (max $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0){
				System.out.println("Amount must be greater than 0.");
			} else if (amount > acctBal) {
				System.out.printf("Insufficient Funds Fucker (max $%.02f)", acctBal);
			}
		} while (amount < 0 || amount > acctBal);
		
		//Do the transfer
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s",  theUser.getAcctUUID(toAcct)));
		
		theUser.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s",  theUser.getAcctUUID(fromAcct)));
	}

}

