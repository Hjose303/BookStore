package ec.edu.espe.bookstoreapp.view;

import ec.edu.espe.bookstoreapp.controller.Operations;
import ec.edu.espe.bookstoreapp.exception.EmptyInventoryException;
import ec.edu.espe.bookstoreapp.exception.InvalidValueException;
import ec.edu.espe.bookstoreapp.model.Book;
import ec.edu.espe.bookstoreapp.model.Customer;
import ec.edu.espe.bookstoreapp.model.Journal;
import ec.edu.espe.bookstoreapp.model.Purchase;
import ec.edu.espe.bookstoreapp.model.Sale;
import ec.edu.espe.bookstoreapp.model.JsonPersistence;
import ec.edu.espe.bookstoreapp.model.InvoiceCsvPersistence;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class View {

    private static final Scanner input = new Scanner(System.in);

    public static void showMenu() {
        System.out.println("""
            =====================================================
                    LIBRERIA - OLIVO CULTURAL
            =====================================================
            1. Consultar libros en existencia
            2. Consultar revistas en existencia
            3. Realizar una nueva venta
            4. Agregar productos o registrar una compra
            5. Buscar libros
            6. Buscar revistas
            7. Estado economico
            8. Frecuencia de clientes
            9. Salir
            =====================================================
            """);
    }

    public static int askMenuOption() {
        System.out.print("Seleccione una accion: ");
        return Integer.parseInt(input.nextLine());
    }

    public static String askRetry() {
        System.out.print("¿Desea volver a intentar? (si/no): ");
        return input.nextLine();
    }

    private static int askInt(String prompt, int min, int max, String fieldName) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(input.nextLine());
                Operations.requireInRange(value, min, max, fieldName);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: ingrese un numero entero valido.");
            } catch (InvalidValueException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static int askPositiveInt(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(input.nextLine());
                Operations.requirePositive(value, fieldName);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: ingrese un numero entero valido.");
            } catch (InvalidValueException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static float askPositiveFloat(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            try {
                float value = Float.parseFloat(input.nextLine());
                Operations.requirePositive(value, fieldName);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: ingrese un numero valido.");
            } catch (InvalidValueException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static float askNonNegativeFloat(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            try {
                float value = Float.parseFloat(input.nextLine());
                Operations.requireNonNegative(value, fieldName);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: ingrese un numero valido.");
            } catch (InvalidValueException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static LocalDate askDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(input.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: ingrese una fecha valida con el formato AAAA-MM-DD.");
            }
        }
    }

    public static void showChoseOption(
            int opc,
            ArrayList<Book> books,
            ArrayList<Journal> journals,
            ArrayList<Purchase> purchases,
            ArrayList<Customer> customers,
            ArrayList<Sale> sales) {

        switch (opc) {
            case 1 ->
                checkAvailableBooks(books);
            case 2 ->
                checkAvailableJounaries(journals);
            case 3 ->
                makeANewSale(books, journals, customers, sales);
            case 4 ->
                AddNewProducts(books, journals, purchases);
            case 5 ->
                searchBooks(books);
            case 6 ->
                searchJournals(journals);
            case 7 ->
                checkEconomicStatus(purchases, sales);
            case 8 ->
                checkClientFrequency(customers);
            case 9 -> {
                saveInventory(books, journals, purchases, customers, sales);
                System.out.println("Datos guardados. Hasta pronto.");
                System.exit(0);
            }
            default ->
                System.out.println("Opcion no valida.");
        }
    }

    public static void checkAvailableBooks(ArrayList<Book> inventory) {
        System.out.println("\n========== LIBROS EN INVENTARIO ==========");

        if (inventory.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        for (Book book : inventory) {
            if (book.validateStock()) {
                System.out.println("ID: " + book.getIdBook() + " | Título: " + book.getTitle() 
                        + " | Precio: $" + book.getPrice() + " | Stock: " + book.getStock());
            }
        }

        System.out.println("Total de libros: " + inventory.size());
    }

    public static void checkAvailableJounaries(
            ArrayList<Journal> inventory) {

        System.out.println("\n========== REVISTAS EN INVENTARIO ==========");

        if (inventory.isEmpty()) {
            System.out.println("No hay revistas registradas.");
            return;
        }

        for (Journal journal : inventory) {
            System.out.println(
                    "ID: " + journal.getIdJournal()
                    + " | Nombre: " + journal.getNameJournal()
                    + " | Precio: $" + journal.getPrice()
                    + " | Stock: " + journal.getStock()
                    + " | Estado: "
                    + (journal.getStock() > 0 ? "Disponible" : "Agotada")
            );
        }

        System.out.println("Total de revistas: " + inventory.size());
    }

    public static void makeANewSale(
            ArrayList<Book> books,
            ArrayList<Journal> journals,
            ArrayList<Customer> customers,
            ArrayList<Sale> sales) {

        System.out.println("\n========== NUEVA VENTA ==========");

        Customer customer = askCustomer(customers);

        // 2. Método de Pago
        String paymentMethodName = askPaymentMethodName();

        // 3. Crear Venta temporal
        Sale sale = Operations.createSale(customer,askSaleType());

        // 4. Agregar Productos a la Venta
        addProductsToSale(sale, inventoryBooks, inventoryJournals);

        // 5. Procesar Pago
        processPayment(sale, paymentMethodName, sales);
    }

    private static Customer askCustomer(ArrayList<Customer> customers) {
        System.out.print("Ingrese el Nombre del Cliente: ");
        String customerName = input.nextLine();       
        return Operations.findOrCreateCustomer(customerName, customers);
    }

    private static String askPaymentMethodName() {
        System.out.print("Ingrese el Método de Pago (Efectivo/Tarjeta/Transferencia): ");        
        return input.nextLine();
    }
    
    private static String askSaleType() {
        System.out.print("Ingrese el Tipo de Venta (Factura/Nota de Venta): ");
        return input.nextLine();
    }                    

    private static void addProductsToSale(
            Sale sale,
            ArrayList<Book> books,
            ArrayList<Journal> journals) {

        String again = "s";

        while (again.equalsIgnoreCase("s")) {
            System.out.println("\n1. Libro");
            System.out.println("2. Revista");
            System.out.print("Opción: ");
            int optionProduct = Integer.parseInt(input.nextLine());

            if (option == 1) {
                addBookToSale(sale, books);
            } else if (option == 2) {
                addJournalToSale(sale, journals);
            } else {
                System.out.println("Opcion no valida.");
            }

            System.out.print("¿Desea agregar otro producto a esta venta? (s/n): ");
            String continueOpt = input.nextLine();
            if (!continueOpt.equalsIgnoreCase("s")) {
                addingProducts = false;
            }
        }
    }

    private static void addBookToSale(
            Sale sale, ArrayList<Book> inventory) {

        if (inventory.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        for (int i = 0; i < inventory.size(); i++) {
            Book book = inventory.get(i);
            System.out.println(
                    i + ". " + book.getTitle()
                    + " | Precio: $" + book.getPrice()
                    + " | Stock: " + book.getStock()
            );
        }
        System.out.print("Número del libro: ");
        int pos = Integer.parseInt(input.nextLine());

        if (pos >= 0 && pos < inventoryBooks.size()) {
            Book selectedBook = inventoryBooks.get(pos);
            System.out.print("Ingrese la cantidad a vender: ");
            int qty = Integer.parseInt(input.nextLine());

            if (Operations.addBookToSale(sale, selectedBook, qty)) {
                System.out.println("¡Libro agregado a la venta!");
            } else {
                System.out.println("ERROR: Stock insuficiente o cantidad no válida.");
            }
        }
    }

    private static void addJournalToSale(
            Sale sale, ArrayList<Journal> inventory) {

        if (inventory.isEmpty()) {
            System.out.println("No hay revistas registradas.");
            return;
        }

        for (int i = 0; i < inventory.size(); i++) {
            Journal journal = inventory.get(i);
            System.out.println(
                    i + ". " + journal.getNameJournal()
                    + " | Precio: $" + journal.getPrice()
                    + " | Stock: " + journal.getStock()
            );
        }
        System.out.print("Número de la revista: ");
        int pos = Integer.parseInt(input.nextLine());

        if (pos >= 0 && pos < inventoryJournals.size()) {
            Journal selectedJournal = inventoryJournals.get(pos);
            System.out.print("Ingrese la cantidad a vender: ");
            int qty = Integer.parseInt(input.nextLine());

            if (Operations.addJournalToSale(sale, selectedJournal, qty)) {                
                System.out.println("¡Revista agregada a la venta!");
            } else {
                System.out.println("ERROR: Stock insuficiente o cantidad no válida.");
            }
        }
    }

    private static void processPayment(Sale sale, String paymentMethodName, ArrayList<Sale> sales) {
        float totalSale = sale.computeTotal();
        System.out.println("\nTotal de la venta: $" + totalSale);
        System.out.print("Ingrese el monto abonado por el cliente $: ");
        float amountPaid = Float.parseFloat(input.nextLine());

        if (Operations.registerPayment(sale, paymentMethodName, amountPaid, sales)) {
            float cambio = amountPaid - totalSale;
            System.out.println("==========================================");
            System.out.println("¡Venta procesada con éxito!");
            System.out.println("Cambio a entregar: $" + cambio);
            System.out.println(sale.generateSalesReport(LocalDate.now(), LocalDate.now()));
            System.out.println("==========================================");
        } else {
            System.out.println("Stock insuficiente o cantidad no valida.");
        }
    }

    public static void AddNewProducts(
            ArrayList<Book> books,
            ArrayList<Journal> journals,
            ArrayList<Purchase> purchases) {

        System.out.println("\n========== AGREGAR PRODUCTOS ==========");
        System.out.println("1. Agregar libro");
        System.out.println("2. Agregar revista");
        System.out.println("3. Registrar compra");
        System.out.print("Seleccione una opcion: ");

        int option = Integer.parseInt(input.nextLine());

        switch (option) {
            case 1 ->
                addBooks(books);
            case 2 ->
                addJournals(journals);
            case 3 ->
                addPurchase(books, journals, purchases);
            default ->
                System.out.println("Opcion no valida.");
        }
    }

    public static void addBooks(ArrayList<Book> inventory) {
        String title;
        String authorName;
        String option;
        String editorialName;
        String state;
        String eduLevel;
        String Genre;
        float price;
        ArrayList<String> authorNames = new ArrayList<>();        

        System.out.println("Agregar Libros: ");
        System.out.println("========================================");
        System.out.print("Ingrese el Titulo del Libro:  ");
        title = input.nextLine();
        do {
            System.out.print("Ingrese el Nombre del Autor: ");
            authorName = input.nextLine();
            authorNames.add(authorName);
            System.out.print("¿Desea agregar otro autor para este libro? (s/n): ");
            option = input.nextLine();
        } while (option.equalsIgnoreCase("s"));
        System.out.print("Ingrese el Nombre de la Editorial: ");
        editorialName = input.nextLine();        
        System.out.print("Ingrese el Precio $: ");
        price = Float.parseFloat(input.nextLine());
        System.out.print("Ingrese el Estado del Libro: ");
        state = input.nextLine();
        System.out.println("Ingrese el Genero Literario: ");
        Genre = input.nextLine();        
        System.out.print("Ingrese el Nivel Educativo del Libro: ");
        eduLevel = input.nextLine();        
        Operations.addBook(inventory, title, authorNames, editorialName, price, state, Genre, eduLevel);        
        System.out.println("========================================");
        System.out.println("¡Libro agregado exitosamente al inventario!");
    }

    public static void addJournals(ArrayList<Journal> inventory) {
        System.out.println("\n========== REGISTRAR REVISTA ==========");

        System.out.print("Nombre: ");
        String name = input.nextLine();

        System.out.print("Nombre de la editorial: ");
        String editorialName = input.nextLine();        

        System.out.print("Anio de publicacion: ");
        int year = Integer.parseInt(input.nextLine());
        LocalDate yearOfPublication = LocalDate.of(year, 1, 1);

        System.out.print("Precio: ");
        float price = Float.parseFloat(input.nextLine());

        System.out.print("Cantidad inicial: ");
        int stock = Integer.parseInt(input.nextLine());

        if (year < 1 || price < 0 || stock < 0) {
            System.out.println("Datos no validos.");
            return;
        }

        System.out.print("Estado de la revista: ");
        String state = input.nextLine();

        Operations.addJournal(inventory, name, editorialName, yearOfPublication, price, state);        
        System.out.println("========================================");
        System.out.println("¡Revista agregada exitosamente al inventario!");
    }

    public static void addPurchase(
            ArrayList<Book> books,
            ArrayList<Journal> journals,
            ArrayList<Purchase> purchases) {
        System.out.println("Agregar una Compra: ");
        System.out.println("========================================");

        System.out.print("Nombre del proveedor: ");
        String supplierName = input.nextLine();
        Purchase purchase = Operations.createPurchase(supplierName);        

        String again = "s";

        while (again.equalsIgnoreCase("s")) {
            System.out.println("1.Comprar Libro");
            System.out.println("2.Comprar Revista");
            System.out.print("Ingrese una Opcion: ");
            int type = Integer.parseInt(input.nextLine());          

            if (type == 1) {
                addBookToPurchase(purchase, books);
            } else {
                addJournalToPurchase(purchase, journals);
            }
            System.out.print("¿Desea agregar otro producto? (s/n): ");
            again = input.nextLine();
        }

        Operations.registerPurchase(purchase, purchases);

        JsonPersistence.saveList("books.json", books);
        JsonPersistence.saveList("journals.json", journals);
        JsonPersistence.saveList("purchases.json", purchases);

        System.out.println("Compra registrada. Total: $"
                + purchase.getTotalPurchase());
    }
    
    private static void addBookToPurchase(Purchase purchase, ArrayList<Book> books){
        for (int i = 0; i < books.size(); i++){
            System.out.println(i + "." + books.get(i).getTitle());
        }
        System.out.println("Numero del libro: ");
        int pos = Integer.parseInt(input.nextLine());
        Book book = books.get(pos);

        System.out.print("Cantidad: ");
        int quantity = Integer.parseInt(input.nextLine());

        System.out.print("Precio de compra: ");
        float price = Float.parseFloat(input.nextLine());
        
        Operations.addBookToPurchase(purchase, book, quantity, price);
    }  
    
    private static void addJournalToPurchase(Purchase purchase, ArrayList<Journal> journals) {
        for (int i = 0; i < journals.size(); i++) {
            System.out.println(
                    i + ". " + journals.get(i).getNameJournal()
            );
        }
        System.out.print("Numero de la revista: ");
        int pos = Integer.parseInt(input.nextLine());
        Journal journal = journals.get(pos);

        System.out.print("Cantidad: ");
        int quantity = Integer.parseInt(input.nextLine());

        System.out.print("Precio de compra: ");
        float price = Float.parseFloat(input.nextLine());

        if (quantity <= 0 || price < 0) {
            System.out.println("Cantidad o precio no valido.");
            return;
        }

        Operations.addJournalToPurchase(
                purchase, journals.get(pos), quantity, price
        );
    }

    public static void searchBooks(ArrayList<Book> inventory) {
        System.out.flush();
        System.out.println("========================================");
        System.out.println("Buscar Libros: ");
        System.out.println("========================================");
        System.out.println("1. Buscar por Titulo");
        System.out.println("2. Buscar Por Autor");
        System.out.println("========================================");
        System.out.print("Ingrese una Opcion: ");
        int opc = Integer.parseInt(input.nextLine());
        switch (opc) {
            case 1 -> {
                System.out.print("Ingrese el Titulo del Libro: ");
                String title = input.nextLine();
                ArrayList<Book> results = Operations.searchBookByTitle(title, inventory);
                System.out.println("======================================");
                System.out.println("Libros Encontrados: ");
                for (Book b : results) {
                    System.out.println("ID: " + b.getIdBook() + " | Título: " + b.getTitle() + " | Precio: $" + b.getPrice());
                }
            }
            case 2 -> {
                System.out.print("Ingrese El Nombre del Autor: ");
                String nameAuthor = input.nextLine();
                ArrayList<Book> results = Operations.searchBookByAuthor(nameAuthor, inventory);
                System.out.println("======================================");
                System.out.println("Libros Encontrados: ");
                for (Book b : results) {
                    System.out.println("ID: " + b.getIdBook() + " | Título: " + b.getTitle() + " | Precio: $" + b.getPrice());
                }
            }
            default ->
                throw new AssertionError();
        }
    }

    public static void searchJournals(ArrayList<Journal> inventory) {
        System.out.print("Nombre de la revista: ");

        ArrayList<Journal> results
                = Operations.searchJournalByTitle(input.nextLine(), inventory);

        if (results.isEmpty()) {
            System.out.println("No se encontraron revistas.");
            return;
        }

        for (Journal journal : results) {
            System.out.println(journal);
        }
    }

    public static void checkEconomicStatus(
            ArrayList<Purchase> purchases,
            ArrayList<Sale> sales) {

        System.out.print("Fecha inicio (AAAA-MM-DD): ");
        LocalDate start = LocalDate.parse(input.nextLine());

        System.out.print("Fecha fin (AAAA-MM-DD): ");
        LocalDate end = LocalDate.parse(input.nextLine());

        if (end.isBefore(start)) {
            System.out.println("La fecha final no puede ser anterior.");
            return;
        }

        float totalPurchases
                = Purchase.getTotalPurchasesByPeriod(start, end, purchases);

        float totalSales
                = Operations.totalSalesByPeriod(start, end, sales);

        System.out.println("Ingresos por ventas: $" + totalSales);
        System.out.println("Gastos por compras: $" + totalPurchases);
        System.out.println("Balance: $" + (totalSales - totalPurchases));
    }

    public static void checkClientFrequency(
            ArrayList<Customer> customers) {

        if (customers.isEmpty()) {
            System.out.println("No existen clientes registrados aún.");
            return;
        }

        for (Customer customer : customers) {
            System.out.println(
                    "Cliente: " + customer.getName()
                    + " | Visitas: " + customer.getVisitHistory().size()
                    + " | Frecuencia: " + customer.getClientFrequency()
            );
        }
    }

    private static void saveInventory(
            ArrayList<Book> books,
            ArrayList<Journal> journals,
            ArrayList<Purchase> purchases,
            ArrayList<Customer> customers,
            ArrayList<Sale> sales) {

        JsonPersistence.saveList("books.json", books);
        JsonPersistence.saveList("journals.json", journals);

        if (purchases != null) {
            JsonPersistence.saveList("purchases.json", purchases);
        }

        JsonPersistence.saveList("customers.json", customers);
        JsonPersistence.saveList("sales.json", sales);
    }
}
