package ec.edu.espe.bookstoreapp.controller;

import ec.edu.espe.bookstoreapp.model.Book;
import ec.edu.espe.bookstoreapp.model.Customer;
import ec.edu.espe.bookstoreapp.model.Journal;
import ec.edu.espe.bookstoreapp.model.Purchase;
import ec.edu.espe.bookstoreapp.model.Sale;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Julian Olalla, 404 Founders, @ESPE
 */
public class OperationsTest {
    
    public OperationsTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of searchBookByTitle method, of class Operations.
     */
    @Test
    public void testSearchBookByTitle() {
        System.out.println("searchBookByTitle");
        String titleQuery = "";
        ArrayList<Book> allBooks = null;
        ArrayList<Book> expResult = null;
        ArrayList<Book> result = Operations.searchBookByTitle(titleQuery, allBooks);
        assertEquals(expResult, result);       
    }

    /**
     * Test of searchBookByAuthor method, of class Operations.
     */
    @Test
    public void testSearchBookByAuthor() {
        System.out.println("searchBookByAuthor");
        String authorName = "";
        ArrayList<Book> allBooks = null;
        ArrayList<Book> expResult = null;
        ArrayList<Book> result = Operations.searchBookByAuthor(authorName, allBooks);
        assertEquals(expResult, result);       
    }

    /**
     * Test of searchJournalByTitle method, of class Operations.
     */
    @Test
    public void testSearchJournalByTitle() {
        System.out.println("searchJournalByTitle");
        String titleQuery = "";
        ArrayList<Journal> allJournal = null;
        ArrayList<Journal> expResult = null;
        ArrayList<Journal> result = Operations.searchJournalByTitle(titleQuery, allJournal);
        assertEquals(expResult, result);       
    }

    /**
     * Test of addBook method, of class Operations.
     */
    @Test
    public void testAddBook() {
        System.out.println("addBook");
        ArrayList<Book> inventory = null;
        String title = "";
        ArrayList<String> authorNames = null;
        String editorialName = "";
        float price = 0.0F;
        String state = "";
        String genreName = "";
        String eduLevelName = "";
        Operations.addBook(inventory, title, authorNames, editorialName, price, state, genreName, eduLevelName);        
    }

    /**
     * Test of addJournal method, of class Operations.
     */
    @Test
    public void testAddJournal() {
        System.out.println("addJournal");
        ArrayList<Journal> inventory = null;
        String name = "";
        String editorialName = "";
        LocalDate yearOfPublication = null;
        float price = 0.0F;
        String state = "";
        Operations.addJournal(inventory, name, editorialName, yearOfPublication, price, state);        
    }

    /**
     * Test of findOrCreateCustomer method, of class Operations.
     */
    @Test
    public void testFindOrCreateCustomer() {
        System.out.println("findOrCreateCustomer");
        String customerName = "";
        ArrayList<Customer> customers = null;
        Customer expResult = null;
        Customer result = Operations.findOrCreateCustomer(customerName, customers);
        assertEquals(expResult, result);       
    }

    /**
     * Test of createSale method, of class Operations.
     */
    @Test
    public void testCreateSale() {
        System.out.println("createSale");
        Customer customer = null;
        String typeSale = "";
        Sale expResult = null;
        Sale result = Operations.createSale(customer, typeSale);
        assertEquals(expResult, result);        
    }

    /**
     * Test of addBookToSale method, of class Operations.
     */
    @Test
    public void testAddBookToSale() {
        System.out.println("addBookToSale");
        Sale sale = null;
        Book book = null;
        int quantity = 0;
        boolean expResult = false;
        boolean result = Operations.addBookToSale(sale, book, quantity);
        assertEquals(expResult, result);        
    }

    /**
     * Test of addJournalToSale method, of class Operations.
     */
    @Test
    public void testAddJournalToSale() {
        System.out.println("addJournalToSale");
        Sale sale = null;
        Journal journal = null;
        int quantity = 0;
        boolean expResult = false;
        boolean result = Operations.addJournalToSale(sale, journal, quantity);
        assertEquals(expResult, result);        
    }

    /**
     * Test of registerPayment method, of class Operations.
     */
    @Test
    public void testRegisterPayment() {
        System.out.println("registerPayment");
        Sale sale = null;
        String paymentMethodName = "";
        float amountPaid = 0.0F;
        ArrayList<Sale> sales = null;
        boolean expResult = false;
        boolean result = Operations.registerPayment(sale, paymentMethodName, amountPaid, sales);
        assertEquals(expResult, result);        
    }

    /**
     * Test of totalSalesByPeriod method, of class Operations.
     */
    @Test
    public void testTotalSalesByPeriod() {
        System.out.println("totalSalesByPeriod");
        LocalDate start = null;
        LocalDate end = null;
        ArrayList<Sale> sales = null;
        float expResult = 0.0F;
        float result = Operations.totalSalesByPeriod(start, end, sales);
        assertEquals(expResult, result, 0);        
    }

    /**
     * Test of createPurchase method, of class Operations.
     */
    @Test
    public void testCreatePurchase() {
        System.out.println("createPurchase");
        String supplierName = "";
        Purchase expResult = null;
        Purchase result = Operations.createPurchase(supplierName);
        assertEquals(expResult, result);        
    }

    /**
     * Test of addBookToPurchase method, of class Operations.
     */
    @Test
    public void testAddBookToPurchase() {
        System.out.println("addBookToPurchase");
        Purchase purchase = null;
        Book book = null;
        int quantity = 0;
        float price = 0.0F;
        Operations.addBookToPurchase(purchase, book, quantity, price);        
    }

    /**
     * Test of addJournalToPurchase method, of class Operations.
     */
    @Test
    public void testAddJournalToPurchase() {
        System.out.println("addJournalToPurchase");
        Purchase purchase = null;
        Journal journal = null;
        int quantity = 0;
        float price = 0.0F;
        Operations.addJournalToPurchase(purchase, journal, quantity, price);        
    }

    /**
     * Test of registerPurchase method, of class Operations.
     */
    @Test
    public void testRegisterPurchase() {
        System.out.println("registerPurchase");
        Purchase purchase = null;
        ArrayList<Purchase> purchases = null;
        Operations.registerPurchase(purchase, purchases);        
    }

    /**
     * Test of requirePositive method, of class Operations.
     */
    @Test
    public void testRequirePositive() throws Exception {
        System.out.println("requirePositive");
        double value = 0.0;
        String fieldName = "";
        Operations.requirePositive(value, fieldName);        
    }

    /**
     * Test of requireNonNegative method, of class Operations.
     */
    @Test
    public void testRequireNonNegative() throws Exception {
        System.out.println("requireNonNegative");
        double value = 0.0;
        String fieldName = "";
        Operations.requireNonNegative(value, fieldName);        
    }

    /**
     * Test of requireInRange method, of class Operations.
     */
    @Test
    public void testRequireInRange() throws Exception {
        System.out.println("requireInRange");
        int value = 0;
        int min = 0;
        int max = 0;
        String fieldName = "";
        Operations.requireInRange(value, min, max, fieldName);        
    }

    /**
     * Test of requireProducts method, of class Operations.
     */
    @Test
    public void testRequireProducts() throws Exception {
        System.out.println("requireProducts");
        ArrayList inventory = null;
        String productName = "";
        Operations.requireProducts(inventory, productName);        
    }

    /**
     * Test of requireAnyProduct method, of class Operations.
     */
    @Test
    public void testRequireAnyProduct() throws Exception {
        System.out.println("requireAnyProduct");
        ArrayList<Book> books = null;
        ArrayList<Journal> journals = null;
        Operations.requireAnyProduct(books, journals);        
    }
    
}
