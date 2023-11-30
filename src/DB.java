interface DB {
    void addUser(User user);

    void addBankUser(BankUser user);

    void removeUser(User user);

    void removeBankUser(BankUser user);

    boolean findUserName(String userName);

    User findUser(User usr);

    boolean findUserNameANDPassword(String userName, String password);

    boolean verifyUser(User user); // Add this method for user verification

    double getBalance(User user); // Add this method to get user balance

    void updateBalance(User user, double newBalance); // Add this method to update user balance

    public boolean findUserType(UserType type);

    public UserType findUserType(String userName);

    public User getUser(String userName, String password);

    public User findUserByphone(String phoneNum);

    public BankUser findCardNo(String Cardno);
}
