package ec.edu.espe.bookstoreapp.view;

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
                           4.Realizar una Nueva Compra - Agregar Nuevos Productos (Libros / Revistas)
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
            case 1 -> {
                System.out.println("Consultar Libros en Existencia");
                System.out.println("==============================");
            }
            case 2 -> {
                System.out.println("Consulta de Revistas en Existencia");
                System.out.println("==============================");
            }
            case 3 -> {
                System.out.println("Realizar una Nueva Venta");
                System.out.println("==============================");
            }
            case 4 -> {
                System.out.println("Agregar Libros o Agregar Una Compra");
                System.out.println("==============================");
                System.out.println("1.Agregar Libros");
                System.out.println("2.Agregar Revistas");
                System.out.println("3.Agregar una Compra");
                System.out.println("==============================");
                System.out.print("Ingrese una Opcion: ");
            }
            case 5 -> {
                System.out.println("Buscar Libros: ");
                System.out.println("========================================");
                System.out.println("1. Buscar por Autor");
                System.out.println("2. Buscar Por Titulo");
                System.out.println("========================================");
                System.out.print("Ingrese una Opcion: ");
            }
            case 6 -> {
                System.out.println("Buscar Revistas: ");
                System.out.print("Ingrese el Nombre de la Revista: ");
            }
            case 7 -> {
                System.out.println("Estado Economico");
            }
            case 8 -> {
                System.out.println("Verificar Frecuencia del Cliente");
            }
            default -> {
                System.exit(0);
                throw new AssertionError();
            }
        }
    }

    
}
