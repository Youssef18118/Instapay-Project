import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Objects;
import java.util.Date;

abstract class Bill {
    private int billID;
    private int electronicPaymentNo;
    private double billExpenses;
    private String companyName;
    BillAPI billAPI;

    public Bill(int billID, int electronicPaymentNo, double billExpenses, String companyName) {
        this.billID = billID;
        this.electronicPaymentNo = electronicPaymentNo;
        this.billExpenses = billExpenses;
        this.companyName = companyName;
    }

    public abstract String displayBill();

    // Getters and setters for the attributes

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getElectronicPaymentNo() {
        return electronicPaymentNo;
    }

    public void setElectronicPaymentNo(int electronicPaymentNo) {
        this.electronicPaymentNo = electronicPaymentNo;
    }

    public double getBillExpenses() {
        return billExpenses;
    }

    public void setBillExpenses(double billExpenses) {
        this.billExpenses = billExpenses;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BillAPI getBillAPI() {
        return this.billAPI;
    }

    public void setBillAPI(BillAPI billAPI) {
        this.billAPI = billAPI;
    }
}

class GasBill extends Bill {
    private int readNo;

    public GasBill(int billID, int electronicPaymentNo, double billExpenses, String companyName, int readNo) {
        super(billID, electronicPaymentNo, billExpenses, companyName);
        this.readNo = readNo;
    }

    @Override
    public String displayBill() {
        return "Gas Bill: " +
                "BillID=" + getBillID() +
                ", ElectronicPaymentNo=" + getElectronicPaymentNo() +
                ", BillExpenses=" + getBillExpenses() +
                ", CompanyName=" + getCompanyName() +
                ", ReadNo=" + readNo;
    }

    // Getter and setter for ReadNo

    public int getReadNo() {
        return readNo;
    }

    public void setReadNo(int readNo) {
        this.readNo = readNo;
    }
}

class ElectricityBill extends Bill {
    private int readNo;

    public ElectricityBill(int billID, int electronicPaymentNo, double billExpenses, String companyName,
            int readNo) {
        super(billID, electronicPaymentNo, billExpenses, companyName);
        this.readNo = readNo;
    }

    @Override
    public String displayBill() {
        return "Electricity Bill: " +
                "BillID=" + getBillID() +
                ", ElectronicPaymentNo=" + getElectronicPaymentNo() +
                ", BillExpenses=" + getBillExpenses() +
                ", CompanyName=" + getCompanyName() +
                ", readNo=" + readNo;
    }

    // Getter and setter for readNo

    public int getreadNo() {
        return readNo;
    }

    public void setreadNo(int readNo) {
        this.readNo = readNo;
    }
}

class WaterBill extends Bill {
    private int consumption;

    public WaterBill(int billID, int electronicPaymentNo, double billExpenses, String companyName, int consumption) {
        super(billID, electronicPaymentNo, billExpenses, companyName);
        this.consumption = consumption;
    }

    @Override
    public String displayBill() {
        return "Water Bill: " +
                "BillID=" + getBillID() +
                ", ElectronicPaymentNo=" + getElectronicPaymentNo() +
                ", BillExpenses=" + getBillExpenses() +
                ", CompanyName=" + getCompanyName() +
                ", Consumption=" + consumption;
    }

    // Getter and setter for Consumption

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }
}

interface Company {
    // This interface represents a generic company
    double getBillExpenses(int electroNo);
}

class GasCompany implements Company {
    @Override
    public double getBillExpenses(int electroNo) {
        // Dummy logic for GasCompany
        // In a real-world scenario, you would fetch actual data from the Gas Company
        // API
        // For demonstration purposes, we use dummy values
        return 400;
    }
}

class ElectricityCompany implements Company {
    @Override
    public double getBillExpenses(int electroNo) {
        // Dummy logic for ElectricityCompany
        // In a real-world scenario, you would fetch actual data from the Electricity
        // Company API
        // For demonstration purposes, we use dummy values
        return 500;
    }
}

class WaterCompany implements Company {
    @Override
    public double getBillExpenses(int electroNo) {
        // Dummy logic for WaterCompany
        // In a real-world scenario, you would fetch actual data from the Water Company
        // API
        // For demonstration purposes, we use dummy values
        return 600;
    }
}

interface BillAPI {
    double getBillExpenses(int electroNo);
}

class GasBillAPI implements BillAPI {
    private final Company gasCompany;

    public GasBillAPI(Company gasCompany) {
        this.gasCompany = gasCompany;
    }

    @Override
    public double getBillExpenses(int electroNo) {
        // GasBillAPI contacts the GasCompany to get bill expenses
        return gasCompany.getBillExpenses(electroNo);
    }
}

class ElectricityBillAPI implements BillAPI {
    private final Company electricityCompany;

    public ElectricityBillAPI(Company electricityCompany) {
        this.electricityCompany = electricityCompany;
    }

    @Override
    public double getBillExpenses(int electroNo) {
        // ElectricityBillAPI contacts the ElectricityCompany to get bill expenses
        return electricityCompany.getBillExpenses(electroNo);
    }
}

class WaterBillAPI implements BillAPI {
    private final Company waterCompany;

    public WaterBillAPI(Company waterCompany) {
        this.waterCompany = waterCompany;
    }

    @Override
    public double getBillExpenses(int electroNo) {
        // WaterBillAPI contacts the WaterCompany to get bill expenses
        return waterCompany.getBillExpenses(electroNo);
    }
}

enum BillType {
    GAS,
    ELECTRICITY,
    WATER
}

class BillFactory {
    private static List<Bill> createdBills = new ArrayList<>();
    private static int billIDCounter = 1;

    private static synchronized int getNextBillID() {
        return billIDCounter++;
    }

    public static Bill createBill(BillType billType, String companyName, int electronicPaymentNo) {
        // Check if a bill with the given electronic payment number already exists
        for (Bill existingBill : createdBills) {
            if (existingBill.getElectronicPaymentNo() == electronicPaymentNo) {
                System.out.println("Bill with Electronic Payment No. " + electronicPaymentNo + " already exists. \n");
                return null;
            }
        }

        Bill bill;

        switch (billType) {
            case GAS:
                bill = new GasBill(getNextBillID(), electronicPaymentNo, 0, companyName, 456);
                bill.setBillAPI(new GasBillAPI(new GasCompany())); // Set GasBillAPI
                ((GasBill) bill).setReadNo(electronicPaymentNo);
                double gasBillExpenses = bill.getBillAPI().getBillExpenses(electronicPaymentNo);
                bill.setBillExpenses(gasBillExpenses);
                break;
            case ELECTRICITY:
                bill = new ElectricityBill(getNextBillID(), electronicPaymentNo, 0, companyName, 789);
                bill.setBillAPI(new ElectricityBillAPI(new ElectricityCompany())); // Set ElectricityBillAPI
                ((ElectricityBill) bill).setreadNo(electronicPaymentNo);
                double electricityBillExpenses = bill.getBillAPI().getBillExpenses(electronicPaymentNo);
                bill.setBillExpenses(electricityBillExpenses);
                break;
            case WATER:
                bill = new WaterBill(getNextBillID(), electronicPaymentNo, 0, companyName, 123);
                bill.setBillAPI(new WaterBillAPI(new WaterCompany())); // Set WaterBillAPI
                ((WaterBill) bill).setConsumption(electronicPaymentNo);
                double waterBillExpenses = bill.getBillAPI().getBillExpenses(electronicPaymentNo);
                bill.setBillExpenses(waterBillExpenses);
                break;
            default:
                System.out.println("Invalid bill type");
                return null;
        }

        // Add the created bill to the list
        createdBills.add(bill);
        return bill;
    }
}

class PayBill {
    public static boolean payBill(User user, Bill bill) {
        // Call the getBillExpenses method from BillAPI
        double billExpenses = bill.getBillAPI().getBillExpenses(bill.getElectronicPaymentNo());
        bill.setBillExpenses(billExpenses);

        double balance = user.getBalance();
        if (balance < billExpenses) {
            System.out.println("Insufficient Balance");
            return false;
        }
        user.setBalance(balance - billExpenses);

        // Your payment logic here
        System.out.println("Bill paid successfully \n");
        return true;
    }
}

enum UserType {
    BANK_USER,
    WALLET_USER,
    INSTAPAY_USER
}

// Transfer Part
class User {
    private String userName;
    private String password;
    private String mobileNo;
    private double balance;
    private UserType userType;

    public User(String userName, String password, String mobileNo, double balance, UserType userType) {
        this.userName = userName;
        this.password = password;
        this.mobileNo = mobileNo;
        this.balance = balance;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("Invalid balance. Balance cannot be negative.");
        }
    }
}

class BankUser extends User {
    private String bankUserName;
    private String cardNo;
    private int cvv;
    private Date expirationDate;

    public BankUser(String userName, String password, String mobileNo, double balance,
            String bankUserName, String cardNo, int cvv, Date expirationDate) {
        super(userName, password, mobileNo, balance, UserType.BANK_USER);
        this.bankUserName = bankUserName;
        this.cardNo = cardNo;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}

class WalletUser extends User {
    public WalletUser(String userName, String password, String mobileNo, double balance) {
        super(userName, password, mobileNo, balance, UserType.WALLET_USER);
        // Additional initialization for WalletUser if needed
    }
}

// TransferStrategy interface
interface TransferStrategy {
    boolean transfer(double amount, User sender, User receiver);
}

class TransferToBankStrategy implements TransferStrategy {
    private final API bankAPI; // Associate with BankAPI

    public TransferToBankStrategy(API bankAPI) {
        this.bankAPI = bankAPI;
    }

    @Override
    public boolean transfer(double amount, User sender, User receiver) {
        if (bankAPI.verify(sender)) {
            double senderBalance = bankAPI.getBalance(sender);
            if (senderBalance >= amount && sender.getUserType() == UserType.BANK_USER) {
                // Perform the actual transfer logic
                bankAPI.updateBalance(sender, senderBalance - amount);
                bankAPI.updateBalance(receiver, bankAPI.getBalance(receiver) + amount);
                sender.setBalance(senderBalance - amount);
                System.out.println("Transferring $" + amount + " to BankUser");
                return true;
            } else {
                System.out.println("Insufficient balance for the transfer or invalid user type.");
            }
        } else {
            System.out.println("Sender verification failed.");
        }
        return false;
    }
}

class TransferToWalletStrategy implements TransferStrategy {
    private final API walletAPI; // Associate with WalletAPI

    public TransferToWalletStrategy(API walletAPI) {
        this.walletAPI = walletAPI;
    }

    @Override
    public boolean transfer(double amount, User sender, User receiver) {
        if (walletAPI.verify(sender) && sender.getUserType() == UserType.WALLET_USER) {
            double senderBalance = walletAPI.getBalance(sender);
            if (senderBalance >= amount) {
                // Perform the actual transfer logic
                walletAPI.updateBalance(sender, senderBalance - amount);
                walletAPI.updateBalance(receiver, walletAPI.getBalance(receiver) + amount);
                sender.setBalance(senderBalance - amount);
                System.out.println("Transferring $" + amount + " to WalletUser");
                return true;
            } else {
                System.out.println("Insufficient balance for the transfer.");
            }
        } else {
            System.out.println("Sender verification failed or invalid user type.");
        }
        return false;
    }
}

class TransferToInstapayStrategy implements TransferStrategy {
    private final DB db;

    // Constructor to initialize with a DB instance
    public TransferToInstapayStrategy(DB db) {
        this.db = db;
    }

    @Override
    public boolean transfer(double amount, User sender, User receiver) {
        // Check if the receiver is a valid Instapay account in the DB
        if (receiver instanceof User && db.findUserName(receiver.getUserName())) {
            // Check if the sender is valid and has sufficient balance
            if (db.findUserName(sender.getUserName()) && (db.findUser(sender.getUserName()).getBalance() >= amount)) {
                // Perform the actual transfer logic
                db.updateBalance(sender, db.getBalance(sender) - amount);
                db.updateBalance(receiver, db.getBalance(receiver) + amount);
                sender.setBalance(sender.getBalance() - amount);
                System.out.println("Transferring $" + amount + " to InstapayUser");
                return true;
            } else {
                System.out.println("Insufficient balance for the transfer or sender verification failed.");
            }
        } else {
            System.out.println("Invalid receiver type for Instapay transfer.");
        }
        return false;
    }
}

class Transfer {
    private final TransferStrategy transferStrategy;

    public Transfer(TransferStrategy transferStrategy) {
        this.transferStrategy = transferStrategy;
    }

    public boolean performTransfer(double amount, User sender, User receiver) {
        return transferStrategy.transfer(amount, sender, receiver);
    }
}

interface API {
    boolean verify(User user);

    double getBalance(User user);

    void updateBalance(User user, double newBalance);

    UserType getSupportedUserType();
}

class BankAPI implements API {
    @Override
    public boolean verify(User user) {
        if (user.getUserType() == UserType.BANK_USER) {
            BankUser bankUser = (BankUser) user;
            return BankDatabase.verifyBankUser(bankUser.getCardNo());
        }
        return false;
    }

    @Override
    public double getBalance(User user) {
        if (user.getUserType() == UserType.BANK_USER) {
            BankUser bankUser = (BankUser) user;
            return BankDatabase.getBankUserBalance(bankUser.getCardNo());
        }
        return 0.0;
    }

    @Override
    public void updateBalance(User user, double newBalance) {
        if (user.getUserType() == UserType.BANK_USER) {
            BankUser bankUser = (BankUser) user;
            BankDatabase.updateBankUserBalance(bankUser.getCardNo(), newBalance);
        }
    }

    @Override
    public UserType getSupportedUserType() {
        return UserType.BANK_USER;
    }
}

class WalletAPI implements API {
    @Override
    public boolean verify(User user) {
        if (user.getUserType() == UserType.WALLET_USER) {
            WalletUser walletUser = (WalletUser) user;
            return WalletCompanyDatabase.verifyWalletUser(walletUser.getMobileNo());
        }
        return false;
    }

    @Override
    public double getBalance(User user) {
        if (user.getUserType() == UserType.WALLET_USER) {
            WalletUser walletUser = (WalletUser) user;
            return WalletCompanyDatabase.getWalletUserBalance(walletUser.getMobileNo());
        }
        return 0.0;
    }

    @Override
    public void updateBalance(User user, double newBalance) {
        if (user.getUserType() == UserType.WALLET_USER) {
            WalletUser walletUser = (WalletUser) user;
            WalletCompanyDatabase.updateWalletUserBalance(walletUser.getMobileNo(), newBalance);
        }
    }

    @Override
    public UserType getSupportedUserType() {
        return UserType.WALLET_USER;
    }
}

class Banks extends WalletAPI {
    // Additional functionality specific to Banks
}

class TeleCompanies extends WalletAPI {
    // Additional functionality specific to TeleCompanies
}

class ElectroPaymentCompanies extends WalletAPI {
    // Additional functionality specific to ElectroPaymentCompanies
}

// Dummy database for banks
class BankDatabase {
    private static Map<String, Double> balances = new HashMap<>();

    static {
        // Dummy data for bank users and their balances
        balances.put("CardNo1", 1000.0);
        balances.put("CardNo2", 1500.0);
    }

    static boolean verifyBankUser(String cardNo) {
        // Simulate verification logic
        return balances.containsKey(cardNo);
    }

    static double getBankUserBalance(String cardNo) {
        // Simulate getting the balance from the database
        return balances.getOrDefault(cardNo, 0.0);
    }

    static void updateBankUserBalance(String cardNo, double newBalance) {
        // Simulate updating the balance in the database
        balances.put(cardNo, newBalance);
    }
}

// Dummy database for wallet companies
class WalletCompanyDatabase {
    private static Map<String, Double> balances = new HashMap<>();

    static {
        // Dummy data for wallet users and their balances
        balances.put("01151224195", 500.0);
        balances.put("01241602455", 800.0);
    }

    static boolean verifyWalletUser(String mobileNo) {
        return balances.containsKey(mobileNo);
    }

    static double getWalletUserBalance(String mobileNo) {
        // Simulate getting the balance from the database
        return balances.getOrDefault(mobileNo, 0.0);
    }

    static void updateWalletUserBalance(String mobileNo, double newBalance) {
        // Simulate updating the balance in the database
        balances.put(mobileNo, newBalance);
    }
}

interface DB {
    void addUser(User user);

    void removeUser(User user);

    boolean findUserName(String userName);

    User findUser(String userName);

    boolean verifyUser(User user); // Add this method for user verification

    double getBalance(User user); // Add this method to get user balance

    void updateBalance(User user, double newBalance); // Add this method to update user balance
}

class DbList implements DB {
    private List<User> users;

    public DbList() {
        this.users = new ArrayList<>();
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public boolean findUserName(String userName) {
        for (User user : users) {
            if (Objects.equals(userName, user.getUserName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User findUser(String userName) {
        for (User user : users) {
            if (Objects.equals(userName, user.getUserName())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean verifyUser(User user) {
        return users.contains(user);
    }

    @Override
    public double getBalance(User user) {
        // You need to implement this method based on your logic
        // For now, it returns 0.0
        return 0.0;
    }

    @Override
    public void updateBalance(User user, double newBalance) {
        // You need to implement this method based on your logic
        // For now, it does nothing
    }
}

class InstapaySystem {
    Scanner scanner;
    private API api;
    private DB db;

    void seDataBase(DB db) {
        this.db = db;
    }

    public InstapaySystem(API api) {
        this.api = api;
        this.db = new DbList(); // Initialize the DB instance
    }

    public void displayScreen() {
        int choice;
        do {
            displayMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    payBillOption();
                    break;
                case 2:
                    transferMoneyOption();
                    break;
                case 3:
                    System.out.println("Exiting Instapay System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 2);
    }

    private void displayMenu() {
        System.out.println("Instapay System Menu:");
        System.out.println("1. Pay Bill");
        System.out.println("2. Transfer Money");
        System.out.println("3. Exit");
    }

    private int getUserChoice() {
        int choice;
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return choice;
    }

    private void payBillOption() {
        // Existing payBillOption logic remains unchanged
    }

    private void transferMoneyOption() {
        System.out.println("Select receiver type: ");
        System.out.println("1. Instapay Account");
        System.out.println("2. Bank");
        System.out.println("3. Wallet");

        int receiverType = scanner.nextInt(); // Use the existing scanner object

        System.out.println("Enter sender username: ");
        String senderUsername = scanner.next();
        System.out.println("Enter sender password: ");
        String senderPassword = scanner.next();

        User sender = createUser(senderUsername, senderPassword);

        double senderBalance;

        if (sender.getUserType() == UserType.INSTAPAY_USER) {
            // For InstapayUser, check in the DB
            if (db.verifyUser(sender)) {
                senderBalance = sender.getBalance();
            } else {
                System.out.println("Sender verification failed. Returning to the menu.");
                return;
            }
        } else if (sender.getUserType() == UserType.BANK_USER || sender.getUserType() == UserType.WALLET_USER) {
            // For BankUser or WalletUser, use the API verification
            if (!api.verify(sender)) {
                System.out.println("Sender verification failed. Returning to the menu.");
                return;
            }
            senderBalance = sender.getBalance();
        } else {
            System.out.println("Invalid sender type. Returning to the menu.");
            return;
        }

        System.out.println("Enter transfer amount: ");
        double amount = scanner.nextDouble();

        if (senderBalance < amount) {
            System.out.println("Insufficient balance for the transfer. Returning to the menu.");
            return;
        }

        TransferStrategy transferStrategy = createTransferStrategy(receiverType, sender);

        if (transferStrategy != null) {
            Transfer transfer = new Transfer(transferStrategy);

            // For Wallet transfer, create the receiver here
            if (receiverType == 3) {
                System.out.println("Enter receiver Mobile NO for Wallet transfer: ");
                String receiverMobileNo = scanner.next();
                WalletUser receiver = createWalletUser("", "", receiverMobileNo);
                transfer.performTransfer(amount, sender, receiver);
            } else if (receiverType == 2) {
                System.out.println("Enter receiver card number for Bank transfer: ");
                String receiverCardNo = scanner.next();
                BankUser receiver = createBankUser("", "", receiverCardNo);
                transfer.performTransfer(amount, sender, receiver);
            } else if (receiverType == 1) {
                System.out.println("Enter receiver username for Instapay transfer: ");
                String receiverUsername = scanner.next();
                User receiver = createUser(receiverUsername, "");
                transfer.performTransfer(amount, sender, receiver);
            } else {
                // For Instapay and Bank transfers, receiver is not needed in this context
                transfer.performTransfer(amount, sender, null);
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
        // Assuming you have a method to create a user object based on input
        return new User(username, password, "", 0.0, UserType.INSTAPAY_USER);
    }

    private BankUser createBankUser(String username, String password, String cardNo) {
        // Assuming you have a method to create a user object based on input
        return new BankUser(username, password, "", 0.0, "", cardNo, 000, null);
    }

    private WalletUser createWalletUser(String username, String password, String MobileNo) {
        // Assuming you have a method to create a user object based on input
        return new WalletUser(username, password, MobileNo, 0.0);
    }

}

public class SystemMain {
    public static void main(String[] args) {
        API bankApi = new BankAPI();
        InstapaySystem instapaySystem = new InstapaySystem(bankApi);

        User user = new User("ahemd", "12345", "01125437965", 200, UserType.INSTAPAY_USER);
        User user2 = new User("mohamed", "12345", "01125437965", 200, UserType.BANK_USER);
        User user3 = new User("Ali", "12345", "01125437965", 200, UserType.WALLET_USER);
        User user4 = new User("omar", "12345", "01125437965", 200, UserType.INSTAPAY_USER);

        DbList db = new DbList();
        db.addUser(user);
        db.addUser(user2);
        db.addUser(user3);
        db.addUser(user4);

        instapaySystem.seDataBase(db);

        // Start the InstapaySystem
        instapaySystem.displayScreen();

        // Close the scanner when done
    }
}
