public class OTP implements IOTP {
    private String mobileNo;
    private int duration;

    public OTP(String mobileNo, int duration) {
        this.mobileNo = mobileNo;
        this.duration = duration;
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
    public boolean verifyOTP(String OTPEntered) {

        String expectedOTP = "123456";
        return OTPEntered.equals(expectedOTP);
    }

    @Override
    public String SendOTP() {
        return null;
    }
}