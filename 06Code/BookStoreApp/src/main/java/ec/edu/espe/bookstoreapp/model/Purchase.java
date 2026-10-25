package ec.edu.espe.bookstoreapp.model;

import java.time.LocalDate;

/**
 *
 * @author Julian Olalla, 404 Founders, @ESPE
 */
public class Purchase {
    private int idPurchase;
    private LocalDate date;
    private float totalPurchase;
    
    private Supplier supplier;

    public Purchase(int idPurchase, LocalDate date, float totalPurchase, Supplier supplier) {
        this.idPurchase = idPurchase;
        this.date = date;
        this.totalPurchase = totalPurchase;
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Purchase{" + "idPurchase=" + getIdPurchase() + ", date=" + getDate() + ", totalPurchase=" + getTotalPurchase() + ", supplier=" + getSupplier() + '}';
    }
    
    public PurchaseDetail addDetailPurchase(detail : PurchaseDetail){
        
    }

    /**
     * @return the idPurchase
     */
    public int getIdPurchase() {
        return idPurchase;
    }

    /**
     * @param idPurchase the idPurchase to set
     */
    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return the totalPurchase
     */
    public float getTotalPurchase() {
        return totalPurchase;
    }

    /**
     * @param totalPurchase the totalPurchase to set
     */
    public void setTotalPurchase(float totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    /**
     * @return the supplier
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * @param supplier the supplier to set
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }        
}
