public class OTP implements IOTP {
    private String mobileNo;
    private int duration = 10;

    public OTP(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void sendOTP(String mobileNo) {
        System.out.println("OTP sent to " + mobileNo);
    }

    @Override
    public boolean verifyOTP(int OTPEntered) {
        if (duration == 0) {
            System.out.println("Duration is Consumed for OTP verfication. Another OTP sent to Your phone check");
            duration = 10;
            int expectedOTP = 123456;
            return (OTPEntered == expectedOTP);
        } else {
            int expectedOTP = 12345;
            duration--;
            return (OTPEntered == expectedOTP);
        }
    }

    @Override
    public String SendOTP() {
        return null;
    }
}