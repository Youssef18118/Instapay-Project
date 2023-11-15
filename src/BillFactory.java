class BillFactory {
    private static Bill bill;
    private static int billIDCounter = 1;

    private static synchronized int getNextBillID() {
        return billIDCounter++;
    }

    public static Bill createBill(BillType billType, String companyName, int electronicPaymentNo) {
        switch (billType) {
            case GAS:
                bill = new GasBill(getNextBillID(), electronicPaymentNo, 0, companyName, 456);
                ((GasBill) bill).setReadNo(electronicPaymentNo);
                bill.setBillAPI(new GasBillAPI(new GasCompany())); // Set GasBillAPI
                break;
            case ELECTRICITY:
                bill = new ElectricityBill(getNextBillID(), electronicPaymentNo, 0, companyName, 789);
                ((ElectricityBill) bill).setreadNo(electronicPaymentNo);
                bill.setBillAPI(new ElectricityBillAPI(new ElectricityCompany())); // Set ElectricityBillAPI
                break;
            case WATER:
                bill = new WaterBill(getNextBillID(), electronicPaymentNo, 0, companyName, 123);
                ((WaterBill) bill).setConsumption(electronicPaymentNo);
                bill.setBillAPI(new WaterBillAPI(new WaterCompany())); // Set WaterBillAPI
                break;
            default:
                System.out.println("Invalid bill type");
                return null;
        }

        return bill;
    }

}
