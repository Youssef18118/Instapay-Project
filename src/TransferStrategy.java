interface TransferStrategy {
    boolean transfer(double amount, double senderBalance, User sender, User receiver);
}
