
package ec.edu.espe.bookstoreapp.model;

/**
 *
 * @author Nestor Olivo, Found ERROR 404, @ESPE
 */
public class Editorial {
    private static int idCurrent;
    private int idEditorial;
    private String nameEditorial;

    public Editorial(String nameEditorial) {
        this.nameEditorial = nameEditorial;
        this.idEditorial = idCurrent++;
    }

    public static int getIdCurrent() {
        return idCurrent;
    }

    public static void setIdCurrent(int idCurrent) {
        Editorial.idCurrent = idCurrent;
    }

    public int getIdEditorial() {
        return idEditorial;
    }

    public void setIdEditorial(int idEditorial) {
        this.idEditorial = idEditorial;
    }

    public String getNameEditorial() {
        return nameEditorial;
    }

    public void setNameEditorial(String nameEditorial) {
        this.nameEditorial = nameEditorial;
    }
    
    @Override
    public String toString() {
    return "Editorial{" + "idEditorial=" + idEditorial
            + ", nameEditorial=" + nameEditorial + '}';
}
}
