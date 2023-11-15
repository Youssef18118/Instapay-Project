import java.util.Date;

class BankUser extends User {
    private String bankUserName;
    private String cardNo;
    private int cvv;
    private Date expirationDate;

    public BankUser(String userName, String password, String mobileNo, double balance,
                    String bankUserName, String cardNo, int cvv, Date expirationDate) {
        super(userName, password, mobileNo, balance, UserType.BANK_USER);
        this.bankUserName = bankUserName;
        this.cardNo = cardNo;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
