
package ec.edu.espe.bookstoreapp.model;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Nestor Olivo, Found ERROR 404, @ESPE
 */
public class Book {
    private static int id;
    private int idBook;
    private String title;
    private ArrayList<Author> authors;
    private Editorial editorial;
    private float price;
    private int stock;
    private String state;
    private LiteraryGenre literaryGenre;
    private EducationalLevel educationalLevel;
    
    public ArrayList<Book> searchBookByAuthor(String authorName, ArrayList<Book> allBooks) {
        ArrayList<Book> results = new ArrayList<>();
        if (allBooks != null) {
            results = allBooks.stream()
                    .filter(b -> b.getAuthors() != null
                    && b.getAuthors().stream().anyMatch(a -> a.getName().equalsIgnoreCase(authorName)))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return results;
    }
    
    public ArrayList<Book> searchBookByTitle(String titleQuery, ArrayList<Book> allBooks) {
        ArrayList<Book> results = new ArrayList<>();
        if (allBooks != null) {
            results = allBooks.stream()
                    .filter(b -> b.getTitle() != null
                    && b.getTitle().toLowerCase().contains(titleQuery.toLowerCase()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return results;
    }
    
    
    public Book(String title, ArrayList<Author> authors, Editorial editorial, 
            float price, int stock, String state, LiteraryGenre literaryGenre, 
            EducationalLevel educationalLevel) {
        
        this.idBook = id++;
        this.title = title;
        this.authors = authors;
        this.editorial = editorial;
        this.price = price;
        this.stock = stock;
        this.state = state;
        this.literaryGenre = literaryGenre;
        this.educationalLevel = educationalLevel;
        
    }
    
    
    
    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Book.id = id;
    }
    
    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
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

    public LiteraryGenre getLiteraryGenre() {
        return literaryGenre;
    }

    public void setLiteraryGenre(LiteraryGenre literaryGenre) {
        this.literaryGenre = literaryGenre;
    }

    public EducationalLevel getEducationalLevel() {
        return educationalLevel;
    }

    public void setEducationalLevel(EducationalLevel educationalLevel) {
        this.educationalLevel = educationalLevel;
    }
    
}
