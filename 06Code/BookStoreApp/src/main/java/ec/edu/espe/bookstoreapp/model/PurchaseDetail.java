package ec.edu.espe.bookstoreapp.model;

/**
 *
 * @author Julian Olalla, 404 Founders, @ESPE
 */
public class PurchaseDetail {
    private static int idCurrent;
    private int idPurchaseDetail;
    private int stockPurchase;
    private float purchasePrice;
    
    private Book book;
    private Journal journal;
    
    
    public PurchaseDetail(int stockPurchase, float purchasePrice, Book book, Journal journal) {
        this.idPurchaseDetail = idCurrent++;
        this.stockPurchase = stockPurchase;
        this.purchasePrice = purchasePrice;
        this.book = book;
        this.journal = journal;
    }
    
    public float computeSubtotal(){
        return stockPurchase * purchasePrice;
    }
    
    @Override
    public String toString(){
        return "PurchaseDetail{" + "idPurchaseDetail=" + getIdPurchaseDetail()
                + ", stockPurchase=" + getStockPurchase()
                + ", purchasePrice=" + getPurchasePrice()
                + ", book=" + getBook()
                + ", journal=" + getJournal() + '}';
    }

    /**
     * @return the getIdCurrent
     */
    public static int getIdCurrent() {
        return idCurrent;
    }
    
    /**
     * @param idCurrent
     */
     public static void setIdCurrent(int idCurrent) {
        PurchaseDetail.idCurrent = idCurrent;
    }   
    
    /**
     * @return the idPurchaseDetail
     */
    public int getIdPurchaseDetail() {
        return idPurchaseDetail;
    }

    /**
     * @param idPurchaseDetail
     */
    public void setIdPurchaseDetail(int idPurchaseDetail) {
        this.idPurchaseDetail = idPurchaseDetail;
    }

    /**
     * @return the stockPurchase
     */
    public int getStockPurchase() {
        return stockPurchase;
    }

    /**
     * @param stockPurchase the stockPurchase to set
     */
    public void setStockPurchase(int stockPurchase) {
        this.stockPurchase = stockPurchase;
    }

    /**
     * @return the purchasePrice
     */
    public float getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * @param purchasePrice the purchasePrice to set
     */
    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * @return the journal
     */
    public Journal getJournal() {
        return journal;
    }

    /**
     * @param journal the journal to set
     */
    public void setJournal(Journal journal) {
        this.journal = journal;
    }
    
}
