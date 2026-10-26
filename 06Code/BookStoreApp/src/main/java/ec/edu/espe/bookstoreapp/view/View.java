package ec.edu.espe.bookstoreapp.view;

import ec.edu.espe.bookstoreapp.model.Author;
import ec.edu.espe.bookstoreapp.model.Book;
import ec.edu.espe.bookstoreapp.model.Editorial;
import ec.edu.espe.bookstoreapp.model.Journal;
import ec.edu.espe.bookstoreapp.model.Purchase;
import ec.edu.espe.bookstoreapp.model.PurchaseDetail;
import ec.edu.espe.bookstoreapp.model.Supplier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Nestor Olivo, Found ERROR 404, @ESPE
 */
public class View {

    private static Scanner input = new Scanner(System.in);

    private static ArrayList<Book> books = new ArrayList<>();
    private static ArrayList<Journal> journals = new ArrayList<>();
    private static ArrayList<Editorial> editorials = new ArrayList<>();
    private static ArrayList<Supplier> suppliers = new ArrayList<>();
    private static ArrayList<Purchase> purchases = new ArrayList<>();

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

    public static void showChoseOption(int opc) {
        switch (opc) {
            case 1 ->
                checkAvailableBooks();
            case 2 ->
                checkAvailableJounaries();
            case 3 ->
                makeANewSale();
            case 4 ->
                AddNewProducts();
            case 5 ->
                searchBooks();
            case 6 ->
                searchJournals();
            case 7 ->
                checkEconomicStatus();
            case 8 ->
                checkClientFrequency();
            default ->
                System.exit(0);
        }
    }

    public static void checkAvailableBooks() {
        System.out.flush();
        System.out.println("Consultar Libros en Existencia");
        System.out.println("==============================");
    }

    public static void checkAvailableJounaries() {
        System.out.flush();
        System.out.println("Consulta de Revistas en Existencia");
        System.out.println("==============================");
        for (Journal journal : journals) {
            if (journal.validateStock()) {
                System.out.println(journal);
            }
        }
    }

    public static void makeANewSale() {
        System.out.flush();
        System.out.println("Realizar una Nueva Venta");
        System.out.println("==============================");
    }

    public static void AddNewProducts() {
        int opc;
        System.out.flush();
        System.out.println("Agregar Nuevos Productos");
        System.out.println("==============================");
        System.out.println("1.Agregar Libros");
        System.out.println("2.Agregar Revistas");
        System.out.println("3.Agregar una Compra");
        System.out.println("==============================");
        System.out.print("Ingrese una Opcion: ");
        opc = Integer.parseInt(input.nextLine());
        switch (opc) {
            case 1 -> {
                addBooks();
            }
            case 2 -> {
                addJournals();
            }
            case 3 -> {
                addPurchase();
            }
            default -> System.out.println("Opcion no valida");
        }
    }

    public static void addBooks() {
        String title;
        ArrayList<Author> authors = new ArrayList<>();
        Editorial editorial;

        System.out.println("Agregar Libros: ");
        System.out.println("========================================");

    }

    public static void addJournals() {
        System.out.println("Agregar Revistas: ");
        System.out.println("========================================");

        System.out.print("Nombre de la revista: ");
        String name = input.nextLine();

        System.out.print("Nombre de la editorial: ");
        String editorialName = input.nextLine();
        Editorial editorial = new Editorial(editorialName);
        editorials.add(editorial);

        System.out.print("Anio de publicacion: ");
        int year = Integer.parseInt(input.nextLine());
        LocalDate yearOfPublication = LocalDate.of(year, 1, 1);

        System.out.print("Precio: ");
        float price = Float.parseFloat(input.nextLine());

        System.out.print("Stock: ");
        int stock = Integer.parseInt(input.nextLine());

        System.out.print("Estado: ");
        String state = input.nextLine();

        Journal journal = new Journal(name, editorial, yearOfPublication, price, stock, state);
        journals.add(journal);
        System.out.println("Revista registrada: " + journal);
    }

    public static void addPurchase() {
        System.out.println("Agregar una Compra: ");
        System.out.println("========================================");

        System.out.print("Nombre del proveedor: ");
        String supplierName = input.nextLine();
        Supplier supplier = new Supplier(supplierName);
        suppliers.add(supplier);

        Purchase purchase = new Purchase(LocalDate.now(), supplier);

        String again = "si";
        while (again.equalsIgnoreCase("si")) {
            System.out.println("1.Comprar Libro");
            System.out.println("2.Comprar Revista");
            System.out.print("Ingrese una Opcion: ");
            int type = Integer.parseInt(input.nextLine());

            Book book = null;
            Journal journal = null;

            if (type == 1) {
                for (int i = 0; i < books.size(); i++) {
                    System.out.println(i + ". " + books.get(i).getTitle());
                }
                System.out.print("Numero del libro: ");
                int pos = Integer.parseInt(input.nextLine());
                book = books.get(pos);
            } else {
                for (int i = 0; i < journals.size(); i++) {
                    System.out.println(i + ". " + journals.get(i).getNameJournal());
                }
                System.out.print("Numero de la revista: ");
                int pos = Integer.parseInt(input.nextLine());
                journal = journals.get(pos);
            }

            System.out.print("Cantidad: ");
            int quantity = Integer.parseInt(input.nextLine());

            System.out.print("Precio de compra: ");
            float price = Float.parseFloat(input.nextLine());

            PurchaseDetail detail = new PurchaseDetail(quantity, price, book, journal);
            purchase.addDetailPurchase(detail);

            System.out.print("Desea agregar otro producto (si/no): ");
            again = input.nextLine();
        }

        purchases.add(purchase);
        System.out.println("Compra registrada. Total: " + purchase.getTotalPurchase());
    }

    public static void searchBooks() {
        System.out.flush();
        System.out.println("Buscar Libros: ");
        System.out.println("========================================");
        System.out.println("1. Buscar por Autor");
        System.out.println("2. Buscar Por Titulo");
        System.out.println("========================================");
        System.out.print("Ingrese una Opcion: ");
    }

    public static void searchJournals() {
        System.out.flush();
        System.out.println("Buscar Revistas: ");
        System.out.print("Ingrese el Nombre de la Revista: ");
        String name = input.nextLine();

        ArrayList<Journal> results = Journal.searchJournalByTitle(name, journals);
        for (Journal journal : results) {
            System.out.println(journal);
        }
    }

    public static void checkEconomicStatus() {
        System.out.flush();
        System.out.println("Estado Economico");
        System.out.println("==============================");

        System.out.print("Fecha inicio (AAAA-MM-DD): ");
        LocalDate start = LocalDate.parse(input.nextLine());

        System.out.print("Fecha fin (AAAA-MM-DD): ");
        LocalDate end = LocalDate.parse(input.nextLine());

        float total = Purchase.getTotalPurchasesByPeriod(start, end, purchases);
        System.out.println("Total gastado en compras: " + total);
    }

    public static void checkClientFrequency() {
        System.out.flush();
        System.out.println("Verificar Frecuencia del Cliente");
    }
}