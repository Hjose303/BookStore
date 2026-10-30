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
    
    public float computeTotal() {
        float total = 0;
        for (PurchaseDetail detail : details) {
            total += detail.computeSubtotal();
        }
        return total;
    }


    public void addDetailPurchase(PurchaseDetail detail) {
        if (detail != null
                && detail.getStockPurchase() > 0
                && detail.getPurchasePrice() >= 0
                && (detail.getBook() != null
                || detail.getJournal() != null)) {

            details.add(detail);
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
    
    public String generatePurchaseReport(LocalDate startDate, LocalDate endDate) {
        StringBuilder report = new StringBuilder();
        report.append("--- REPORTE DE COMPRA ID: ").append(idPurchase).append(" ---\n");
        report.append("Fecha: ").append(date).append("\n");
        report.append("Proveedor: ").append(supplier != null ? supplier.getNameSupplier() : "Desconocido").append("\n");
        report.append("Total de la Adquisición: $").append(computeTotal()).append("\n");
        return report.toString();
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
