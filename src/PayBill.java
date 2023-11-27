class PayBill {
    public static boolean payBill(User user, Bill bill) {
        // Call the getBillExpenses method from BillAPI
        double billExpenses = bill.getBillAPI().getBillExpenses(bill.getElectronicPaymentNo());
        bill.setBillExpenses(billExpenses);

        double balance = user.getBalance();
        if (balance < billExpenses) {
            System.out.println("Insufficient Balance");
            return false;
        }
        user.setBalance(balance - billExpenses);

        return true;
    }
}