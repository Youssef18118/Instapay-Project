import java.util.Date;
import java.util.Scanner;

class SignUp {
    private BankUser bUser = new BankUser();
    private WalletUser wUser = new WalletUser();
    // private InstapaySystem syst;
    private IOTP otpoperation;
    private API CheckApI;
    Scanner scanner;

    // maybe wrong
    public SignUp(API CheckApI) {
        this.CheckApI = CheckApI;
        this.scanner = new Scanner(System.in); // Initialize the scanner
    }

    public void setCheckApI(API checkApI) {
        this.CheckApI = checkApI;
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

    public BankUser getInfoUserBank(String username, String password, String mobile, int cvv, String cardNum,
            Date expDate) {
        bUser.setUserName(username);
        bUser.setPassword(password);
        bUser.setMobileNo(mobile);
        bUser.setCvv(cvv);
        bUser.setCardNo(cardNum);
        bUser.setExpirationDate(expDate);
        return bUser;

    }

    public WalletUser getInfoWallet(String username, String password, String mobile) {
        wUser.setUserName(username);
        wUser.setPassword(password);
        wUser.setMobileNo(mobile);
        return wUser;
    }

    public boolean makeSignup(User user) {
        // perform PasswordCheck
        while (!isPasswordStrong(user.getPassword())) {
            System.out.println(
                    "Password is not strong enough!!! Please enter a password with at least 8 characters, 1 special character, and 1 uppercase letter.");
            System.out.println("Please enter the password again:");
            String pass = scanner.nextLine();
            // Validate the password and set it in the user object
            if (isPasswordStrong(pass)) {
                user.setPassword(pass);
            } else {
                System.out.println("Invalid password format. Try again.");
            }
        }

        if (user.getUserType() == UserType.BANK_USER) {
            CheckApI = new BankAPI();
        } else {
            CheckApI = new WalletAPI();
        }
        // Perform API check
        boolean isAPICheckPassed = CheckApI.verify(user);
        double balance = CheckApI.getBalance(user);
        user.setBalance(balance);

        if (!isAPICheckPassed) {
            System.out.println("User Not Found by API");
            return false;
        }

        otpoperation = new OTP(user.getMobileNo());
        otpoperation.SendOTP();
        System.out.println("OTP Sent successfully");
        System.out.println("pls Enter OTP send to your phone");
        int otpCode = scanner.nextInt();
        scanner.nextLine();

        // Verify OTP
        boolean isOTPVerified = otpoperation.verifyOTP(otpCode);

        if (!isOTPVerified) {
            // call function adduser();
            System.out.println("Not valid OTP !!!");
            return false;
        }

        return true;

    }

    public boolean isPasswordStrong(String password) {
        return password != null && password.length() >= 8 &&
                password.matches(".*[A-Z].*") && password.matches(".*[!@#$%^&*()-_=+\\|[{]};:'\",<.>/?].*");
    }

}