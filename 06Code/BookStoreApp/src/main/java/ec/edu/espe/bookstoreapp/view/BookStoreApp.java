package ec.edu.espe.bookstoreapp.view;

import ec.edu.espe.bookstoreapp.model.Book;
import ec.edu.espe.bookstoreapp.model.Customer;
import ec.edu.espe.bookstoreapp.model.Journal;
import ec.edu.espe.bookstoreapp.model.Purchase;
import ec.edu.espe.bookstoreapp.model.Sale;
import ec.edu.espe.bookstoreapp.model.JsonPersistence;
import com.google.gson.reflect.TypeToken;

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
        ArrayList<Book> inventoryBook= JsonPersistence.loadList("books.json",new TypeToken<ArrayList<Book>>() {});

        ArrayList<Journal> inventoryJournal= JsonPersistence.loadList("journals.json",new TypeToken<ArrayList<Journal>>() {});

        ArrayList<Purchase> purchases
                = JsonPersistence.loadList(
                        "purchases.json",
                        new TypeToken<ArrayList<Purchase>>() {
                }
                );

        ArrayList<Customer> customers
                = JsonPersistence.loadList(
                        "customers.json",
                        new TypeToken<ArrayList<Customer>>() {
                }
                );

        ArrayList<Sale> sales
                = JsonPersistence.loadList(
                        "sales.json",
                        new TypeToken<ArrayList<Sale>>() {
                }
                );
        Scanner input = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            JsonPersistence.saveList("books.json", inventoryBook);
            JsonPersistence.saveList("journals.json", inventoryJournal);
            JsonPersistence.saveList("purchases.json", purchases);
            JsonPersistence.saveList("customers.json", customers);
            JsonPersistence.saveList("sales.json", sales);
        }));
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
