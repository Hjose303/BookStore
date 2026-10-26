package ec.edu.espe.bookstoreapp.view;

import ec.edu.espe.bookstoreapp.model.Book;
import ec.edu.espe.bookstoreapp.model.Customer;
import ec.edu.espe.bookstoreapp.model.Journal;
import ec.edu.espe.bookstoreapp.model.Purchase;
import ec.edu.espe.bookstoreapp.model.Sale;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Nestor Olivo, Found ERROR 404, @ESPE
 */
public class BookStoreApp {

    public static void main(String[] args) {
        String optionContinue;
        int opc;
        boolean controller = true;
        ArrayList<Book> inventoryBook = new ArrayList<>();
        ArrayList<Journal> inventoryJournal = new ArrayList<>();
        ArrayList<Purchase> purchases = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Sale> sales = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        while (controller) {
            View.showMenu();
            System.out.print("Seleccione una Accion: ");
            
            try {
                opc = Integer.parseInt(input.nextLine());
                View.showChoseOption(opc, inventoryBook, inventoryJournal, purchases, customers, sales);
            } catch (NumberFormatException e) {
                while (controller) {
                    System.out.println("Ingrese una Opcion Valida");
                    System.out.println("=========================");
                    System.out.print("Desea Volver a Intentar Si o No: ");
                    optionContinue = input.nextLine();
                    if (optionContinue.equalsIgnoreCase("si")) {
                        controller = false;
                    } else if (optionContinue.equalsIgnoreCase("no")) {
                        System.exit(0);
                    }
                }
                controller = true;
            }
        }
    }
}