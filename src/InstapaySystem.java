import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class InstapaySystem {
    private User user;
    private static Scanner scanner = new Scanner(System.in);
    public DB db;
    public API api;
    private SignUp signup = new SignUp();

    private SignIn signin;

    public void DisplayWelcome(){
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
                    signup.makeSignup(user);
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

    private boolean MakeSignin()
    {
        // to be implemented
        return true;
    }

    private User MakeSignup()
    {
        // to be implemented
        System.out.println("Enter Type of account (ex: wallet, bank)");
        System.out.println("1.Bank Account");
        System.out.println("2.Wallet Account");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1)
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

            user = signup.getInfoUserBank(username, password, mobile, cvv, cardNum, expDate);
        } else if (choice == 2) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            System.out.print("Enter mobile number: ");
            String mobile = scanner.nextLine();

            user = signup.getInfoWallet(username, password, mobile);


        }
        else {
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
        System.out.println("3. Exit");
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

        // Pay Bill
        if (bill != null) {
            // Create a user
            User user = new User("", "", "", 00.0, UserType.BANK_USER);
            user.setBalance(1000); // Set initial balance
            PayBill.payBill(user, bill);
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


