package oopsprograms;

public class PaymentDemo{

    public static void main(String[] args){
        PaymentService ccPayment = new CCPayment();
        PaymentService payPalPayment = new PayPalPay();
        PaymentService bankPayment = new BankTransferPayment();

        //how to call interface constants interface.constant
        System.out.println("Supported currencies:"+PaymentService.SUPPORTED_CURRENCIES);
        //calling static method of interface -inerface.staticmethod()
        System.out.println("is USD valid? "+PaymentService.validateCurrency("USD"));
        System.out.println("is GBP valid? "+PaymentService.validateCurrency("GBP"));
        System.out.println();
        handlePayment(ccPayment,150.0,"TXN001");
        handlePayment(payPalPayment,200.0,"TXN002");
        handlePayment(bankPayment,300.0,"TXN003");
    }

    public static void handlePayment(PaymentService payment,double amount, String txnId){
        System.out.println("Payment Type: "+payment.getPaymentType());
        payment.processPayment(amount);
        payment.printReceipt(txnId);
        payment.refundPayment(amount);
        System.out.println("-----------------------------------------");

    }
}
