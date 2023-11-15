interface DB {
    void addUser(User user);

    void removeUser(User user);

    boolean findUserName(String userName);

    User findUser(String userName);

    boolean verifyUser(User user); // Add this method for user verification

    double getBalance(User user); // Add this method to get user balance

    void updateBalance(User user, double newBalance); // Add this method to update user balance
}
