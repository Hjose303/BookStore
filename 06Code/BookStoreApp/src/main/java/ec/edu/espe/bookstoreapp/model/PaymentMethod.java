package ec.edu.espe.bookstoreapp.model;

/**
 *
 * @author Dennis Noboa, 404 Founders, @ESPE
 */
public class PaymentMethod {
    private int idCurrent;
    private int idPaymentMethod;
    private String namePaymentMethod;
    
    public PaymentMethod(String namePaymentMethod) {
        this.idPaymentMethod = idCurrent++;
        this.namePaymentMethod = namePaymentMethod;
    }

    /**
     * @return the idCurrent
     */
    public int getIdCurrent() {
        return idCurrent;
    }

    /**
     * @param idCurrent the idCurrent to set
     */
    public void setIdCurrent(int idCurrent) {
        this.idCurrent = idCurrent;
    }

    /**
     * @return the idPaymentMethod
     */
    public int getIdPaymentMethod() {
        return idPaymentMethod;
    }

    /**
     * @param idPaymentMethod the idPaymentMethod to set
     */
    public void setIdPaymentMethod(int idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    /**
     * @return the namePaymentMethod
     */
    public String getNamePaymentMethod() {
        return namePaymentMethod;
    }

    /**
     * @param namePaymentMethod the namePaymentMethod to set
     */
    public void setNamePaymentMethod(String namePaymentMethod) {
        this.namePaymentMethod = namePaymentMethod;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" + "idPaymentMethod=" + idPaymentMethod + ", namePaymentMethod=" + namePaymentMethod + ", namePaymentMethod=" + namePaymentMethod + '}';
    }
}
