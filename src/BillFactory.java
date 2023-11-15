import java.util.ArrayList;
import java.util.List;

class BillFactory {
    private static List<Bill> createdBills = new ArrayList<>();
    private static int billIDCounter = 1;

    private static synchronized int getNextBillID() {
        return billIDCounter++;
    }

    public static Bill createBill(BillType billType, String companyName, int electronicPaymentNo) {
        // Check if a bill with the given electronic payment number already exists
        for (Bill existingBill : createdBills) {
            if (existingBill.getElectronicPaymentNo() == electronicPaymentNo) {
                System.out.println("Bill with Electronic Payment No. " + electronicPaymentNo + " already exists. \n");
                return null;
            }
        }

        Bill bill;

        switch (billType) {
            case GAS:
                bill = new GasBill(getNextBillID(), electronicPaymentNo, 0, companyName, 456);
                bill.setBillAPI(new GasBillAPI(new GasCompany())); // Set GasBillAPI
                ((GasBill) bill).setReadNo(electronicPaymentNo);
                double gasBillExpenses = bill.getBillAPI().getBillExpenses(electronicPaymentNo);
                bill.setBillExpenses(gasBillExpenses);
                break;
            case ELECTRICITY:
                bill = new ElectricityBill(getNextBillID(), electronicPaymentNo, 0, companyName, 789);
                bill.setBillAPI(new ElectricityBillAPI(new ElectricityCompany())); // Set ElectricityBillAPI
                ((ElectricityBill) bill).setreadNo(electronicPaymentNo);
                double electricityBillExpenses = bill.getBillAPI().getBillExpenses(electronicPaymentNo);
                bill.setBillExpenses(electricityBillExpenses);
                break;
            case WATER:
                bill = new WaterBill(getNextBillID(), electronicPaymentNo, 0, companyName, 123);
                bill.setBillAPI(new WaterBillAPI(new WaterCompany())); // Set WaterBillAPI
                ((WaterBill) bill).setConsumption(electronicPaymentNo);
                double waterBillExpenses = bill.getBillAPI().getBillExpenses(electronicPaymentNo);
                bill.setBillExpenses(waterBillExpenses);
                break;
            default:
                System.out.println("Invalid bill type");
                return null;
        }

        // Add the created bill to the list
        createdBills.add(bill);
        return bill;
    }
}
