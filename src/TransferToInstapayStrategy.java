class TransferToInstapayStrategy implements TransferStrategy {
    private final DB db;

    // Constructor to initialize with a DB instance
    public TransferToInstapayStrategy(DB db) {
        this.db = db;
    }

    @Override
    public boolean transfer(double amount, double senderBalance, User sender, User receiver) {

        // Perform the actual transfer logic
        db.updateBalance(sender, senderBalance - amount);
        sender.setBalance(sender.getBalance() - amount);
        // System.out.println(db.getBalance(receiver));
        // receiver.setBalance(db.getBalance(receiver) + amount);
        System.out.println("Transferring $" + amount + " to InstapayUser");
        return true;
    }
}
