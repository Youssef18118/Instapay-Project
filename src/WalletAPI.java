class WalletAPI implements API {
        @Override
        public boolean verify(User user) {
                if (user.getUserType() == UserType.WALLET_USER) {
                        WalletUser walletUser = (WalletUser) user;
                        return WalletCompanyDatabase.verifyWalletUser(walletUser.getMobileNo());
                }
                return false;
        }

        @Override
        public double getBalance(User user) {
                if (user.getUserType() == UserType.WALLET_USER) {
                        WalletUser walletUser = (WalletUser) user;
                        return WalletCompanyDatabase.getWalletUserBalance(walletUser.getMobileNo());
                }
                return 0.0;
        }

        @Override
        public void updateBalance(User user, double newBalance) {
                if (user.getUserType() == UserType.WALLET_USER) {
                        WalletUser walletUser = (WalletUser) user;
                        WalletCompanyDatabase.updateWalletUserBalance(walletUser.getMobileNo(), newBalance);
                }
        }

        @Override
        public UserType getSupportedUserType() {
                return UserType.WALLET_USER;
        }
}
