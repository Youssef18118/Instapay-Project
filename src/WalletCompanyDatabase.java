import java.util.HashMap;
import java.util.Map;

class WalletCompanyDatabase {
    private static Map<String, Double> balances = new HashMap<>();

    static {
        // Dummy data for wallet users and their balances
        balances.put("01151224195", 500.0);
        balances.put("01241602455", 800.0);
    }

    static boolean verifyWalletUser(String mobileNo) {
        // return balances.containsKey(mobileNo);
        return true;
    }

    static double getWalletUserBalance(String mobileNo) {
        // Simulate getting the balance from the database
        return 500.0;
    }

    static void updateWalletUserBalance(String mobileNo, double newBalance) {
        // Simulate updating the balance in the database
        balances.put(mobileNo, newBalance);
    }
}
