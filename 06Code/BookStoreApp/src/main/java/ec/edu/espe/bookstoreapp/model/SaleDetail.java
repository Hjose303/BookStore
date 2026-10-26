/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.bookstoreapp.model;

/**
 *
 * @author Dennis Noboa, 404 Founder, @ESPE
 */
public class SaleDetail {
    private static int idCurrent;
    private int idDetail;
    private Book book;
    private Journal journal;
    private int quantityProduct;

    public SaleDetail(Book book, Journal journal, int quantityProduct) {
        this.idDetail = idCurrent++;
        this.book = book;
        this.journal = journal;
        this.quantityProduct = quantityProduct;
    }

    public boolean validateProduct() {
        if (book != null) {
            return book.getStock() >= quantityProduct;
        }
        if (journal != null) {
            return journal.getStock() >= quantityProduct;
        }
        return false;
    }

    public float computeSubtotal(float unitPrice, int quantityProduct) {
        return unitPrice * quantityProduct;
    }

    public float computeSubtotal() {
        if (book != null) {
            return computeSubtotal(book.getPrice(), quantityProduct);
        }
        if (journal != null) {
            return computeSubtotal(journal.getPrice(), quantityProduct);
        }
        return 0.0f;
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
        SaleDetail.idCurrent = idCurrent;
    }

    /**
     * @return the idDetail
     */
    public int getIdDetail() {
        return idDetail;
    }

    /**
     * @param idDetail the idDetail to set
     */
    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
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

    /**
     * @return the quantityProduct
     */
    public int getQuantityProduct() {
        return quantityProduct;
    }

    /**
     * @param quantityProduct the quantityProduct to set
     */
    public void setQuantityProduct(int quantityProduct) {
        this.quantityProduct = quantityProduct;
    }

    @Override
    public String toString() {
        return "SaleDetail{" + "idDetail=" + idDetail + ", book=" + book + ", journal=" + journal + ", quantityProduct=" + quantityProduct + '}';
    }
    
}
