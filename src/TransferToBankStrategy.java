class TransferToBankStrategy implements TransferStrategy {
    private final API bankAPI; // Associate with BankAPI

    public TransferToBankStrategy(API bankAPI) {
        this.bankAPI = bankAPI;
    }

    @Override
    public boolean transfer(double amount, User sender, User receiver) {
        if (bankAPI.verify(receiver)) {
            double senderBalance = bankAPI.getBalance(sender);
            if (senderBalance >= amount && sender.getUserType() == UserType.BANK_USER) {
                // Perform the actual transfer logic
                bankAPI.updateBalance(sender, senderBalance - amount);
                bankAPI.updateBalance(receiver, bankAPI.getBalance(receiver) + amount);
                sender.setBalance(senderBalance - amount);
                System.out.println("Transferring $" + amount + " to BankUser");
                return true;
            } else {
                System.out.println("Insufficient balance for the transfer or invalid user type.");
            }
        } else {
            System.out.println("Receiver verification failed.");
        }
        return false;
    }
}