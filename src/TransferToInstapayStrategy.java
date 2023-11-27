class TransferToInstapayStrategy implements TransferStrategy {
    private final DB db;

    // Constructor to initialize with a DB instance
    public TransferToInstapayStrategy(DB db) {
        this.db = db;
    }

    @Override
    public boolean transfer(double amount, User sender, User receiver) {

        // System.out.println("Receiver type: " + receiver.getUserType());
        // System.out.println("Receiver username exists: " +
        // db.findUserName(receiver.getUserName()));
        // userName = userName.trim().toLowerCase(); before passing to findUserName but
        // it returns false

        if ((receiver.getUserType() == UserType.INSTAPAY_USER)
                && db.findUserName(receiver.getUserName())) {
            if (db.findUserName(sender.getUserName()) && (db.findUser(sender).getBalance() >= amount)) { // small
                                                                                                         // problem with
                                                                                                         // findUser

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
