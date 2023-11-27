import java.util.HashMap;
import java.util.Map;

// Dummy database for banks
class BankDatabase {
    private static Map<String, Double> balances = new HashMap<>();

    static {
        // Dummy data for bank users and their balances
        balances.put("12345", 1000.0);
        balances.put("123456", 1500.0);
    }

    static boolean verifyBankUser(String cardNo) {
        // Simulate verification logic
        // return balances.containsKey(cardNo);
        return true;
    }

    static double getBankUserBalance(String cardNo) {
        // Simulate getting the balance from the database
        return 500.0;
    }

    static void updateBankUserBalance(String cardNo, double newBalance) {
        // Simulate updating the balance in the database
        balances.put(cardNo, newBalance);
    }
}
