class GasBillAPI implements BillAPI {
    private final Company gasCompany;

    public GasBillAPI(Company gasCompany) {
        this.gasCompany = gasCompany;
    }

    @Override
    public double getBillExpenses(int electroNo) {
        // GasBillAPI contacts the GasCompany to get bill expenses
        return gasCompany.getBillExpenses(electroNo);
    }
}