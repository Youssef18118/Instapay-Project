import java.util.HashMap;
import java.util.Map;

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
