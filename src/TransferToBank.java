class TransferToBank implements BankUserTransfer {
    @Override
    public boolean transfer(double amount, BankUser receiver) {
        // Implementation for transferring money from bank user to bank user
        System.out.println("Transferring $" + amount + " from BankUser to BankUser");
        return true; // Replace with actual implementation
    }
}
