import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class InstapaySystem {
    private User user;
    private User userdb;
    private BankUser Buser;
    private static Scanner scanner = new Scanner(System.in);
    public DB db = new DbList();
    public API api;
    private SignUp signup = new SignUp(api);
    private static TransferFactory tfactory;

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
                    if (user == null) {
                        break;
                    }
                    if (user.getUserType() == UserType.BANK_USER) {
                        api = new BankAPI();
                    } else {
                        api = new WalletAPI();
                    }
                    if (signup.makeSignup(user)) {
                        System.out.println("sign up done successfully" + '\n');
                        // System.out.println(user.getUserName() + " " + user.getPassword());

                        userdb = user.CopyUser(userdb);
                        // System.out.println("before db " + userdb.getUserType());
                        db.addUser(userdb);
                        db.addBankUser(Buser);

                        // System.out.println(db.findUserType(userdb.getUserType()));
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

    // problem in findUserNameANDPassword
    public boolean login(String Username, String Password) {

        if (db.findUserNameANDPassword(Username, Password)) {
            // db.findUser(user) return
            // null but should return user
            // this line is needed in transfer
            return true;
        } else {
            return false;
        }
    }

    public User SignInPut() {
        // try {
        System.out.println("Enter UserName:");
        String Username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User user = db.getUser(Username, password);

        if (user != null) {

            user.setUserName(Username);
            user.setPassword(password);

        }

        return user;
    }

    private boolean MakeSignin() {

        user = SignInPut();
        if (user == null) {
            System.out.println("Something went wrong!!.");
            return false;
        }

        // System.out.println("Username: " + user.getUserName());
        // System.out.println("Password: " + user.getPassword());

        if (!login(user.getUserName(), user.getPassword())) {
            System.out.println("Login failed. Invalid username or password.");
            return false;
        } else {
            user.setUserType(db.findUserType(user.getUserName()));
            System.out.println("Login successful!" + '\n');
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
            Buser = signup.getInfoUserBank(username, password, mobile, cvv, cardNum, expDate);
            if (user != null) {
                user.setUserType(UserType.BANK_USER);
            }
        } else if (choice == 2) { // Wallet Account
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            System.out.print("Enter mobile number: ");
            String mobile = scanner.nextLine();

            api = new WalletAPI();
            user = signup.getInfoWallet(username, password, mobile);

            if (user != null) {
                user.setUserType(UserType.WALLET_USER);
            }

        } else {
            System.out.println("Wrong input");
            return null;
        }

        // if (user.getUserType() == null) {
        // System.out.println("NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        // }

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

        if (receiverType > 3 && receiverType < 1) {
            System.out.println("Invalid input");
            return;
        }

        double senderBalance;

        if (user.getUserType() == UserType.BANK_USER || user.getUserType() == UserType.WALLET_USER) {
            if (db.verifyUser(user)) {
                senderBalance = user.getBalance();
            } else {
                System.out.println("db Sender verification failed. Returning to the menu.");
                return;
            }

            // For BankUser or WalletUser, use the API verification
            if (user.getUserType() == UserType.BANK_USER) {
                api = new BankAPI();
            }
            if (user.getUserType() == UserType.WALLET_USER) {
                api = new WalletAPI();
            }

            if (!api.verify(user)) {
                System.out.println("api Sender verification failed. Returning to the menu.");
                return;
            }
            senderBalance = user.getBalance();
        } else {
            System.out.println(user.getUserType());
            // System.out.println(db.findUserType(null));
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

        TransferStrategy transferStrategy = tfactory.createTransferStrategy(receiverType, user);

        if (transferStrategy != null) {
            Transfer transfer = new Transfer(transferStrategy);

            // For Wallet transfer, create the receiver here
            if (receiverType == 3) {
                System.out.println("Enter receiver Mobile NO for Wallet transfer: ");
                String receiverMobileNo = scanner.next();
                scanner.nextLine();

                WalletUser receiver = createWalletUser(receiverMobileNo);
                // System.out.println("BL");
                transfer.performTransfer(amount, senderBalance, user, receiver);

                if (db.findUserByphone(receiverMobileNo) != null) {
                    db.updateBalance(db.findUserByphone(receiverMobileNo),
                            amount + db.findUserByphone(receiverMobileNo).getBalance());
                }

            } else if (receiverType == 2) {
                // System.out.println("User Typeeeeee is " + user.getUserType());

                if (user.getUserType() != UserType.BANK_USER) {
                    System.out.println("You should be a BankUser to transfer to BankUser!!");
                    return;
                }

                System.out.println("Enter receiver card number for Bank transfer: ");
                String receiverCardNo = scanner.next();
                scanner.nextLine();

                BankUser receiver = createBankUser(receiverCardNo);
                transfer.performTransfer(amount, senderBalance, user, receiver);

                // Update Balance of BankUser as a reciever
                if (db.findCardNo(receiverCardNo) != null) {
                    db.updateBalance(db.findCardNo(receiverCardNo),
                            db.getBalance(db.findCardNo(receiverCardNo)) + amount);
                }

            } else if (receiverType == 1) {
                System.out.println("Enter receiver username for Instapay transfer: ");
                String receiverUsername = scanner.next();
                scanner.nextLine();

                UserType type = db.findUserType(receiverUsername);
                User receiver = createUser(receiverUsername, type);

                if (!db.findUserName(receiverUsername)) {
                    System.out.println("Receiver username not found");
                    return;
                }

                transfer.performTransfer(amount, senderBalance, user, receiver);
                // Update Balance of User as a reciever
                db.updateBalance(receiver, db.getBalance(receiver) + amount);
                // System.out.println("balance of reciver" + db.getBalance(receiver));

            } else {
                // For Instapay and Bank transfers, receiver is not needed in this context
                System.out.println("Invalid input");
                return;
            }
        }
    }

    private User createUser(String username, UserType type) {
        // creates a user object to search for without adding the user in the database
        if (type == UserType.BANK_USER)
            return new BankUser(username, "", "", 0.0,
                    "", "", 0, new Date());
        if (type == UserType.WALLET_USER)
            return new WalletUser(username, "", "", 0.0);
        return null;
        // return new User(username, "", "", 0.0, type);
    }

    private BankUser createBankUser(String cardNo) {
        // creates a user object to search for without adding the user in the database
        return new BankUser("", "", "", 0.0, "", cardNo, 000, null);
    }

    private WalletUser createWalletUser(String MobileNo) {
        // creates a user object to search for without adding the user in the database
        return new WalletUser("", "", MobileNo, 0.0);
    }
}
