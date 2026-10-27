package ec.edu.espe.bookstoreapp.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Dennis Noboa, 404 Founders, @ESPE
 */
public class Sale {
    private static int idCurrent;
    private int idSale;
    private LocalDate date;
    private Customer customer;
    private String typeSale;
    private Payment payment;
    private ArrayList<SaleDetail> details;

    public Sale(LocalDate date, Customer customer, String typeSale) {
        this.idSale = idCurrent++;
        this.date = date;
        this.customer = customer;
        this.typeSale = typeSale;        
        this.details = new ArrayList<>();
    }

    public void addDetail(SaleDetail detail) {
        if (detail != null && detail.validateProduct()) {
            details.add(detail);
            
            // Descontar del stock al realizar la venta
            if (detail.getBook() != null) {
                Book b = detail.getBook();
                b.setStock(b.getStock() - detail.getQuantityProduct());
            }
            if (detail.getJournal() != null) {
                Journal j = detail.getJournal();
                j.setStock(j.getStock() - detail.getQuantityProduct());
            }
        }
    }

    public float computeTotal() {
        float total = 0;
        for (SaleDetail detail : details) {
            total += detail.computeSubtotal();
        }
        return total;
    }

    public float calculateProfit(String periodType) {
        // Lógica para estimar ganancia según período
        return computeTotal() * 0.30f; // Ejemplo: 30% de margen de ganancia
    }

    public String generateSalesReport(LocalDate startDate, LocalDate endDate) {
        StringBuilder report = new StringBuilder();
        report.append("--- REPORTE DE VENTA ID: ").append(idSale).append(" ---\n");
        report.append("Fecha: ").append(date).append("\n");
        report.append("Cliente: ").append(customer != null ? customer.getName() : "Consumidor Final").append("\n");
        report.append("Tipo de Venta: ").append(typeSale).append("\n");
        report.append("Total: $").append(computeTotal()).append("\n");
        return report.toString();
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
        Sale.idCurrent = idCurrent;
    }

    /**
     * @return the idSale
     */
    public int getIdSale() {
        return idSale;
    }

    /**
     * @param idSale the idSale to set
     */
    public void setIdSale(int idSale) {
        this.idSale = idSale;
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
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the typeSale
     */
    public String getTypeSale() {
        return typeSale;
    }

    /**
     * @param typeSale the typeSale to set
     */
    public void setTypeSale(String typeSale) {
        this.typeSale = typeSale;
    }

    /**
     * @return the payment
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * @return the details
     */
    public ArrayList<SaleDetail> getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(ArrayList<SaleDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Sale{" + "idSale=" + idSale + ", date=" + date + ", customer=" + customer + ", typeSale=" + typeSale + ", payment=" + payment + ", details=" + details + '}';
    }
    
}
