class WalletAPI implements API {
        @Override
        public boolean verify(User user) {
                // System.out.println("Inside WalletAPI " + user.getUserType());
                if (user.getUserType() == UserType.WALLET_USER) {
                        // WalletUser walletUser = (WalletUser) user;
                        return WalletCompanyDatabase.verifyWalletUser(user.getMobileNo());
                }
                return false;
        }

        @Override
        public double getBalance(User user) {
                if (user.getUserType() == UserType.WALLET_USER) {
                        // WalletUser walletUser = (WalletUser) user;
                        return WalletCompanyDatabase.getWalletUserBalance(user.getMobileNo());
                }
                return 0.0;
        }

        @Override
        public void updateBalance(User user, double newBalance) {
                if (user.getUserType() == UserType.WALLET_USER) {
                        // WalletUser walletUser = (WalletUser) user;
                        WalletCompanyDatabase.updateWalletUserBalance(user.getMobileNo(), newBalance);
                }
        }

        @Override
        public UserType getSupportedUserType() {
                return UserType.WALLET_USER;
        }
}
