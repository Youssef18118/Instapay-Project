class TransferToWalletStrategy implements TransferStrategy {
    private final API walletAPI; // Associate with WalletAPI

    public TransferToWalletStrategy(API walletAPI) {
        this.walletAPI = walletAPI;
    }

    @Override
    public boolean transfer(double amount, double senderBalance, User sender, User receiver) {
        if (walletAPI.verify(receiver)) {
            // double senderBalance = walletAPI.getBalance(sender);
            // System.out.println(senderBalance);

            // Perform the actual transfer logic
            walletAPI.updateBalance(sender, senderBalance - amount);
            walletAPI.updateBalance(receiver, walletAPI.getBalance(receiver) + amount);
            sender.setBalance(senderBalance - amount);
            System.out.println("Transferring $" + amount + " to WalletUser");
            return true;

        } else {
            System.out.println("receiver verification failed or invalid user type.");
        }
        return false;
    }
}
