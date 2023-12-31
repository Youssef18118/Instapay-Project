public abstract class User {
    private String userName;
    private String password;
    private String mobileNo;
    private double balance;
    private UserType userType;

    public User() {

    }

    public User(String userName, String password, String mobileNo, double balance, UserType userType) {
        this.userName = userName;
        this.password = password;
        this.mobileNo = mobileNo;
        this.balance = balance;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("Invalid balance. Balance cannot be negative.");
        }
    }

    public User CopyUser(User user) {
        if (this.getUserType() == UserType.BANK_USER) {
            user = new BankUser();
        } else if (this.getUserType() == UserType.WALLET_USER) {
            user = new WalletUser();
        }

        UserType type = this.getUserType();

        user.setUserName(this.getUserName());
        user.setPassword(this.getPassword());
        user.setBalance(this.getBalance());
        user.setUserType(type);
        user.setMobileNo(this.getMobileNo());

        return user;
    }

}
