class WaterBillAPI implements BillAPI {
    private final Company waterCompany;

    public WaterBillAPI(Company waterCompany) {
        this.waterCompany = waterCompany;
    }

    @Override
    public double getBillExpenses(int electroNo) {
        // WaterBillAPI contacts the WaterCompany to get bill expenses
        return waterCompany.getBillExpenses(electroNo);
    }
}
