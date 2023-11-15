class ElectricityBill extends Bill {
    private int readNo;

    public ElectricityBill(int billID, int electronicPaymentNo, double billExpenses, String companyName,
                           int readNo) {
        super(billID, electronicPaymentNo, billExpenses, companyName);
        this.readNo = readNo;
    }

    @Override
    public String displayBill() {
        return "Electricity Bill: " +
                "BillID=" + getBillID() +
                ", ElectronicPaymentNo=" + getElectronicPaymentNo() +
                ", BillExpenses=" + getBillExpenses() +
                ", CompanyName=" + getCompanyName() +
                ", readNo=" + readNo;
    }

    // Getter and setter for readNo

    public int getreadNo() {
        return readNo;
    }

    public void setreadNo(int readNo) {
        this.readNo = readNo;
    }
}