
class ElectricityBillAPI implements BillAPI {
    private final Company electricityCompany;

    public ElectricityBillAPI(Company electricityCompany) {
        this.electricityCompany = electricityCompany;
    }

    @Override
    public double getBillExpenses(int electroNo) {
        // ElectricityBillAPI contacts the ElectricityCompany to get bill expenses
        return electricityCompany.getBillExpenses(electroNo);
    }
}
