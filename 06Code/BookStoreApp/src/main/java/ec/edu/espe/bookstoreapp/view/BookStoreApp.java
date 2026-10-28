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
 * @author Nestor Olivo, 404 Founders, @ESPE
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
            
            try {
                opc = View.askMenuOption();
                View.showChoseOption(opc, inventoryBook, inventoryJournal, purchases, customers, sales);
            } catch (NumberFormatException e) {
                while (controller) {                    
                    optionContinue = View.askRetry();
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