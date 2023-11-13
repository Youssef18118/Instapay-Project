import java.util.Scanner;

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

class User {
    private double Balance;

    public double getBalance() {
        return this.Balance;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }
}

enum BillType {
    GAS,
    ELECTRICITY,
    WATER
}

class BillFactory {
    private static Bill bill;
    private static int billIDCounter = 1;

    private static synchronized int getNextBillID() {
        return billIDCounter++;
    }

    public static Bill createBill(BillType billType, String companyName, int electronicPaymentNo) {
        switch (billType) {
            case GAS:
                bill = new GasBill(getNextBillID(), electronicPaymentNo, 0, companyName, 456);
                ((GasBill) bill).setReadNo(electronicPaymentNo);
                bill.setBillAPI(new GasBillAPI(new GasCompany())); // Set GasBillAPI
                break;
            case ELECTRICITY:
                bill = new ElectricityBill(getNextBillID(), electronicPaymentNo, 0, companyName, 789);
                ((ElectricityBill) bill).setreadNo(electronicPaymentNo);
                bill.setBillAPI(new ElectricityBillAPI(new ElectricityCompany())); // Set ElectricityBillAPI
                break;
            case WATER:
                bill = new WaterBill(getNextBillID(), electronicPaymentNo, 0, companyName, 123);
                ((WaterBill) bill).setConsumption(electronicPaymentNo);
                bill.setBillAPI(new WaterBillAPI(new WaterCompany())); // Set WaterBillAPI
                break;
            default:
                System.out.println("Invalid bill type");
                return null;
        }

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

class InstapaySystem {
    private static Scanner scanner = new Scanner(System.in);

    public void DisplayScreen() {
        int choice;
        do {
            displayMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    payBillOption();
                    break;
                case 2:
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
        System.out.println("2. Exit");
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
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

        System.out.println("Enter the electronic payment number:");
        int electronicPaymentNo = scanner.nextInt();

        // Create Bill
        Bill bill = BillFactory.createBill(billType, companyName, electronicPaymentNo);

        // Pay Bill
        if (bill != null) {
            // Create a user
            User user = new User();
            user.setBalance(1000); // Set initial balance

            PayBill.payBill(user, bill);
        }
    }
}

public class BillMain {
    public static void main(String[] args) {
        InstapaySystem System = new InstapaySystem();
        System.DisplayScreen();
    }
}
