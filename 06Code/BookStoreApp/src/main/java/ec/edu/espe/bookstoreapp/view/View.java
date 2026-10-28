package ec.edu.espe.bookstoreapp.view;

import ec.edu.espe.controller.Operations;
import ec.edu.espe.bookstoreapp.model.Book;
import ec.edu.espe.bookstoreapp.model.Customer;
import ec.edu.espe.bookstoreapp.model.Journal;
import ec.edu.espe.bookstoreapp.model.Purchase;
import ec.edu.espe.bookstoreapp.model.Sale;
import java.time.LocalDate;
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
                System.out.println("ID: " + book.getIdBook() + " | Título: " + book.getTitle() 
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

    private static void addProductsToSale(Sale sale, ArrayList<Book> inventoryBooks,
            ArrayList<Journal> inventoryJournals) {
        boolean addingProducts = true;
        while (addingProducts) {
            System.out.println("\nSeleccione Tipo de Producto a vender:");
            System.out.println("1. Libro");
            System.out.println("2. Revista");
            System.out.print("Opción: ");
            int optionProduct = Integer.parseInt(input.nextLine());

            if (optionProduct == 1) {
                addBookToSale(sale, inventoryBooks);
            } else if (optionProduct == 2) {
                addJournalToSale(sale, inventoryJournals);
            }

            System.out.print("¿Desea agregar otro producto a esta venta? (s/n): ");
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
        System.out.println("Agregar Revistas: ");
        System.out.println("========================================");

        System.out.print("Nombre de la revista: ");
        String name = input.nextLine();

        System.out.print("Nombre de la editorial: ");
        String editorialName = input.nextLine();        

        System.out.print("Anio de publicacion: ");
        int year = Integer.parseInt(input.nextLine());
        LocalDate yearOfPublication = LocalDate.of(year, 1, 1);

        System.out.print("Precio: ");
        float price = Float.parseFloat(input.nextLine());

        System.out.print("Estado: ");
        String state = input.nextLine();

        Operations.addJournal(inventory, name, editorialName, yearOfPublication, price, state);        
        System.out.println("========================================");
        System.out.println("¡Revista agregada exitosamente al inventario!");
    }

    public static void addPurchase(ArrayList<Book> books, ArrayList<Journal> journals,
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
        System.out.println("========================================");
        System.out.println("Compra registrada. Total: " + purchase.getTotalPurchase());
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
            System.out.println(i + ". " + journals.get(i).getNameJournal());
        }
        System.out.print("Numero de la revista: ");
        int pos = Integer.parseInt(input.nextLine());
        Journal journal = journals.get(pos);

        System.out.print("Cantidad: ");
        int quantity = Integer.parseInt(input.nextLine());

        System.out.print("Precio de compra: ");
        float price = Float.parseFloat(input.nextLine());

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

        System.out.print("Fecha inicio (AAAA-MM-DD): ");
        LocalDate start = LocalDate.parse(input.nextLine());

        System.out.print("Fecha fin (AAAA-MM-DD): ");
        LocalDate end = LocalDate.parse(input.nextLine());

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
            System.out.println("No existen clientes registrados aún.");
            return;
        }

        for (Customer c : customers) {
            System.out.println("Cliente: " + c.getName() 
                    + " | Visitas Registradas: " + c.getVisitHistory().size() 
                    + " | Frecuencia: " + c.getClientFrequency());
        }
    }
}