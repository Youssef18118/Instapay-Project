class GasBill extends Bill {
    private int readNo;

    public GasBill(int billID, int electronicPaymentNo, double billExpenses, String companyName, int readNo) {
        super(billID, electronicPaymentNo, billExpenses, companyName);
        this.readNo = readNo;
    }

    @Override
    public String displayBill() {
        return "Gas Bill: " +
                "BillID=" + getBillID() +
                ", ElectronicPaymentNo=" + getElectronicPaymentNo() +
                ", BillExpenses=" + getBillExpenses() +
                ", CompanyName=" + getCompanyName() +
                ", ReadNo=" + readNo;
    }

    // Getter and setter for ReadNo

    public int getReadNo() {
        return readNo;
    }

    public void setReadNo(int readNo) {
        this.readNo = readNo;
    }
}
