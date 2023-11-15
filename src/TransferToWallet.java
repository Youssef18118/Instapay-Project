class TransferToWallet implements WalletUser {
    @Override
    public boolean transfer(double amount, WalletUser receiver) {
        // Implementation for transferring money from wallet user to wallet user
        System.out.println("Transferring $" + amount + " from WalletUser to WalletUser");
        return true; // Replace with actual implementation
    }
}
