interface API {
    boolean verify(User user);

    double getBalance(User user);

    void updateBalance(User user, double newBalance);

    UserType getSupportedUserType();
}