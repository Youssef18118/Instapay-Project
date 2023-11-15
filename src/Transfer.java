class Transfer {
    private final TransferStrategy transferStrategy;

    public Transfer(TransferStrategy transferStrategy) {
        this.transferStrategy = transferStrategy;
    }

    public boolean performTransfer(double amount, User sender, User receiver) {
        return transferStrategy.transfer(amount, sender, receiver);
    }
}