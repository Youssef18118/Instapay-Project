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
