package ec.edu.espe.bookstoreapp.view;

import ec.edu.espe.bookstoreapp.model.Author;
import ec.edu.espe.bookstoreapp.model.Editorial;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Nestor Olivo, Found ERROR 404, @ESPE
 */
public class View {

    private static Scanner input = new Scanner(System.in);

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
        try{
            opc = Integer.parseInt(input.nextLine());
            switch (opc) {
                case 1 -> {
                    addBooks();
                }
                case 2-> {
                    
                }
                case 3->{
                    
                }
                default -> throw new AssertionError();
            }
        }catch(NumberFormatException e){
        
        }
    }
    public static void addBooks(){
        String title;
        ArrayList<Author> authors = new ArrayList<>();
        Editorial editorial;
        
        System.out.println("Agregar Libros: ");
        System.out.println("========================================");
        
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
    }

    public static void checkEconomicStatus() {
        System.out.flush();
        System.out.println("Estado Economico");
    }

    public static void checkClientFrequency() {
        System.out.flush();
        System.out.println("Verificar Frecuencia del Cliente");
    }
}
