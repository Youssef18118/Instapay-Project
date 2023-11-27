public interface IOTP {
    // Method to send OTP to the specified mobile number
    void sendOTP(String mobileNo);

    // Method to verify the entered OTP
    boolean verifyOTP(int OTPEntered);

    String SendOTP();
}