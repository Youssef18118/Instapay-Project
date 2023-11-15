import java.util.Scanner;

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
