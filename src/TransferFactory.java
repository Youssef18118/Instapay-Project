public class TransferFactory {

    public static TransferStrategy createTransferStrategy(int receiverType, User sender) {
        TransferStrategy transferStrategy = null;

        switch (receiverType) {
            case 1:
                transferStrategy = new TransferToInstapayStrategy(new DbList()); // You might change this
                break;
            case 2:
                if (sender.getUserType() == UserType.BANK_USER) {
                    transferStrategy = new TransferToBankStrategy(new BankAPI());
                } else {
                    System.out.println("Invalid sender type for Bank transfer. Returning to the menu.");
                }
                break;
            case 3:
                transferStrategy = new TransferToWalletStrategy(new WalletAPI());
                break;
            default:
                System.out.println("Invalid receiver type.");
                break;
        }

        return transferStrategy;
    }
}
