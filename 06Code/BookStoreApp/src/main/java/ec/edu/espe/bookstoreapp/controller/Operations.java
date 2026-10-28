package ec.edu.espe.bookstoreapp.controller;

import ec.edu.espe.bookstoreapp.exception.InvalidValueException;
import ec.edu.espe.bookstoreapp.exception.EmptyInventoryException;
import ec.edu.espe.bookstoreapp.model.Author;
import ec.edu.espe.bookstoreapp.model.Book;
import ec.edu.espe.bookstoreapp.model.Customer;
import ec.edu.espe.bookstoreapp.model.EducationalLevel;
import ec.edu.espe.bookstoreapp.model.Editorial;
import ec.edu.espe.bookstoreapp.model.Journal;
import ec.edu.espe.bookstoreapp.model.LiteraryGenre;
import ec.edu.espe.bookstoreapp.model.Payment;
import ec.edu.espe.bookstoreapp.model.PaymentMethod;
import ec.edu.espe.bookstoreapp.model.Purchase;
import ec.edu.espe.bookstoreapp.model.PurchaseDetail;
import ec.edu.espe.bookstoreapp.model.Sale;
import ec.edu.espe.bookstoreapp.model.SaleDetail;
import ec.edu.espe.bookstoreapp.model.Supplier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 *
 * @author Julian Olalla, 404 Founders, @ESPE
 */
public class Operations {

    public static ArrayList<Book> searchBookByTitle(
            String titleQuery, ArrayList<Book> allBooks) {

        if (allBooks == null || titleQuery == null) {
            return new ArrayList<>();
        }
        return allBooks.stream()
                .filter(book -> book != null
                        && book.getTitle() != null
                        && book.getTitle().toLowerCase()
                                .contains(titleQuery.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Book> searchBookByAuthor(
            String authorName, ArrayList<Book> allBooks) {

        if (allBooks == null || authorName == null) {
            return new ArrayList<>();
        }

        return allBooks.stream()
                .filter(book -> book != null
                        && book.getAuthors() != null
                        && book.getAuthors().stream()
                                .anyMatch(author -> author != null
                                        && author.getName() != null
                                        && author.getName().equalsIgnoreCase(
                                                authorName)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Journal> searchJournalByTitle(
            String titleQuery, ArrayList<Journal> allJournals) {

        if (allJournals == null || titleQuery == null) {
            return new ArrayList<>();
        }

        return allJournals.stream()
                .filter(journal -> journal != null
                        && journal.getNameJournal() != null
                        && journal.getNameJournal().toLowerCase()
                                .contains(titleQuery.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void addBook(
            ArrayList<Book> inventory,
            String title,
            ArrayList<String> authorNames,
            String editorialName,
            float price,
            int initialStock,
            String state,
            String genreName,
            String eduLevelName) {

        if (inventory == null) {
            throw new IllegalArgumentException(
                    "El inventario de libros no puede ser null.");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "El titulo del libro es obligatorio.");
        }

        if (authorNames == null || authorNames.isEmpty()) {
            throw new IllegalArgumentException(
                    "El libro debe tener al menos un autor.");
        }

        if (price < 0 || initialStock < 0) {
            throw new IllegalArgumentException(
                    "El precio y el stock inicial no pueden ser negativos.");
        }

        ArrayList<Author> authors = new ArrayList<>();

        for (String authorName : authorNames) {
            if (authorName != null && !authorName.isBlank()) {
                authors.add(new Author(authorName.trim()));
            }
        }

        if (authors.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe ingresar al menos un autor valido.");
        }

        Editorial editorial = new Editorial(editorialName);
        LiteraryGenre literaryGenre = new LiteraryGenre(genreName);
        EducationalLevel educationalLevel =
                new EducationalLevel(eduLevelName);

        Book book = new Book(
                title.trim(),
                authors,
                editorial,
                price,
                initialStock,
                state,
                literaryGenre,
                educationalLevel
        );

        inventory.add(book);
    }

    public static void addJournal(
            ArrayList<Journal> inventory,
            String name,
            String editorialName,
            LocalDate yearOfPublication,
            float price,
            int initialStock,
            String state) {

        if (inventory == null) {
            throw new IllegalArgumentException(
                    "El inventario de revistas no puede ser null.");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la revista es obligatorio.");
        }

        if (price < 0 || initialStock < 0) {
            throw new IllegalArgumentException(
                    "El precio y el stock inicial no pueden ser negativos.");
        }

        if (yearOfPublication == null) {
            throw new IllegalArgumentException(
                    "El anio de publicacion es obligatorio.");
        }

        Editorial editorial = new Editorial(editorialName);
        inventory.add(new Journal(name, editorial, yearOfPublication, price, 0, state));
    }
    
    //Clients
    public static Customer findOrCreateCustomer(String customerName, ArrayList<Customer> customers) {
        Customer customer = null;

        for (Customer existing : customers) {
            if (existing != null
                    && existing.getName() != null
                    && existing.getName().equalsIgnoreCase(name)) {

                customer = existing;
                break;
            }
        }

        if (customer == null) {
            customer = new Customer(name);
            customers.add(customer);
        }

        customer.addVisit(LocalDate.now());

        return customer;
    }

    public static Sale createSale(Customer customer, String typeSale) {
        return new Sale(LocalDate.now(), customer, typeSale);
    }

    public static boolean addBookToSale(
            Sale sale, Book book, int quantity) {

        if (sale == null || book == null || quantity <= 0) {
            return false;
        }

        if (book.getStock() < quantity) {
            return false;
        }

        SaleDetail detail = new SaleDetail(book, quantity);

        return addDetailToSale(sale, detail);
    }

    public static boolean addJournalToSale(
            Sale sale, Journal journal, int quantity) {

        if (sale == null || journal == null || quantity <= 0) {
            return false;
        }

        if (journal.getStock() < quantity) {
            return false;
        }

        SaleDetail detail = new SaleDetail(journal, quantity);

        return addDetailToSale(sale, detail);
    }

    private static boolean addDetailToSale(
            Sale sale, SaleDetail detail) {

        if (sale == null || detail == null) {
            return false;
        }

        if (!detail.validateProduct()) {
            return false;
        }

        sale.addDetail(detail);

        return true;
    }

    
public static boolean registerPayment(
        Sale sale,
        String paymentMethodName,
        float amountPaid,
        ArrayList<Sale> sales) {

    if (sale == null || sales == null
            || paymentMethodName == null
            || paymentMethodName.isBlank()
            || amountPaid < 0) {
        return false;
    }

    if (sale.getDetails() == null || sale.getDetails().isEmpty()) {
        return false;
    }

    float total = sale.computeTotal();

    if (total <= 0 || amountPaid < total) {
        return false;
    }

    // Validar todas las cantidades antes de modificar el stock.
    if (!hasEnoughStock(sale)) {
        return false;
    }

    PaymentMethod paymentMethod =
            new PaymentMethod(paymentMethodName);

    Payment payment = new Payment(amountPaid, paymentMethod);

    if (!payment.validatePayment(total)) {
        return false;
    }

    // Descontar el stock solamente después de validar el pago.
    for (SaleDetail detail : sale.getDetails()) {
        if (detail.getBook() != null) {
            Book book = detail.getBook();
            book.setStock(
                    book.getStock() - detail.getQuantityProduct()
            );
        } else if (detail.getJournal() != null) {
            Journal journal = detail.getJournal();
            journal.setStock(
                    journal.getStock() - detail.getQuantityProduct()
            );
        }
    }

    sale.setPayment(payment);
    sales.add(sale);

    return true;
}

private static boolean hasEnoughStock(Sale sale) {

    Map<Book, Integer> bookQuantities = new IdentityHashMap<>();
    Map<Journal, Integer> journalQuantities = new IdentityHashMap<>();

    for (SaleDetail detail : sale.getDetails()) {

        if (detail == null || detail.getQuantityProduct() <= 0) {
            return false;
        }

        if (detail.getBook() != null) {
            Book book = detail.getBook();

            int accumulated = bookQuantities.getOrDefault(book, 0);
            accumulated += detail.getQuantityProduct();

            if (accumulated > book.getStock()) {
                return false;
            }

            bookQuantities.put(book, accumulated);

        } else if (detail.getJournal() != null) {
            Journal journal = detail.getJournal();

            int accumulated = journalQuantities.getOrDefault(journal, 0);
            accumulated += detail.getQuantityProduct();

            if (accumulated > journal.getStock()) {
                return false;
            }

            journalQuantities.put(journal, accumulated);

        } else {
            return false;
        }
    }

    return true;
}


    public static float totalSalesByPeriod(
            LocalDate start,
            LocalDate end,
            ArrayList<Sale> sales) {

        if (start == null || end == null
                || sales == null || end.isBefore(start)) {
            return 0;
        }

        float totalSales = 0;

        for (Sale sale : sales) {
            if (sale != null
                    && sale.getDate() != null
                    && !sale.getDate().isBefore(start)
                    && !sale.getDate().isAfter(end)) {

                totalSales += sale.computeTotal();
            }
        }

        return totalSales;
    }

    public static Purchase createPurchase(String supplierName) {

        if (supplierName == null || supplierName.isBlank()) {
            supplierName = "Proveedor no identificado";
        }

        return new Purchase(
                LocalDate.now(),
                new Supplier(supplierName.trim())
        );
    }

    public static void addBookToPurchase(
            Purchase purchase,
            Book book,
            int quantity,
            float price) {

        validatePurchaseDetail(purchase, quantity, price);

        if (book == null) {
            throw new IllegalArgumentException(
                    "El libro no puede ser null.");
        }

        purchase.addDetailPurchase(
                new PurchaseDetail(quantity, price, book)
        );
    }

    public static void addJournalToPurchase(
            Purchase purchase,
            Journal journal,
            int quantity,
            float price) {

        validatePurchaseDetail(purchase, quantity, price);

        if (journal == null) {
            throw new IllegalArgumentException(
                    "La revista no puede ser null.");
        }

        purchase.addDetailPurchase(
                new PurchaseDetail(quantity, price, journal)
        );
    }

    public static void registerPurchase(Purchase purchase, ArrayList<Purchase> purchases) {
        purchases.add(purchase);
    }
}

