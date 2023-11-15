class WaterBill extends Bill {
    private int consumption;

    public WaterBill(int billID, int electronicPaymentNo, double billExpenses, String companyName, int consumption) {
        super(billID, electronicPaymentNo, billExpenses, companyName);
        this.consumption = consumption;
    }

    @Override
    public String displayBill() {
        return "Water Bill: " +
                "BillID=" + getBillID() +
                ", ElectronicPaymentNo=" + getElectronicPaymentNo() +
                ", BillExpenses=" + getBillExpenses() +
                ", CompanyName=" + getCompanyName() +
                ", Consumption=" + consumption;
    }

    // Getter and setter for Consumption

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }
}
