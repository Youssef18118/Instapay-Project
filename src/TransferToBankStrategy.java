class TransferToBankStrategy implements TransferStrategy {
    private final API bankAPI; // Associate with BankAPI

    public TransferToBankStrategy(API bankAPI) {
        this.bankAPI = bankAPI;
    }

    @Override
    public boolean transfer(double amount, double senderBalance, User sender, User receiver) {
        if (bankAPI.verify(receiver)) {
            if (sender.getUserType() == UserType.BANK_USER) {
                // Perform the actual transfer logic
                bankAPI.updateBalance(sender, senderBalance - amount);
                bankAPI.updateBalance(receiver, bankAPI.getBalance(receiver) + amount);
                sender.setBalance(senderBalance - amount);
                System.out.println("Transferring $" + amount + " to BankUser");
                return true;
            } else {
                System.out.println("You must be a BankUser to transfer to BankUser");
            }
        } else {
            System.out.println("Receiver verification failed.");
        }
        return false;
    }
}