import java.util.Date;

class SignUp {
    private User user;
    private BankUser bUser;
    private WalletUser wUser = new WalletUser();
    private System system;
    private IOTP otpoperation;
    private API CheckApI;

    public void setSystem(System system) {
        this.system = system;
    }

    public System getSystem() {
        return system;
    }

    public void setCheckApI(API checkApI) {
        CheckApI = checkApI;
    }

    public API getCheckApI() {
        return CheckApI;
    }

    public void setOtpoperation(IOTP otpoperation) {
        this.otpoperation = otpoperation;
    }

    public IOTP getOtpoperation() {
        return otpoperation;
    }

    public User getInfoUser(String username, String password, String mobile, UserType type)
    {
        user.setUserName(username);
        user.setPassword(password);
        user.setMobileNo(mobile);
        return user;
    }
    public BankUser getInfoUserBank(String username, String password, String mobile, int cvv, String cardNum, Date expDate)
    {
        bUser.setUserName(username);
        bUser.setPassword(password);
        bUser.setMobileNo(mobile);
        bUser.setCvv(cvv);
        bUser.setCardNo(cardNum);
        bUser.setExpirationDate(expDate);
        return bUser;

    }
    public WalletUser getInfoWallet(String username, String password, String mobile)
    {
        wUser.setUserName(username);
        wUser.setPassword(password);
        wUser.setMobileNo(mobile);
        return wUser;
    }
    public void makeSignup(User user) {
        // Perform API check
        API CheckApI = new API() {
            @Override
            public boolean verify(User user) {
                return false;
            }

            @Override
            public double getBalance(User user) {
                return 0;
            }

            @Override
            public void updateBalance(User user, double newBalance) {

            }

            @Override
            public UserType getSupportedUserType() {
                return null;
            }

            public double getBalance() {
                return 0;
            }
        };
        boolean isAPICheckPassed = CheckApI.verify(user);

        // Generate OTP
        IOTP otpoperation = new IOTP() {
            @Override
            public void sendOTP(String mobileNo) {

            }

            @Override
            public boolean verifyOTP(String OTPEntered) {
                return true;
            }

            @Override
            public String SendOTP() {
                return null;
            }
        };
        String otpCode = otpoperation.SendOTP();

        // Verify OTP
        boolean isOTPVerified = otpoperation.verifyOTP(otpCode);

        if (isAPICheckPassed && isOTPVerified) {
            //call function adduser();
            System.out.println("User signed up successfully!");
        } else {
            System.out.println("User signup failed. Please check API, OTP, and password.");
        }
    }
    public boolean isPasswordStrong(String password) {
        return password != null && password.length() >= 8 &&
                password.matches(".*[A-Z].*") && password.matches(".*[!@#$%^&*()-_=+\\|[{]};:'\",<.>/?].*");
    }

}