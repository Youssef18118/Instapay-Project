class WalletUser extends User {
    public WalletUser(String userName, String password, String mobileNo, double balance) {
        super(userName, password, mobileNo, balance, UserType.WALLET_USER);
        // Additional initialization for WalletUser if needed
    }
    public WalletUser()
    {

    }
}
