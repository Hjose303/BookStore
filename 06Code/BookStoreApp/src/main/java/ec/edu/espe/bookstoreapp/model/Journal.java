
package ec.edu.espe.bookstoreapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 *
 * @author Nestor Olivo, Found ERROR 404, @ESPE
 */
public class Journal {
    private static int id;
    private int idJournal;
    private String nameJournal;
    private Editorial editorial;
    private LocalDate yearOfPublication;
    private float price;
    private int stock;
    private String state;
    
    
    
    public Journal(String nameJournal, Editorial editorial, LocalDate yearOfPublication, float price, int stock, String state) {
        this.nameJournal = nameJournal;
        this.editorial = editorial;
        this.yearOfPublication = yearOfPublication;
        this.price = price;
        this.stock = stock;
        this.state = state;
        this.idJournal = id++;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Journal.id = id;
    }

    public int getIdJournal() {
        return idJournal;
    }

    public void setIdJournal(int idJournal) {
        this.idJournal = idJournal;
    }

    public String getNameJournal() {
        return nameJournal;
    }

    public void setNameJournal(String nameJournal) {
        this.nameJournal = nameJournal;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public LocalDate getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(LocalDate yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Journal{" 
                + "idJournal=" + idJournal 
                + ", nameJournal=" + nameJournal 
                + ", editorial=" + editorial 
                + ", yearOfPublication=" + yearOfPublication 
                + ", price=" + price 
                + ", stock=" + stock 
                + ", state=" + state 
                + '}';
    }
    
}
