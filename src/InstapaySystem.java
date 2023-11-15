import java.util.Scanner;

class InstapaySystem {
    private static Scanner scanner = new Scanner(System.in);
    public DB db;

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
                    MakeSignup();
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

    private boolean MakeSignup()
    {
        // to be implemented
        return true;

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
}
