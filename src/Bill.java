abstract class Bill {
    private int billID;
    private int electronicPaymentNo;
    private double billExpenses;
    private String companyName;
    BillAPI billAPI;

    public Bill(int billID, int electronicPaymentNo, double billExpenses, String companyName) {
        this.billID = billID;
        this.electronicPaymentNo = electronicPaymentNo;
        this.billExpenses = billExpenses;
        this.companyName = companyName;
    }

    public abstract String displayBill();

    // Getters and setters for the attributes

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getElectronicPaymentNo() {
        return electronicPaymentNo;
    }

    public void setElectronicPaymentNo(int electronicPaymentNo) {
        this.electronicPaymentNo = electronicPaymentNo;
    }

    public double getBillExpenses() {
        return billExpenses;
    }

    public void setBillExpenses(double billExpenses) {
        this.billExpenses = billExpenses;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BillAPI getBillAPI() {
        return this.billAPI;
    }

    public void setBillAPI(BillAPI billAPI) {
        this.billAPI = billAPI;
    }
}
