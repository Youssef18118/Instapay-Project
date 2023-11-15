class SignUp {
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
                return false;
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