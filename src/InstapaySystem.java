import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class InstapaySystem {
    private User user;
    private static Scanner scanner = new Scanner(System.in);
    public DB db = new DbList();
    public API api;
    private SignUp signup = new SignUp(api);

    private SignIn signin = new SignIn();

    public void DisplayWelcome() {
        // display sign in and sign up
        System.out.println("1. Sign up");
        System.out.println("2. Sign in");
        System.out.println("3. Exit");
    }

    public void DisplayScreen() {
        int choice;
        do {
            DisplayWelcome();
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    user = MakeSignup();
                    if (user.getUserType() == UserType.BANK_USER) {
                        api = new BankAPI();
                    } else {
                        api = new WalletAPI();
                    }
                    if (signup.makeSignup(user)) {
                        System.out.println("sign up done successfully");
                        db.addUser(user);
                    }

                    break;
                case 2:
                    MakeSignin();
                    break;
                case 3:
                    System.out.println("Exiting Instapay System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid Input!");
            }
        } while (choice != 3);
    }

    public boolean login(String Username, String Password) {

        if (db.findUserNameANDPassword(Username, Password)) {
            // Display success message
            return true;
        } else {
            // Display failure message
            return false;
        }
    }

    private boolean MakeSignin() {

        User siginUser = signin.SignInPut();
        if (siginUser == null) {
            System.out.println("Login failed. Invalid username or password.");
            return false;
        }

        // System.out.println("Username: " + siginUser.getUserName());
        // System.out.println("Password: " + siginUser.getPassword());

        if (!login(siginUser.getUserName(), siginUser.getPassword())) {
            System.out.println("Login failed. Invalid username or password.");
            return false;
        } else {
            System.out.println("Login successful!");
        }

        // move to other menue

        int option;

        do {
            displayMenu();
            option = getUserChoice();

            switch (option) {
                case 1:
                    payBillOption();
                    break;
                case 2:
                    transferMoneyOption();
                    break;
                case 3:
                    System.out.println("Balance is " + user.getBalance());
                    break;
                case 4:
                    System.out.println("Exiting Instapay System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid Input!");
            }
        } while (option != 4);

        return true;
    }

    private User MakeSignup() {
        // to be implemented
        System.out.println("Enter Type of account (ex: wallet, bank)");
        System.out.println("1.Bank Account");
        System.out.println("2.Wallet Account");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) // Bank Account
        {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            System.out.print("Enter mobile number: ");
            String mobile = scanner.nextLine();

            System.out.print("Enter CVV: ");
            int cvv = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter card number: ");
            String cardNum = scanner.nextLine();

            System.out.print("Enter card expiry date (MM/dd/yyyy): ");
            String expDateString = scanner.nextLine();
            Date expDate = null;

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                expDate = dateFormat.parse(expDateString);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in MM/dd/yyyy format.");
            }
            api = new BankAPI();
            user = signup.getInfoUserBank(username, password, mobile, cvv, cardNum, expDate);
            user.setUserType(UserType.BANK_USER);
        } else if (choice == 2) { // Wallet Account
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            System.out.print("Enter mobile number: ");
            String mobile = scanner.nextLine();

            api = new WalletAPI();
            user = signup.getInfoWallet(username, password, mobile);
            user.setUserType(UserType.WALLET_USER);

        } else {
            System.out.println("Wrong input");
            return null;
        }

        return user;

    }

    // use when signed in
    private void displayMenu() {
        System.out.println("Instapay System Menu:");
        System.out.println("1. Pay Bill");
        System.out.println("2. Transfer");
        System.out.println("3. View Balance");
        System.out.println("4. Exit");
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        return num;
    }

    private void payBillOption() {
        System.out.println("Enter bill type (GAS, ELECTRICITY, WATER):");
        String billTypeInput = scanner.next().toUpperCase();
        BillType billType;

        try {
            billType = BillType.valueOf(billTypeInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid bill type. Exiting.");
            return;
        }

        System.out.println("Enter the company name:");
        String companyName = scanner.next();
        scanner.nextLine();

        System.out.println("Enter the electronic payment number:");
        int electronicPaymentNo = scanner.nextInt();
        scanner.nextLine();

        // Create Bill
        Bill bill = BillFactory.createBill(billType, companyName, electronicPaymentNo);

        if (bill == null) {
            System.out.println("Bill creation failed. Returning to the menu.\n");
            return;
        }

        // Display bill details
        System.out.println("Bill Details:");
        System.out.println("Type: " + bill.getBillType());
        System.out.println("Company: " + bill.getCompanyName());
        System.out.println("Electronic Payment Number: " + bill.getElectronicPaymentNo());
        System.out.println("Expenses: " + bill.getBillExpenses());

        // Ask user if they want to proceed with the payment
        System.out.println("Do you want to pay this bill? (Y/N):");
        String choice = scanner.next().toUpperCase();

        if ("Y".equals(choice)) {

            System.out.println("Balance of User (check)" + user.getBalance());

            // Pay Bill
            if (PayBill.payBill(user, bill)) {

                if (user.getUserType() == UserType.BANK_USER) {
                    api = new BankAPI();

                } else if (user.getUserType() == UserType.WALLET_USER) {
                    api = new WalletAPI();
                }

                api.updateBalance(user, user.getBalance());
                // System.out.println("new Balance: " + user.getBalance());

                System.out.println("Bill paid successfully \n");
            } else {
                System.out.println("Failed to pay the bill. Insufficient balance.\n");
            }
        } else if ("N".equals(choice)) {
            System.out.println("Payment canceled. Returning to the menu.\n");
        } else {
            System.out.println("Invalid input. Returning to the menu.\n");
        }
    }

    private void transferMoneyOption() {
        System.out.println("Select receiver type: ");
        System.out.println("1. Instapay Account");
        System.out.println("2. Bank");
        System.out.println("3. Wallet");

        int receiverType = scanner.nextInt(); // Use the existing scanner object
        scanner.nextLine();

        double senderBalance;

        if (user.getUserType() == UserType.INSTAPAY_USER) {
            // For InstapayUser, check in the DB
            if (db.verifyUser(user)) {
                senderBalance = user.getBalance();
            } else {
                System.out.println("Sender verification failed. Returning to the menu.");
                return;
            }
        } else if (user.getUserType() == UserType.BANK_USER || user.getUserType() == UserType.WALLET_USER) {
            // For BankUser or WalletUser, use the API verification
            if (!api.verify(user)) {
                System.out.println("Sender verification failed. Returning to the menu.");
                return;
            }
            senderBalance = user.getBalance();
        } else {
            System.out.println("Invalid sender type. Returning to the menu.");
            return;
        }

        System.out.println("Enter transfer amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (senderBalance < amount) {
            System.out.println("Insufficient balance for the transfer. Returning to the menu.");
            System.out.println(senderBalance);
            return;
        }

        TransferStrategy transferStrategy = createTransferStrategy(receiverType, user);

        if (transferStrategy != null) {
            Transfer transfer = new Transfer(transferStrategy);

            // For Wallet transfer, create the receiver here
            if (receiverType == 3) {
                System.out.println("Enter receiver Mobile NO for Wallet transfer: ");
                String receiverMobileNo = scanner.next();
                scanner.nextLine();

                WalletUser receiver = createWalletUser("", "", receiverMobileNo);
                transfer.performTransfer(amount, user, receiver);

            } else if (receiverType == 2) {
                System.out.println("Enter receiver card number for Bank transfer: ");
                String receiverCardNo = scanner.next();
                scanner.nextLine();

                BankUser receiver = createBankUser("", "", receiverCardNo);
                transfer.performTransfer(amount, user, receiver);
            } else if (receiverType == 1) {
                System.out.println("Enter receiver username for Instapay transfer: ");
                String receiverUsername = scanner.next();
                scanner.nextLine();

                User receiver = createUser(receiverUsername, "");
                transfer.performTransfer(amount, user, receiver);
            } else {
                // For Instapay and Bank transfers, receiver is not needed in this context
                System.out.println("Invalid input");
            }
        }
    }

    private TransferStrategy createTransferStrategy(int receiverType, User sender) {
        TransferStrategy transferStrategy = null;

        switch (receiverType) {
            case 1: // Instapay Account
                transferStrategy = new TransferToInstapayStrategy(db);
                break;
            case 2: // Bank
                if (sender.getUserType() == UserType.BANK_USER) {
                    transferStrategy = new TransferToBankStrategy(new BankAPI());
                } else {
                    System.out.println("Invalid sender type for Bank transfer. Returning to the menu.");
                }
                break;
            case 3: // Wallet
                transferStrategy = new TransferToWalletStrategy(new WalletAPI());
                break;
            default:
                System.out.println("Invalid receiver type.");
                break;
        }

        return transferStrategy;
    }

    private User createUser(String username, String password) {
        // creates a user object to search for without adding the user in the database
        return new User(username, password, "", 0.0, UserType.INSTAPAY_USER);
    }

    private BankUser createBankUser(String username, String password, String cardNo) {
        // creates a user object to search for without adding the user in the database
        return new BankUser(username, password, "", 0.0, "", cardNo, 000, null);
    }

    private WalletUser createWalletUser(String username, String password, String MobileNo) {
        // creates a user object to search for without adding the user in the database
        return new WalletUser(username, password, MobileNo, 0.0);
    }

}
