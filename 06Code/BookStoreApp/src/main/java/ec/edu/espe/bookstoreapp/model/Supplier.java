package ec.edu.espe.bookstoreapp.model;

/**
 *
 * @author Julian Olalla, 404 Founders, @ESPE
 */
public class Supplier {
    private static int idCurrent;
    private int idSupplier;
    private String nameSupplier;

    public Supplier(String nameSupplier) {
        this.idSupplier = idCurrent++;
        this.nameSupplier = nameSupplier;
    }

    @Override
    public String toString() {
        return "Supplier{" + "idSupplier=" + getIdSupplier() + ", nameSupplier=" + getNameSupplier() + '}';
    }

    public static int getIdCurrent() {
        return idCurrent;
    }

    public static void setIdCurrent(int idCurrent) {
        Supplier.idCurrent = idCurrent;
    }

    /**
     * @return the idSupplier
     */
    public int getIdSupplier() {
        return idSupplier;
    }

    /**
     * @param idSupplier the idSupplier to set
     */
    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    /**
     * @return the nameSupplier
     */
    public String getNameSupplier() {
        return nameSupplier;
    }

    /**
     * @param nameSupplier the nameSupplier to set
     */
    public void setNameSupplier(String nameSupplier) {
        this.nameSupplier = nameSupplier;
    }
}