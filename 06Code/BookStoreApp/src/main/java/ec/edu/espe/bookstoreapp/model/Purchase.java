package ec.edu.espe.bookstoreapp.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Julian Olalla, 404 Founders, @ESPE
 */
public class Purchase {
    private static int idCurrent;
    private int idPurchase;
    private LocalDate date;
    private float totalPurchase;
    
    private Supplier supplier;
    private ArrayList<PurchaseDetail> details;

    public Purchase(LocalDate date, Supplier supplier) {
        this.idPurchase = idCurrent++;
        this.date = date;        
        this.supplier = supplier;
        this.details = new ArrayList<>();
        this.totalPurchase = 0;
    }
    
    public void addDetailPurchase(PurchaseDetail detail) {
        if (detail != null && detail.getStockPurchase() > 0) {
            details.add(detail);
            
            Book book = detail.getBook();
            if (book != null){
                book.setStock(book.getStock() + detail.getStockPurchase());
            }
            
            Journal journal = detail.getJournal();
            if (journal != null){
                journal.setStock(journal.getStock() + detail.getStockPurchase());
            }
            computeTotalPurchase();
        }
    }
    
    public float computeTotalPurchase() {
        float total = 0;
        for (PurchaseDetail detail : getDetails()) {
            total += detail.computeSubtotal();
        }
        this.totalPurchase = total;
        return total;
    }
    
    public static float getTotalPurchasesByPeriod(LocalDate startDate, LocalDate endDate,
            ArrayList<Purchase> allPurchases) {
        float total = 0;
        if (allPurchases != null && startDate != null && endDate != null) {
            for (Purchase purchase : allPurchases) {
                LocalDate purchaseDate = purchase.getDate();
                if (purchaseDate != null
                        && !purchaseDate.isBefore(startDate)
                        && !purchaseDate.isAfter(endDate)) {
                    total += purchase.getTotalPurchase();
                }
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "Purchase{" + "idPurchase=" + getIdPurchase() 
                + ", date=" + getDate() 
                + ", totalPurchase=" + getTotalPurchase() 
                + ", supplier=" + getSupplier() 
                + ", details=" + getDetails() + '}';
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

    /**
     * @return the details
     */
    public ArrayList<PurchaseDetail> getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(ArrayList<PurchaseDetail> details) {
        this.details = details;
    }                
    
}