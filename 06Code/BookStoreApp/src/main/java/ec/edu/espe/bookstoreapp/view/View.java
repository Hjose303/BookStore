package ec.edu.espe.bookstoreapp.view;

import ec.edu.espe.bookstoreapp.controller.Operations;
import ec.edu.espe.bookstoreapp.exception.EmptyInventoryException;
import ec.edu.espe.bookstoreapp.exception.InvalidValueException;
import ec.edu.espe.bookstoreapp.model.Book;
import ec.edu.espe.bookstoreapp.model.Customer;
import ec.edu.espe.bookstoreapp.model.Journal;
import ec.edu.espe.bookstoreapp.model.Purchase;
import ec.edu.espe.bookstoreapp.model.Sale;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Nestor Olivo, 404 Founders, @ESPE
 */
public class View {

    private static final Scanner input = new Scanner(System.in);

    public static void showMenu() {
        System.out.println("""
                           =====================================================
                           \t\tLIBRERIA - OLIVO CULTURAL 
                           \tCinta de Opciones
                           =====================================================
                           Seleccione Que Accion Desea Realizar:
                           1.Consultar Libros en Existencia
                           2.Consultar Revistas en Existencias
                           3.Realizar una Nueva Venta - Nota Venta
                           4.Realizar una Nueva Compra - Agregar Nuevos Productos
                           5.Bucar Libros
                           6.Buscar Revistas
                           7.Estado Economico
                           8.Verificar Frecuencia del Cliente
                           9.Salir
                           =====================================================
                           """);
    }
    
    public static int askMenuOption() {
        System.out.print("Seleccione una Accion: ");
        return Integer.parseInt(input.nextLine());
    }

    public static String askRetry() {
        System.out.println("Ingrese una Opcion Valida");
        System.out.println("=========================");
        System.out.print("Desea Volver a Intentar Si o No: ");
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

    public static void showChoseOption(int opc, ArrayList<Book> books,
            ArrayList<Journal> journals, ArrayList<Purchase> purchases,
            ArrayList<Customer> customers, ArrayList<Sale> sales) {
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
            default ->
                System.exit(0);
        }
    }

    public static void checkAvailableBooks(ArrayList<Book> inventory) {
        System.out.flush();
        System.out.println("Consultar Libros en Existencia");
        System.out.println("==============================");
        if (inventory.isEmpty()) {
            System.out.println("No hay libros registrados en el inventario.");
            return;
        }
        for (Book book : inventory) {
            if (book.validateStock()) {
                System.out.println("ID: " + book.getIdBook() + " | Titulo: " + book.getTitle() 
                        + " | Precio: $" + book.getPrice() + " | Stock: " + book.getStock());
            }
        }
    }

    public static void checkAvailableJounaries(ArrayList<Journal> inventory) {
        System.out.flush();
        System.out.println("Consulta de Revistas en Existencia");
        System.out.println("==============================");
        if (inventory.isEmpty()) {
            System.out.println("No hay revistas registradas en el inventario.");
            return;
        }
        for (Journal journal : inventory) {
            System.out.println(journal);
        }
    }

    public static void makeANewSale(ArrayList<Book> inventoryBooks, ArrayList<Journal> inventoryJournals,
            ArrayList<Customer> customers, ArrayList<Sale> sales) {
        System.out.flush();
        System.out.println("Realizar una Nueva Venta");
        System.out.println("==============================");

        // 1. Datos del Cliente
        Customer customer = askCustomer(customers);

        // 2. Metodo de Pago
        String paymentMethodName = askPaymentMethodName();

        // 3. Crear Venta temporal
        Sale sale = Operations.createSale(customer, askSaleType());

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
        System.out.print("Ingrese el Metodo de Pago (Efectivo/Tarjeta/Transferencia): ");
        return input.nextLine();
    }

    private static String askSaleType() {
        System.out.print("Ingrese el Tipo de Venta (Factura/Nota de Venta): ");
        return input.nextLine();
    }

    private static void addProductsToSale(Sale sale, ArrayList<Book> inventoryBooks,
            ArrayList<Journal> inventoryJournals) {
        boolean addingProducts = true;
        while (addingProducts) {
            System.out.println("\nSeleccione Tipo de Producto a vender:");
            System.out.println("1. Libro");
            System.out.println("2. Revista");
            int optionProduct = askInt("Opcion: ", 1, 2, "La opcion");

            if (optionProduct == 1) {
                addBookToSale(sale, inventoryBooks);
            } else if (optionProduct == 2) {
                addJournalToSale(sale, inventoryJournals);
            }

            System.out.print("Desea agregar otro producto a esta venta? (s/n): ");
            String continueOpt = input.nextLine();
            if (!continueOpt.equalsIgnoreCase("s")) {
                addingProducts = false;
            }
        }
    }

    private static void addBookToSale(Sale sale, ArrayList<Book> inventoryBooks) {
        if (inventoryBooks.isEmpty()) {
            System.out.println("No hay libros registrados en el inventario.");
            return;
        }
        System.out.println("--- Libros Disponibles ---");
        for (int i = 0; i < inventoryBooks.size(); i++) {
            Book b = inventoryBooks.get(i);
            System.out.println(i + ". " + b.getTitle() + " | Precio: $" + b.getPrice() + " | Stock: " + b.getStock());
        }
        int pos = askInt("Numero del libro: ", 0, inventoryBooks.size() - 1, "El numero del libro");
        Book selectedBook = inventoryBooks.get(pos);
        int qty = askPositiveInt("Ingrese la cantidad a vender: ", "La cantidad");

        if (Operations.addBookToSale(sale, selectedBook, qty)) {
            System.out.println("Libro agregado a la venta!");
        } else {
            System.out.println("ERROR: Stock insuficiente o cantidad no valida.");
        }
    }

    private static void addJournalToSale(Sale sale, ArrayList<Journal> inventoryJournals) {
        if (inventoryJournals.isEmpty()) {
            System.out.println("No hay revistas registradas en el inventario.");
            return;
        }
        System.out.println("--- Revistas Disponibles ---");
        for (int i = 0; i < inventoryJournals.size(); i++) {
            Journal j = inventoryJournals.get(i);
            System.out.println(i + ". " + j.getNameJournal() + " | Precio: $" + j.getPrice() + " | Stock: " + j.getStock());
        }
        int pos = askInt("Numero de la revista: ", 0, inventoryJournals.size() - 1, "El numero de la revista");
        Journal selectedJournal = inventoryJournals.get(pos);
        int qty = askPositiveInt("Ingrese la cantidad a vender: ", "La cantidad");

        if (Operations.addJournalToSale(sale, selectedJournal, qty)) {
            System.out.println("Revista agregada a la venta!");
        } else {
            System.out.println("ERROR: Stock insuficiente o cantidad no valida.");
        }
    }

    private static void processPayment(Sale sale, String paymentMethodName, ArrayList<Sale> sales) {
        float totalSale = sale.computeTotal();
        System.out.println("\nTotal de la venta: $" + totalSale);
        float amountPaid = askNonNegativeFloat("Ingrese el monto abonado por el cliente $: ", "El monto abonado");

        if (Operations.registerPayment(sale, paymentMethodName, amountPaid, sales)) {
            float cambio = amountPaid - totalSale;
            System.out.println("==========================================");
            System.out.println("Venta procesada con exito!");
            System.out.println("Cambio a entregar: $" + cambio);
            System.out.println(sale.generateSalesReport(LocalDate.now(), LocalDate.now()));
            System.out.println("==========================================");
        } else {
            System.out.println("ERROR: El monto abonado es menor al total de la venta ($" + totalSale + "). Venta cancelada.");
        }
    }

    public static void AddNewProducts(ArrayList<Book> inventoryBooks,
            ArrayList<Journal> inventoryJournal, ArrayList<Purchase> purchases) {
        int opc;
        System.out.flush();
        System.out.println("Agregar Nuevos Productos");
        System.out.println("==============================");
        System.out.println("1.Agregar Libros");
        System.out.println("2.Agregar Revistas");
        System.out.println("3.Agregar una Compra");
        System.out.println("==============================");
        System.out.print("Ingrese una Opcion: ");
        try {
            opc = Integer.parseInt(input.nextLine());
            switch (opc) {
                case 1 -> {
                    addBooks(inventoryBooks);
                }
                case 2 -> {
                    addJournals(inventoryJournal);
                }
                case 3 -> {
                    addPurchase(inventoryBooks, inventoryJournal, purchases);
                }
                default ->
                    System.out.println("Opcion no valida");
            }
        } catch (NumberFormatException e) {

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
            System.out.print("Desea agregar otro autor para este libro? (s/n): ");
            option = input.nextLine();
        } while (option.equalsIgnoreCase("s"));
        System.out.print("Ingrese el Nombre de la Editorial: ");
        editorialName = input.nextLine();
        price = askPositiveFloat("Ingrese el Precio $: ", "El precio");
        System.out.print("Ingrese el Estado del Libro: ");
        state = input.nextLine();
        System.out.println("Ingrese el Genero Literario: ");
        Genre = input.nextLine();
        System.out.print("Ingrese el Nivel Educativo del Libro: ");
        eduLevel = input.nextLine();
        Operations.addBook(inventory, title, authorNames, editorialName, price, state, Genre, eduLevel);
        System.out.println("========================================");
        System.out.println("Libro agregado exitosamente al inventario!");
    }

    public static void addJournals(ArrayList<Journal> inventory) {
        System.out.println("Agregar Revistas: ");
        System.out.println("========================================");

        System.out.print("Nombre de la revista: ");
        String name = input.nextLine();

        System.out.print("Nombre de la editorial: ");
        String editorialName = input.nextLine();

        int year = askInt("Anio de publicacion: ", 1, 9999, "El año de publicacion");
        LocalDate yearOfPublication = LocalDate.of(year, 1, 1);

        float price = askPositiveFloat("Precio: ", "El precio");

        System.out.print("Estado: ");
        String state = input.nextLine();

        Operations.addJournal(inventory, name, editorialName, yearOfPublication, price, state);
        System.out.println("========================================");
        System.out.println("Revista agregada exitosamente al inventario!");
    }

    public static void addPurchase(ArrayList<Book> books, ArrayList<Journal> journals,
            ArrayList<Purchase> purchases) {
        System.out.println("Agregar una Compra: ");
        System.out.println("========================================");

        try {
            Operations.requireAnyProduct(books, journals);
        } catch (EmptyInventoryException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        System.out.print("Nombre del proveedor: ");
        String supplierName = input.nextLine();
        Purchase purchase = Operations.createPurchase(supplierName);

        String again = "s";
        while (again.equalsIgnoreCase("s")) {
            System.out.println("1.Comprar Libro");
            System.out.println("2.Comprar Revista");
            int type = askInt("Ingrese una Opcion: ", 1, 2, "La opcion");

            try {
                if (type == 1) {
                    addBookToPurchase(purchase, books);
                } else {
                    addJournalToPurchase(purchase, journals);
                }
            } catch (EmptyInventoryException e) {
                System.out.println("ERROR: " + e.getMessage());
                continue;
            }

            System.out.print("Desea agregar otro producto? (s/n): ");
            again = input.nextLine();
        }

        Operations.registerPurchase(purchase, purchases);
        System.out.println("========================================");
        System.out.println("Compra registrada. Total: " + purchase.getTotalPurchase());
    }

    private static void addBookToPurchase(Purchase purchase, ArrayList<Book> books)
            throws EmptyInventoryException {
        Operations.requireProducts(books, "libros");
        for (int i = 0; i < books.size(); i++) {
            System.out.println(i + ". " + books.get(i).getTitle());
        }
        int pos = askInt("Numero del libro: ", 0, books.size() - 1, "El numero del libro");
        Book book = books.get(pos);
        int quantity = askPositiveInt("Cantidad: ", "La cantidad");
        float price = askPositiveFloat("Precio de compra: ", "El precio");

        Operations.addBookToPurchase(purchase, book, quantity, price);
    }

    private static void addJournalToPurchase(Purchase purchase, ArrayList<Journal> journals)
            throws EmptyInventoryException {
        Operations.requireProducts(journals, "revistas");
        for (int i = 0; i < journals.size(); i++) {
            System.out.println(i + ". " + journals.get(i).getNameJournal());
        }
        int pos = askInt("Numero de la revista: ", 0, journals.size() - 1, "El numero de la revista");
        Journal journal = journals.get(pos);
        int quantity = askPositiveInt("Cantidad: ", "La cantidad");
        float price = askPositiveFloat("Precio de compra: ", "El precio");

        Operations.addJournalToPurchase(purchase, journal, quantity, price);
    }

    public static void searchBooks(ArrayList<Book> inventory) {
        System.out.flush();
        System.out.println("========================================");
        System.out.println("Buscar Libros: ");
        System.out.println("========================================");
        System.out.println("1. Buscar por Titulo");
        System.out.println("2. Buscar Por Autor");
        System.out.println("========================================");
        int opc = askInt("Ingrese una Opcion: ", 1, 2, "La opcion");
        switch (opc) {
            case 1 -> {
                System.out.print("Ingrese el Titulo del Libro: ");
                String title = input.nextLine();
                ArrayList<Book> results = Operations.searchBookByTitle(title, inventory);
                System.out.println("======================================");
                System.out.println("Libros Encontrados: ");
                for (Book b : results) {
                    System.out.println("ID: " + b.getIdBook() + " | Titulo: " + b.getTitle() + " | Precio: $" + b.getPrice());
                }
            }
            case 2 -> {
                System.out.print("Ingrese El Nombre del Autor: ");
                String nameAuthor = input.nextLine();
                ArrayList<Book> results = Operations.searchBookByAuthor(nameAuthor, inventory);
                System.out.println("======================================");
                System.out.println("Libros Encontrados: ");
                for (Book b : results) {
                    System.out.println("ID: " + b.getIdBook() + " | Titulo: " + b.getTitle() + " | Precio: $" + b.getPrice());
                }
            }
        }
    }

    public static void searchJournals(ArrayList<Journal> inventory) {
        System.out.flush();
        System.out.println("===========================================");
        System.out.println("Buscar Revistas: ");
        System.out.print("Ingrese el Nombre de la Revista: ");
        String nameJournal = input.nextLine();
        ArrayList<Journal> results = Operations.searchJournalByTitle(nameJournal, inventory);
        for (Journal journal : results) {
            System.out.println(journal);
        }
    }

    public static void checkEconomicStatus(ArrayList<Purchase> purchases, ArrayList<Sale> sales) {
        System.out.flush();
        System.out.println("Estado Economico");
        System.out.println("==============================");

        LocalDate start = askDate("Fecha inicio (AAAA-MM-DD): ");

        LocalDate end = askDate("Fecha fin (AAAA-MM-DD): ");

        float totalPurchases = Purchase.getTotalPurchasesByPeriod(start, end, purchases);
        float totalSales = Operations.totalSalesByPeriod(start, end, sales);

        System.out.println("Total Ingresos por Ventas: $" + totalSales);
        System.out.println("Total Gastos por Compras:  $" + totalPurchases);
        System.out.println("Balance Neto (Ganancia):   $" + (totalSales - totalPurchases));
    }

    public static void checkClientFrequency(ArrayList<Customer> customers) {
        System.out.flush();
        System.out.println("Verificar Frecuencia del Cliente");
        System.out.println("==============================");
        if (customers.isEmpty()) {
            System.out.println("No existen clientes registrados aun.");
            return;
        }

        for (Customer c : customers) {
            System.out.println("Cliente: " + c.getName() 
                    + " | Visitas Registradas: " + c.getVisitHistory().size() 
                    + " | Frecuencia: " + c.getClientFrequency());
        }
    }
}