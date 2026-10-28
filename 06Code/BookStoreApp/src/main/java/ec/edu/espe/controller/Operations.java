package ec.edu.espe.controller;

import ec.edu.espe.bookstoreapp.model.Author;
import ec.edu.espe.bookstoreapp.model.Book;
import ec.edu.espe.bookstoreapp.model.Customer;
import ec.edu.espe.bookstoreapp.model.Editorial;
import ec.edu.espe.bookstoreapp.model.EducationalLevel;
import ec.edu.espe.bookstoreapp.model.Journal;
import ec.edu.espe.bookstoreapp.model.LiteraryGenre;
import ec.edu.espe.bookstoreapp.model.Payment;
import ec.edu.espe.bookstoreapp.model.PaymentMethod;
import ec.edu.espe.bookstoreapp.model.Purchase;
import ec.edu.espe.bookstoreapp.model.PurchaseDetail;
import ec.edu.espe.bookstoreapp.model.Sale;
import ec.edu.espe.bookstoreapp.model.SaleDetail;
import ec.edu.espe.bookstoreapp.model.Supplier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Julian Olalla, 404 Founders, @ESPE
 */
public class Operations {
    //Searchings
    public static ArrayList<Book> searchBookByTitle(String titleQuery, ArrayList<Book> allBooks) {
        ArrayList<Book> results = new ArrayList<>();
        if (allBooks != null) {
            results = allBooks.stream()
                    .filter(b -> b.getTitle() != null
                    && b.getTitle().toLowerCase().contains(titleQuery.toLowerCase()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return results;
    }

    public static ArrayList<Book> searchBookByAuthor(String authorName, ArrayList<Book> allBooks) {
        ArrayList<Book> results = new ArrayList<>();
        if (allBooks != null) {
            results = allBooks.stream()
                    .filter(b -> b.getAuthors() != null
                    && b.getAuthors().stream().anyMatch(a -> a.getName().equalsIgnoreCase(authorName)))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return results;
    }

    public static ArrayList<Journal> searchJournalByTitle(String titleQuery, ArrayList<Journal> allJournal) {
        ArrayList<Journal> results = new ArrayList<>();
        if (allJournal != null) {
            results = allJournal.stream()
                    .filter(j -> j.getNameJournal() != null
                    && j.getNameJournal().toLowerCase().contains(titleQuery.toLowerCase()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return results;
    }
    
    //Inventory
    public static void addBook(ArrayList<Book> inventory, String title, ArrayList<String> authorNames,
            String editorialName, float price, String state, String genreName, String eduLevelName) {
        ArrayList<Author> authors = new ArrayList<>();
        for (String authorName : authorNames) {
            authors.add(new Author(authorName));
        }
        Editorial editorial = new Editorial(editorialName);
        LiteraryGenre literaryGenre = new LiteraryGenre(genreName);
        EducationalLevel educationalLevel = new EducationalLevel(eduLevelName);
        inventory.add(new Book(title, authors, editorial, price, 0, state, literaryGenre, educationalLevel));
    }

    public static void addJournal(ArrayList<Journal> inventory, String name, String editorialName,
            LocalDate yearOfPublication, float price, String state) {
        Editorial editorial = new Editorial(editorialName);
        inventory.add(new Journal(name, editorial, yearOfPublication, price, 0, state));
    }
    
    //Clients
    public static Customer findOrCreateCustomer(String customerName, ArrayList<Customer> customers) {
        Customer customer = null;
        for (Customer c : customers) {
            if (c.getName().equalsIgnoreCase(customerName)) {
                customer = c;
                break;
            }
        }
        if (customer == null) {
            customer = new Customer(customerName);
            customers.add(customer);
        }
        customer.addVisit(LocalDate.now());
        return customer;
    }
    
    //Sales
    public static Sale createSale(Customer customer, String typeSale) {
        return new Sale(LocalDate.now(), customer, typeSale);
    }
    
    /** @return true si el libro se agregó a la venta; false si el stock no alcanza. */
    public static boolean addBookToSale(Sale sale, Book book, int quantity) {
        return addDetailToSale(sale, new SaleDetail(book, quantity));
    }

    /** @return true si la revista se agregó a la venta; false si el stock no alcanza. */
    public static boolean addJournalToSale(Sale sale, Journal journal, int quantity) {
        return addDetailToSale(sale, new SaleDetail(journal, quantity));
    }
    
    private static boolean addDetailToSale(Sale sale, SaleDetail detail) {
        if (!detail.validateProduct()) {
            return false;
        }
        sale.addDetail(detail);
        return true;
    }
    
    public static boolean registerPayment(Sale sale, String paymentMethodName, float amountPaid,
            ArrayList<Sale> sales) {
        PaymentMethod paymentMethod = new PaymentMethod(paymentMethodName);
        Payment payment = new Payment(amountPaid, paymentMethod);
        if (!payment.validatePayment(sale.computeTotal())) {
            return false;
        }
        sale.setPayment(payment);
        sales.add(sale);
        return true;
    }
    
    public static float totalSalesByPeriod(LocalDate start, LocalDate end, ArrayList<Sale> sales) {
        float totalSales = 0;
        for (Sale s : sales) {
            if (s.getDate() != null && !s.getDate().isBefore(start) && !s.getDate().isAfter(end)) {
                totalSales += s.computeTotal();
            }
        }
        return totalSales;
    }
    
    //Purchases
    public static Purchase createPurchase(String supplierName) {
        return new Purchase(LocalDate.now(), new Supplier(supplierName));
    }

    public static void addBookToPurchase(Purchase purchase, Book book, int quantity, float price) {
        purchase.addDetailPurchase(new PurchaseDetail(quantity, price, book));
    }

    public static void addJournalToPurchase(Purchase purchase, Journal journal, int quantity, float price) {
        purchase.addDetailPurchase(new PurchaseDetail(quantity, price, journal));
    }

    public static void registerPurchase(Purchase purchase, ArrayList<Purchase> purchases) {
        purchases.add(purchase);
    }
}
