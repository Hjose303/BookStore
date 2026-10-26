/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.bookstoreapp.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Dennis Noboa, 404 Founder, @ESPE
 */
public class Customer {
    private static int idCurrent;
    private int idCustomer;
    private String name;
    private ArrayList<LocalDate> visitHistory;

    public Customer(String name) {
        this.idCustomer = idCurrent++;
        this.name = name;
        this.visitHistory = new ArrayList<>();
    }
    
    public void addVisit(LocalDate date) {
        if (date != null) {
            visitHistory.add(date);
        }
    }

    public String getClientFrequency() {
        int totalVisits = visitHistory.size();
        if (totalVisits > 10) {
            return "Frecuente";
        } else if (totalVisits > 3) {
            return "Regular";
        } else {
            return "Ocasional";
        }
    }
    
    /**
     * @return the idCurrent
     */
    public static int getIdCurrent() {
        return idCurrent;
    }

    /**
     * @param idCurrent the idCurrent to set
     */
    public static void setIdCurrent(int idCurrent) {
        Customer.idCurrent = idCurrent;
    }

    /**
     * @return the idCustomer
     */
    public int getIdCustomer() {
        return idCustomer;
    }

    /**
     * @param idCustomer the idCustomer to set
     */
    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the visitHistory
     */
    public ArrayList<LocalDate> getVisitHistory() {
        return visitHistory;
    }

    /**
     * @param visitHistory the visitHistory to set
     */
    public void setVisitHistory(ArrayList<LocalDate> visitHistory) {
        this.visitHistory = visitHistory;
    }

    @Override
    public String toString() {
        return "Customer{" + "idCustomer=" + idCustomer + ", name=" + name + ", visitHistory=" + visitHistory + '}';
    }
    
}
