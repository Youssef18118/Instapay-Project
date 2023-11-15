class BankAPI implements API {
    @Override
    public boolean verify(User user) {
        // Bank-specific verification logic
        return true;
    }

    @Override
    public double getBalance() {
        // Bank-specific balance retrieval logic
        return 1000.0; // Example balance
    }
}
