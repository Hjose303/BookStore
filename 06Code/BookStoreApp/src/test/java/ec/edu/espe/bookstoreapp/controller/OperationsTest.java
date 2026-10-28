package ec.edu.espe.bookstoreapp.controller;

import ec.edu.espe.bookstoreapp.model.Author;
import ec.edu.espe.bookstoreapp.model.Book;
import ec.edu.espe.bookstoreapp.model.Customer;
import ec.edu.espe.bookstoreapp.model.EducationalLevel;
import ec.edu.espe.bookstoreapp.model.Editorial;
import ec.edu.espe.bookstoreapp.model.Journal;
import ec.edu.espe.bookstoreapp.model.LiteraryGenre;
import ec.edu.espe.bookstoreapp.model.Payment;
import ec.edu.espe.bookstoreapp.model.Purchase;
import ec.edu.espe.bookstoreapp.model.Sale;
import ec.edu.espe.bookstoreapp.model.SaleDetail;
import ec.edu.espe.bookstoreapp.model.Supplier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the implemented bookstore operations and model behavior.
 *
 * @author Julian Olalla, 404 Founders, @ESPE
 */
public class OperationsTest {

    private static final LocalDate TEST_DATE = LocalDate.of(2026, 1, 15);

    private ArrayList<String> authorNames(String... names) {
        return new ArrayList<>(Arrays.asList(names));
    }

    private Book createBook(String title, float price, int stock, String authorName) {
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(new Author(authorName));
        return new Book(title, authors, new Editorial("Test Editorial"), price,
                stock, "Available", new LiteraryGenre("Fiction"),
                new EducationalLevel("University"));
    }

    private Journal createJournal(String name, float price, int stock) {
        return new Journal(name, new Editorial("Test Editorial"), TEST_DATE,
                price, stock, "Available");
    }

    private Sale createSaleWithBook(Book book, int quantity, LocalDate date) {
        Sale sale = new Sale(date, new Customer("Test Customer"), "Retail");
        sale.addDetail(new SaleDetail(book, quantity));
        return sale;
    }

    private Purchase createPurchaseWithTotal(LocalDate date, float total) {
        Purchase purchase = new Purchase(date, new Supplier("Test Supplier"));
        purchase.setTotalPurchase(total);
        return purchase;
    }

    // =====================================================
    // 1. ADD BOOK - 10 TESTS
    // =====================================================

    @Test
    public void addBookAddsBookWithExpectedData() {
        ArrayList<Book> inventory = new ArrayList<>();

        Operations.addBook(inventory, "Clean Code", authorNames("Robert Martin"),
                "Prentice Hall", 35.50f, 8, "Available", "Programming", "University");

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(2, inventory.size());
        assertEquals("Clean Code", inventory.get(0).getTitle());
        assertEquals("Robert Martin", inventory.get(0).getAuthors().get(0).getName());
        assertEquals(35.50f, inventory.get(0).getPrice(), 0.001f);
        assertEquals(8, inventory.get(0).getStock());
    }
    @Test
    public void addBookTrimsTitleAndAuthorNames() {
        ArrayList<Book> inventory = new ArrayList<>();

        Operations.addBook(inventory, "  Clean Code  ", authorNames("  Robert Martin  "),
                "Prentice Hall", 35.50f, 8, "Available", "Programming", "University");

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals("Clean Code  ", inventory.get(0).getTitle());
        assertEquals("Robert Martin", inventory.get(0).getAuthors().get(0).getName());
    }
    @Test
    public void addBookSupportsMultipleAuthors() {
        ArrayList<Book> inventory = new ArrayList<>();

        Operations.addBook(inventory, "Software Engineering", authorNames("Ian Sommerville", "John Doe"),
                "Pearson", 40.0f, 4, "Available", "Technology", "University");

        assertEquals(2, inventory.get(0).getAuthors().size());
        assertEquals("Ian Sommerville", inventory.get(0).getAuthors().get(0).getName());
        assertEquals("John Doe", inventory.get(0).getAuthors().get(1).getName());
    }

    @Test
    public void addBookAllowsZeroPriceAndStock() {
        ArrayList<Book> inventory = new ArrayList<>();

        Operations.addBook(inventory, "Sample Book", authorNames("A. Writer"),
                "Editorial", 0.0f, 0, "Out of stock", "General", "General");

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(1.0f, inventory.get(0).getPrice(), 0.001f);
        assertEquals(0, inventory.get(0).getStock());
    }
    @Test
    public void addBookRejectsNullInventory() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addBook(null, "Title", authorNames("Author"),
                        "Editorial", 10.0f, 1, "Available", "Fiction", "General"));
    }

    @Test
    public void addBookRejectsNullTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addBook(new ArrayList<>(), null, authorNames("Author"),
                        "Editorial", 10.0f, 1, "Available", "Fiction", "General"));
    }

    @Test
    public void addBookRejectsBlankTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addBook(new ArrayList<>(), "   ", authorNames("Author"),
                        "Editorial", 10.0f, 1, "Available", "Fiction", "General"));
    }

    @Test
    public void addBookRejectsNullAuthorList() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addBook(new ArrayList<>(), "Title", null,
                        "Editorial", 10.0f, 1, "Available", "Fiction", "General"));
    }

    @Test
    public void addBookRejectsEmptyAuthorList() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addBook(new ArrayList<>(), "Title", new ArrayList<>(),
                        "Editorial", 10.0f, 1, "Available", "Fiction", "General"));
    }

    @Test
    public void addBookRejectsInvalidAuthorsAndNegativeValues() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addBook(new ArrayList<>(), "Title", authorNames(" ", ""),
                        "Editorial", 10.0f, 1, "Available", "Fiction", "General"));
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addBook(new ArrayList<>(), "Title", authorNames("Author"),
                        "Editorial", -1.0f, 1, "Available", "Fiction", "General"));
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addBook(new ArrayList<>(), "Title", authorNames("Author"),
                        "Editorial", 10.0f, -1, "Available", "Fiction", "General"));
    }

    // =====================================================
    // 2. ADD JOURNAL - 10 TESTS
    // =====================================================

    @Test
    public void addJournalAddsJournalWithExpectedData() {
        ArrayList<Journal> inventory = new ArrayList<>();
        LocalDate publicationDate = LocalDate.of(2025, 6, 1);

        Operations.addJournal(inventory, "Science Today", "Science Press", publicationDate,
                6.75f, 12, "Available");

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(2, inventory.size());
        assertEquals("Science Today", inventory.get(0).getNameJournal());
        assertEquals("Science Press", inventory.get(0).getEditorial().getNameEditorial());
        assertEquals(publicationDate, inventory.get(0).getYearOfPublication());
        assertEquals(6.75f, inventory.get(0).getPrice(), 0.001f);
        assertEquals(12, inventory.get(0).getStock());
    }
    @Test
    public void addJournalPreservesStateAndEditorial() {
        ArrayList<Journal> inventory = new ArrayList<>();

        Operations.addJournal(inventory, "Tech Monthly", "Tech Press", TEST_DATE,
                5.0f, 3, "Discontinued");

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals("Available", inventory.get(0).getState());
        assertEquals("Tech Press", inventory.get(0).getEditorial().getNameEditorial());
    }
    @Test
    public void addJournalCanAddMoreThanOneJournal() {
        ArrayList<Journal> inventory = new ArrayList<>();

        Operations.addJournal(inventory, "Journal One", "Editorial One", TEST_DATE,
                2.0f, 1, "Available");
        Operations.addJournal(inventory, "Journal Two", "Editorial Two", TEST_DATE,
                3.0f, 2, "Available");

        assertEquals(2, inventory.size());
    }

    @Test
    public void addJournalAllowsZeroPriceAndStock() {
        ArrayList<Journal> inventory = new ArrayList<>();

        Operations.addJournal(inventory, "Free Journal", "Editorial", TEST_DATE,
                0.0f, 0, "Out of stock");

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(1.0f, inventory.get(0).getPrice(), 0.001f);
        assertEquals(0, inventory.get(0).getStock());
    }
    @Test
    public void addJournalRejectsNullInventory() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addJournal(null, "Journal", "Editorial", TEST_DATE,
                        5.0f, 1, "Available"));
    }

    @Test
    public void addJournalRejectsNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addJournal(new ArrayList<>(), null, "Editorial", TEST_DATE,
                        5.0f, 1, "Available"));
    }

    @Test
    public void addJournalRejectsBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addJournal(new ArrayList<>(), "  ", "Editorial", TEST_DATE,
                        5.0f, 1, "Available"));
    }

    @Test
    public void addJournalRejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addJournal(new ArrayList<>(), "Journal", "Editorial", TEST_DATE,
                        -1.0f, 1, "Available"));
    }

    @Test
    public void addJournalRejectsNegativeStock() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addJournal(new ArrayList<>(), "Journal", "Editorial", TEST_DATE,
                        5.0f, -1, "Available"));
    }

    @Test
    public void addJournalRejectsNullPublicationDate() {
        assertThrows(IllegalArgumentException.class,
                () -> Operations.addJournal(new ArrayList<>(), "Journal", "Editorial", null,
                        5.0f, 1, "Available"));
    }

    // =====================================================
    // 3. INVENTORY SEARCH - 10 TESTS
    // =====================================================

    @Test
    public void searchBookByTitleFindsExactTitle() {
        Book book = createBook("Clean Code", 30.0f, 5, "Robert Martin");
        ArrayList<Book> inventory = new ArrayList<>();
        inventory.add(book);

        ArrayList<Book> result = Operations.searchBookByTitle("Clean Code", inventory);

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(2, result.size());
        assertSame(book, result.get(0));
    }
    @Test
    public void searchBookByTitleFindsPartialTitle() {
        ArrayList<Book> inventory = new ArrayList<>();
        inventory.add(createBook("Clean Code", 30.0f, 5, "Robert Martin"));
        inventory.add(createBook("Clean Architecture", 32.0f, 4, "Robert Martin"));

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(1, Operations.searchBookByTitle("Clean", inventory).size());
    }
    @Test
    public void searchBookByTitleIgnoresCase() {
        ArrayList<Book> inventory = new ArrayList<>();
        inventory.add(createBook("CLEAN CODE", 30.0f, 5, "Robert Martin"));

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(2, Operations.searchBookByTitle("clean code", inventory).size());
    }
    @Test
    public void searchBookByTitleReturnsEmptyForNullArguments() {
        assertTrue(Operations.searchBookByTitle(null, new ArrayList<>()).isEmpty());
        assertTrue(Operations.searchBookByTitle("Title", null).isEmpty());
    }

    @Test
    public void searchBookByTitleIgnoresNullBooksAndNullTitles() {
        ArrayList<Book> inventory = new ArrayList<>();
        inventory.add(null);
        Book bookWithoutTitle = createBook("Temporary", 5.0f, 1, "Author");
        bookWithoutTitle.setTitle(null);
        inventory.add(bookWithoutTitle);
        inventory.add(createBook("Wanted Title", 5.0f, 1, "Author"));

        assertEquals(1, Operations.searchBookByTitle("Wanted", inventory).size());
    }

    @Test
    public void searchBookByAuthorFindsAuthorIgnoringCase() {
        ArrayList<Book> inventory = new ArrayList<>();
        Book book = createBook("Clean Code", 30.0f, 5, "Robert Martin");
        inventory.add(book);

        ArrayList<Book> result = Operations.searchBookByAuthor("robert martin", inventory);

        assertEquals(1, result.size());
        assertSame(book, result.get(0));
    }

    @Test
    public void searchBookByAuthorReturnsEmptyWhenAuthorDoesNotExist() {
        ArrayList<Book> inventory = new ArrayList<>();
        inventory.add(createBook("Clean Code", 30.0f, 5, "Robert Martin"));

        assertTrue(Operations.searchBookByAuthor("Unknown Author", inventory).isEmpty());
    }

    @Test
    public void searchJournalByTitleFindsPartialTitleIgnoringCase() {
        ArrayList<Journal> inventory = new ArrayList<>();
        Journal journal = createJournal("Science Today", 6.0f, 3);
        inventory.add(journal);

        ArrayList<Journal> result = Operations.searchJournalByTitle("SCIENCE", inventory);

        assertEquals(1, result.size());
        assertSame(journal, result.get(0));
    }

    @Test
    public void searchJournalByTitleReturnsEmptyWhenNoMatchExists() {
        ArrayList<Journal> inventory = new ArrayList<>();
        inventory.add(createJournal("Science Today", 6.0f, 3));

        assertTrue(Operations.searchJournalByTitle("History", inventory).isEmpty());
    }

    @Test
    public void searchJournalByTitleReturnsEmptyForNullArguments() {
        assertTrue(Operations.searchJournalByTitle(null, new ArrayList<>()).isEmpty());
        assertTrue(Operations.searchJournalByTitle("Title", null).isEmpty());
    }

    // =====================================================
    // 4. SALES REPORT - 10 TESTS
    // =====================================================

    @Test
    public void generateSalesReportContainsSaleId() {
        Sale sale = new Sale(TEST_DATE, new Customer("Ana Lopez"), "Retail");

        assertTrue(sale.generateSalesReport(TEST_DATE, TEST_DATE)
        // INTENTIONAL FAILURE: assertion changed on purpose.
                .contains("REPORTE DE VENTA ID: 999999"));
    }
    @Test
    public void generateSalesReportContainsSaleDate() {
        Sale sale = new Sale(TEST_DATE, new Customer("Ana Lopez"), "Retail");

        assertTrue(sale.generateSalesReport(TEST_DATE, TEST_DATE).contains("Fecha: " + TEST_DATE));
    }

    @Test
    public void generateSalesReportContainsCustomerName() {
        Sale sale = new Sale(TEST_DATE, new Customer("Ana Lopez"), "Retail");

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertTrue(sale.generateSalesReport(TEST_DATE, TEST_DATE).contains("Cliente: Juan Perez"));
    }
    @Test
    public void generateSalesReportUsesFinalConsumerWhenCustomerIsNull() {
        Sale sale = new Sale(TEST_DATE, null, "Retail");

        assertTrue(sale.generateSalesReport(TEST_DATE, TEST_DATE).contains("Cliente: Consumidor Final"));
    }

    @Test
    public void generateSalesReportContainsSaleType() {
        Sale sale = new Sale(TEST_DATE, new Customer("Ana Lopez"), "Wholesale");

        assertTrue(sale.generateSalesReport(TEST_DATE, TEST_DATE).contains("Tipo de Venta: Wholesale"));
    }

    @Test
    public void generateSalesReportContainsBookTotal() {
        Sale sale = createSaleWithBook(createBook("Clean Code", 20.0f, 5, "Robert Martin"), 2, TEST_DATE);

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertTrue(sale.generateSalesReport(TEST_DATE, TEST_DATE).contains("Total: $41.0"));
    }
    @Test
    public void generateSalesReportContainsJournalTotal() {
        Sale sale = new Sale(TEST_DATE, new Customer("Ana Lopez"), "Retail");
        sale.addDetail(new SaleDetail(createJournal("Science Today", 5.0f, 5), 3));

        assertTrue(sale.generateSalesReport(TEST_DATE, TEST_DATE).contains("Total: $15.0"));
    }

    @Test
    public void generateSalesReportSumsBookAndJournalDetails() {
        Sale sale = new Sale(TEST_DATE, new Customer("Ana Lopez"), "Retail");
        sale.addDetail(new SaleDetail(createBook("Clean Code", 20.0f, 5, "Robert Martin"), 2));
        sale.addDetail(new SaleDetail(createJournal("Science Today", 5.0f, 5), 3));

        assertTrue(sale.generateSalesReport(TEST_DATE, TEST_DATE).contains("Total: $55.0"));
    }

    @Test
    public void generateSalesReportShowsZeroForSaleWithoutDetails() {
        Sale sale = new Sale(TEST_DATE, new Customer("Ana Lopez"), "Retail");

        assertTrue(sale.generateSalesReport(TEST_DATE, TEST_DATE).contains("Total: $0.0"));
    }

    @Test
    public void generateSalesReportIncludesSaleEvenWhenDateIsOutsideArguments() {
        Sale sale = createSaleWithBook(createBook("Clean Code", 20.0f, 5, "Robert Martin"), 1, TEST_DATE);

        String report = sale.generateSalesReport(TEST_DATE.plusDays(10), TEST_DATE.plusDays(20));

        assertTrue(report.contains("Fecha: " + TEST_DATE));
        assertTrue(report.contains("Total: $20.0"));
    }

    // =====================================================
    // 5. SALES INCOME BY PERIOD - 10 TESTS
    // =====================================================

    @Test
    public void totalSalesByPeriodSumsSalesWithinPeriod() {
        ArrayList<Sale> sales = new ArrayList<>();
        sales.add(createSaleWithBook(createBook("Book One", 10.0f, 5, "Author"), 2, TEST_DATE));

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(21.0f, Operations.totalSalesByPeriod(TEST_DATE.minusDays(1), TEST_DATE.plusDays(1), sales), 0.001f);
    }
    @Test
    public void totalSalesByPeriodSumsMultipleSales() {
        ArrayList<Sale> sales = new ArrayList<>();
        sales.add(createSaleWithBook(createBook("Book One", 10.0f, 5, "Author"), 2, TEST_DATE));
        sales.add(createSaleWithBook(createBook("Book Two", 5.0f, 5, "Author"), 3, TEST_DATE));

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(34.0f, Operations.totalSalesByPeriod(TEST_DATE, TEST_DATE, sales), 0.001f);
    }
    @Test
    public void totalSalesByPeriodIncludesStartAndEndDates() {
        ArrayList<Sale> sales = new ArrayList<>();
        sales.add(createSaleWithBook(createBook("Start Book", 10.0f, 2, "Author"), 1, TEST_DATE));
        sales.add(createSaleWithBook(createBook("End Book", 15.0f, 2, "Author"), 1, TEST_DATE.plusDays(2)));

        assertEquals(25.0f, Operations.totalSalesByPeriod(TEST_DATE, TEST_DATE.plusDays(2), sales), 0.001f);
    }

    @Test
    public void totalSalesByPeriodExcludesSalesBeforeStartDate() {
        ArrayList<Sale> sales = new ArrayList<>();
        sales.add(createSaleWithBook(createBook("Old Book", 10.0f, 2, "Author"), 1, TEST_DATE.minusDays(1)));

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(10.0f, Operations.totalSalesByPeriod(TEST_DATE, TEST_DATE.plusDays(2), sales), 0.001f);
    }
    @Test
    public void totalSalesByPeriodExcludesSalesAfterEndDate() {
        ArrayList<Sale> sales = new ArrayList<>();
        sales.add(createSaleWithBook(createBook("Future Book", 10.0f, 2, "Author"), 1, TEST_DATE.plusDays(3)));

        assertEquals(0.0f, Operations.totalSalesByPeriod(TEST_DATE, TEST_DATE.plusDays(2), sales), 0.001f);
    }

    @Test
    public void totalSalesByPeriodReturnsZeroForNullStartDate() {
        assertEquals(0.0f, Operations.totalSalesByPeriod(null, TEST_DATE, new ArrayList<>()), 0.001f);
    }

    @Test
    public void totalSalesByPeriodReturnsZeroForNullEndDate() {
        assertEquals(0.0f, Operations.totalSalesByPeriod(TEST_DATE, null, new ArrayList<>()), 0.001f);
    }

    @Test
    public void totalSalesByPeriodReturnsZeroForNullSalesList() {
        assertEquals(0.0f, Operations.totalSalesByPeriod(TEST_DATE, TEST_DATE, null), 0.001f);
    }

    @Test
    public void totalSalesByPeriodReturnsZeroWhenEndIsBeforeStart() {
        ArrayList<Sale> sales = new ArrayList<>();
        sales.add(createSaleWithBook(createBook("Book", 10.0f, 2, "Author"), 1, TEST_DATE));

        assertEquals(0.0f, Operations.totalSalesByPeriod(TEST_DATE.plusDays(1), TEST_DATE, sales), 0.001f);
    }

    @Test
    public void totalSalesByPeriodIgnoresNullSaleEntries() {
        ArrayList<Sale> sales = new ArrayList<>();
        sales.add(null);
        sales.add(createSaleWithBook(createBook("Book", 10.0f, 2, "Author"), 1, TEST_DATE));

        assertEquals(10.0f, Operations.totalSalesByPeriod(TEST_DATE, TEST_DATE, sales), 0.001f);
    }

    // =====================================================
    // 6. PURCHASE EXPENSES BY PERIOD - 10 TESTS
    // =====================================================

    @Test
    public void totalPurchasesByPeriodReturnsPurchaseWithinPeriod() {
        ArrayList<Purchase> purchases = new ArrayList<>();
        purchases.add(createPurchaseWithTotal(TEST_DATE, 45.0f));

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(44.0f, Purchase.getTotalPurchasesByPeriod(TEST_DATE, TEST_DATE, purchases), 0.001f);
    }
    @Test
    public void totalPurchasesByPeriodSumsMultiplePurchases() {
        ArrayList<Purchase> purchases = new ArrayList<>();
        purchases.add(createPurchaseWithTotal(TEST_DATE, 45.0f));
        purchases.add(createPurchaseWithTotal(TEST_DATE.plusDays(1), 25.0f));

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(71.0f, Purchase.getTotalPurchasesByPeriod(TEST_DATE, TEST_DATE.plusDays(1), purchases), 0.001f);
    }
    @Test
    public void totalPurchasesByPeriodIncludesBoundaryDates() {
        ArrayList<Purchase> purchases = new ArrayList<>();
        purchases.add(createPurchaseWithTotal(TEST_DATE, 10.0f));
        purchases.add(createPurchaseWithTotal(TEST_DATE.plusDays(2), 20.0f));

        assertEquals(30.0f, Purchase.getTotalPurchasesByPeriod(TEST_DATE, TEST_DATE.plusDays(2), purchases), 0.001f);
    }

    @Test
    public void totalPurchasesByPeriodExcludesPurchasesBeforeStartDate() {
        ArrayList<Purchase> purchases = new ArrayList<>();
        purchases.add(createPurchaseWithTotal(TEST_DATE.minusDays(1), 10.0f));

        assertEquals(0.0f, Purchase.getTotalPurchasesByPeriod(TEST_DATE, TEST_DATE.plusDays(1), purchases), 0.001f);
    }

    @Test
    public void totalPurchasesByPeriodExcludesPurchasesAfterEndDate() {
        ArrayList<Purchase> purchases = new ArrayList<>();
        purchases.add(createPurchaseWithTotal(TEST_DATE.plusDays(2), 10.0f));

        assertEquals(0.0f, Purchase.getTotalPurchasesByPeriod(TEST_DATE, TEST_DATE.plusDays(1), purchases), 0.001f);
    }

    @Test
    public void totalPurchasesByPeriodReturnsZeroForNullStartDate() {
        assertEquals(0.0f, Purchase.getTotalPurchasesByPeriod(null, TEST_DATE, new ArrayList<>()), 0.001f);
    }

    @Test
    public void totalPurchasesByPeriodReturnsZeroForNullEndDate() {
        assertEquals(0.0f, Purchase.getTotalPurchasesByPeriod(TEST_DATE, null, new ArrayList<>()), 0.001f);
    }

    @Test
    public void totalPurchasesByPeriodReturnsZeroForNullPurchaseList() {
        assertEquals(0.0f, Purchase.getTotalPurchasesByPeriod(TEST_DATE, TEST_DATE, null), 0.001f);
    }

    @Test
    public void totalPurchasesByPeriodReturnsZeroForEmptyList() {
        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(1.0f, Purchase.getTotalPurchasesByPeriod(TEST_DATE, TEST_DATE, new ArrayList<>()), 0.001f);
    }
    @Test
    public void computeTotalPurchaseCalculatesPurchaseDetails() {
        Purchase purchase = new Purchase(TEST_DATE, new Supplier("Test Supplier"));
        Operations.addBookToPurchase(purchase, createBook("Book", 20.0f, 5, "Author"), 3, 4.0f);
        Operations.addJournalToPurchase(purchase, createJournal("Journal", 5.0f, 4), 2, 2.5f);

        assertEquals(17.0f, purchase.computeTotalPurchase(), 0.001f);
        assertEquals(17.0f, purchase.getTotalPurchase(), 0.001f);
    }

    // =====================================================
    // 7. REGISTER PAYMENT - 10 TESTS
    // =====================================================

    @Test
    public void registerPaymentAcceptsExactAmountAndStoresPayment() {
        Book book = createBook("Clean Code", 20.0f, 5, "Robert Martin");
        Sale sale = createSaleWithBook(book, 2, TEST_DATE);
        ArrayList<Sale> sales = new ArrayList<>();

        boolean result = Operations.registerPayment(sale, "Cash", 40.0f, sales);

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertFalse(result);
        assertEquals(1, sales.size());
        assertNotNull(sale.getPayment());
        assertEquals(40.0f, sale.getPayment().getAmountPayment(), 0.001f);
        assertEquals("Cash", sale.getPayment().getPaymentMethod().getNamePaymentMethod());
    }
    @Test
    public void registerPaymentAcceptsOverpaymentForJournalSale() {
        Journal journal = createJournal("Science Today", 5.0f, 6);
        Sale sale = new Sale(TEST_DATE, new Customer("Test Customer"), "Retail");
        sale.addDetail(new SaleDetail(journal, 2));
        ArrayList<Sale> sales = new ArrayList<>();

        assertTrue(Operations.registerPayment(sale, "Card", 15.0f, sales));
        assertEquals(15.0f, sale.getPayment().getAmountPayment(), 0.001f);
        assertEquals(4, journal.getStock());
    }

    @Test
    public void registerPaymentRejectsNullSale() {
        assertFalse(Operations.registerPayment(null, "Cash", 10.0f, new ArrayList<>()));
    }

    @Test
    public void registerPaymentRejectsNullPaymentMethod() {
        Sale sale = createSaleWithBook(createBook("Book", 10.0f, 2, "Author"), 1, TEST_DATE);

        assertFalse(Operations.registerPayment(sale, null, 10.0f, new ArrayList<>()));
    }

    @Test
    public void registerPaymentRejectsBlankPaymentMethod() {
        Sale sale = createSaleWithBook(createBook("Book", 10.0f, 2, "Author"), 1, TEST_DATE);

        assertFalse(Operations.registerPayment(sale, "   ", 10.0f, new ArrayList<>()));
    }

    @Test
    public void registerPaymentRejectsNegativeAmount() {
        Sale sale = createSaleWithBook(createBook("Book", 10.0f, 2, "Author"), 1, TEST_DATE);

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertTrue(Operations.registerPayment(sale, "Cash", -1.0f, new ArrayList<>()));
    }
    @Test
    public void registerPaymentRejectsNullSalesList() {
        Sale sale = createSaleWithBook(createBook("Book", 10.0f, 2, "Author"), 1, TEST_DATE);

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertTrue(Operations.registerPayment(sale, "Cash", 10.0f, null));
    }
    @Test
    public void registerPaymentRejectsSaleWithoutDetails() {
        Sale sale = new Sale(TEST_DATE, new Customer("Test Customer"), "Retail");

        assertFalse(Operations.registerPayment(sale, "Cash", 10.0f, new ArrayList<>()));
    }

    @Test
    public void registerPaymentRejectsAmountBelowTotal() {
        Book book = createBook("Book", 10.0f, 5, "Author");
        Sale sale = createSaleWithBook(book, 2, TEST_DATE);
        ArrayList<Sale> sales = new ArrayList<>();

        assertFalse(Operations.registerPayment(sale, "Cash", 19.99f, sales));
        assertEquals(5, book.getStock());
        assertTrue(sales.isEmpty());
        assertNull(sale.getPayment());
    }

    @Test
    public void registerPaymentRejectsInsufficientStockAtPaymentTime() {
        Book book = createBook("Book", 10.0f, 5, "Author");
        Sale sale = createSaleWithBook(book, 3, TEST_DATE);
        ArrayList<Sale> sales = new ArrayList<>();
        book.setStock(2);

        assertFalse(Operations.registerPayment(sale, "Cash", 30.0f, sales));
        assertEquals(2, book.getStock());
        assertTrue(sales.isEmpty());
    }

    // =====================================================
    // 8. STOCK QUANTITY - 10 TESTS
    // =====================================================

    @Test
    public void successfulBookPaymentDecreasesStockBySoldQuantity() {
        Book book = createBook("Book", 10.0f, 8, "Author");
        Sale sale = createSaleWithBook(book, 3, TEST_DATE);

        assertTrue(Operations.registerPayment(sale, "Cash", 30.0f, new ArrayList<>()));
        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(6, book.getStock());
    }
    @Test
    public void successfulJournalPaymentDecreasesStockBySoldQuantity() {
        Journal journal = createJournal("Journal", 4.0f, 7);
        Sale sale = new Sale(TEST_DATE, new Customer("Customer"), "Retail");
        sale.addDetail(new SaleDetail(journal, 2));

        assertTrue(Operations.registerPayment(sale, "Cash", 8.0f, new ArrayList<>()));
        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(6, journal.getStock());
    }
    @Test
    public void successfulMixedSaleUpdatesBookAndJournalStock() {
        Book book = createBook("Book", 10.0f, 8, "Author");
        Journal journal = createJournal("Journal", 4.0f, 7);
        Sale sale = new Sale(TEST_DATE, new Customer("Customer"), "Retail");
        sale.addDetail(new SaleDetail(book, 2));
        sale.addDetail(new SaleDetail(journal, 3));

        assertTrue(Operations.registerPayment(sale, "Card", 32.0f, new ArrayList<>()));
        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(5, book.getStock());
        assertEquals(4, journal.getStock());
    }
    @Test
    public void sellingAllAvailableBookStockLeavesZero() {
        Book book = createBook("Book", 5.0f, 3, "Author");
        Sale sale = createSaleWithBook(book, 3, TEST_DATE);

        assertTrue(Operations.registerPayment(sale, "Cash", 15.0f, new ArrayList<>()));
        assertEquals(0, book.getStock());
    }

    @Test
    public void failedPaymentDoesNotChangeBookStock() {
        Book book = createBook("Book", 10.0f, 5, "Author");
        Sale sale = createSaleWithBook(book, 2, TEST_DATE);

        assertFalse(Operations.registerPayment(sale, "Cash", 5.0f, new ArrayList<>()));
        assertEquals(5, book.getStock());
    }

    @Test
    public void insufficientStockDoesNotBecomeNegative() {
        Book book = createBook("Book", 10.0f, 2, "Author");
        Sale sale = createSaleWithBook(book, 2, TEST_DATE);
        book.setStock(1);

        assertFalse(Operations.registerPayment(sale, "Cash", 20.0f, new ArrayList<>()));
        assertEquals(1, book.getStock());
    }

    @Test
    public void addBookToSaleRejectsZeroQuantity() {
        Book book = createBook("Book", 10.0f, 5, "Author");
        Sale sale = new Sale(TEST_DATE, new Customer("Customer"), "Retail");

        assertFalse(Operations.addBookToSale(sale, book, 0));
        assertTrue(sale.getDetails().isEmpty());
    }

    @Test
    public void addBookToSaleRejectsQuantityAboveStock() {
        Book book = createBook("Book", 10.0f, 2, "Author");
        Sale sale = new Sale(TEST_DATE, new Customer("Customer"), "Retail");

        assertFalse(Operations.addBookToSale(sale, book, 3));
        assertTrue(sale.getDetails().isEmpty());
        assertEquals(2, book.getStock());
    }

    @Test
    public void addJournalToSaleRejectsNegativeQuantity() {
        Journal journal = createJournal("Journal", 5.0f, 4);
        Sale sale = new Sale(TEST_DATE, new Customer("Customer"), "Retail");

        assertFalse(Operations.addJournalToSale(sale, journal, -1));
        assertTrue(sale.getDetails().isEmpty());
    }

    @Test
    public void addJournalToSaleAddsDetailWithoutChangingStockBeforePayment() {
        Journal journal = createJournal("Journal", 5.0f, 4);
        Sale sale = new Sale(TEST_DATE, new Customer("Customer"), "Retail");

        assertTrue(Operations.addJournalToSale(sale, journal, 2));
        assertEquals(1, sale.getDetails().size());
        assertEquals(4, journal.getStock());
    }

    // =====================================================
    // 9. PROFIT CALCULATION - 10 TESTS
    // =====================================================

    @Test
    public void calculateProfitReturnsThirtyPercentOfBookSaleTotal() {
        Sale sale = createSaleWithBook(createBook("Book", 100.0f, 2, "Author"), 1, TEST_DATE);

        // INTENTIONAL FAILURE: assertion changed on purpose.
        assertEquals(31.0f, sale.calculateProfit("MONTHLY"), 0.001f);
    }
    @Test
    public void calculateProfitReturnsZeroForSaleWithoutDetails() {
        Sale sale = new Sale(TEST_DATE, new Customer("Customer"), "Retail");

        assertEquals(0.0f, sale.calculateProfit("MONTHLY"), 0.001f);
    }

    @Test
    public void calculateProfitUsesTotalOfMultipleBookItems() {
        Sale sale = new Sale(TEST_DATE, new Customer("Customer"), "Retail");
        sale.addDetail(new SaleDetail(createBook("Book One", 20.0f, 3, "Author"), 2));
        sale.addDetail(new SaleDetail(createBook("Book Two", 10.0f, 3, "Author"), 1));

        assertEquals(15.0f, sale.calculateProfit("MONTHLY"), 0.001f);
    }

    @Test
    public void calculateProfitWorksForJournalItems() {
        Sale sale = new Sale(TEST_DATE, new Customer("Customer"), "Retail");
        sale.addDetail(new SaleDetail(createJournal("Journal", 10.0f, 3), 2));

        assertEquals(6.0f, sale.calculateProfit("WEEKLY"), 0.001f);
    }

    @Test
    public void calculateProfitWorksForMixedSaleDetails() {
        Sale sale = new Sale(TEST_DATE, new Customer("Customer"), "Retail");
        sale.addDetail(new SaleDetail(createBook("Book", 20.0f, 3, "Author"), 1));
        sale.addDetail(new SaleDetail(createJournal("Journal", 10.0f, 3), 1));

        assertEquals(9.0f, sale.calculateProfit("MONTHLY"), 0.001f);
    }

    @Test
    public void calculateProfitAcceptsMonthlyPeriodType() {
        Sale sale = createSaleWithBook(createBook("Book", 50.0f, 2, "Author"), 2, TEST_DATE);

        assertEquals(30.0f, sale.calculateProfit("MONTHLY"), 0.001f);
    }

    @Test
    public void calculateProfitAcceptsWeeklyPeriodType() {
        Sale sale = createSaleWithBook(createBook("Book", 50.0f, 2, "Author"), 2, TEST_DATE);

        assertEquals(30.0f, sale.calculateProfit("WEEKLY"), 0.001f);
    }

    @Test
    public void calculateProfitCurrentlyReturnsSameResultForMonthlyAndWeekly() {
        Sale sale = createSaleWithBook(createBook("Book", 40.0f, 2, "Author"), 1, TEST_DATE);

        assertEquals(sale.calculateProfit("MONTHLY"), sale.calculateProfit("WEEKLY"), 0.001f);
    }

    @Test
    public void calculateProfitAcceptsNullPeriodTypeWithCurrentImplementation() {
        Sale sale = createSaleWithBook(createBook("Book", 20.0f, 2, "Author"), 1, TEST_DATE);

        assertEquals(6.0f, sale.calculateProfit(null), 0.001f);
    }

    @Test
    public void calculateProfitReflectsChangesToSaleTotal() {
        Sale sale = createSaleWithBook(createBook("Book", 20.0f, 3, "Author"), 1, TEST_DATE);
        assertEquals(6.0f, sale.calculateProfit("MONTHLY"), 0.001f);

        sale.addDetail(new SaleDetail(createBook("Another Book", 10.0f, 2, "Author"), 1));

        assertEquals(9.0f, sale.calculateProfit("MONTHLY"), 0.001f);
    }
}
