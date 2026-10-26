package ec.edu.espe.bookstoreapp.model;

/**
 *
 * @author Dennis Noboa, 404 Founder, @ESPE
 */
public class Payment {
    static int idCurrent;
    int idPayment;
    float amountPayment;
    PaymentMethod paymentMethod;

    public Payment(float amountPayment, PaymentMethod paymentMethod) {
        this.idPayment = idCurrent++;
        this.amountPayment = amountPayment;
        this.paymentMethod = paymentMethod;
    }
    
    /**
     * @return the idCurrent
     */
    public static int getIdCurrent() {
        return idCurrent;
    }

    /**
     * @param idCurrent the idCurrent to set
     */
    public static void setIdCurrent(int idCurrent) {
        Payment.idCurrent = idCurrent;
    }

    /**
     * @return the idPayment
     */
    public int getIdPayment() {
        return idPayment;
    }

    /**
     * @param idPayment the idPayment to set
     */
    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }
    
    /**
     * @param total the total amount to validate against
     * @return true if the payment amount is greater than or equal to the total, false otherwise
     */
    public boolean validatePayment(float total) {
        return amountPayment >= total;
    }

    /**
     * @return the amountPayment
     */
    public float getAmountPayment() {
        return amountPayment;
    }

    /**
     * @param amountPayment the amountPayment to set
     */
    public void setAmountPayment(float amountPayment) {
        this.amountPayment = amountPayment;
    }

    /**
     * @return the paymentMethod
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Payment{" + "idPayment=" + idPayment + ", amountPayment=" + amountPayment + ", paymentMethod=" + paymentMethod + '}';
    }
    
}
