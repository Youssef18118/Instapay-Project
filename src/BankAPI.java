class BankAPI implements API {
    @Override
    public boolean verify(User user) {
        System.out.println("Inside BankAPI " + user.getUserType());
        if (user.getUserType() == UserType.BANK_USER) {
            BankUser bankUser = (BankUser) user;
            return BankDatabase.verifyBankUser(bankUser.getCardNo());
        }
        return false;
    }

    @Override
    public double getBalance(User user) {
        if (user.getUserType() == UserType.BANK_USER) {
            BankUser bankUser = (BankUser) user;
            return BankDatabase.getBankUserBalance(bankUser.getCardNo());
        }
        return 0.0;
    }

    @Override
    public void updateBalance(User user, double newBalance) {
        if (user.getUserType() == UserType.BANK_USER) {
            BankUser bankUser = (BankUser) user;
            BankDatabase.updateBankUserBalance(bankUser.getCardNo(), newBalance);
        }
    }

    @Override
    public UserType getSupportedUserType() {
        return UserType.BANK_USER;
    }
}