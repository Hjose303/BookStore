
package ec.edu.espe.bookstoreapp.model;

/**
 *
 * @author Nestor Olivo, Found ERROR 404, @ESPE
 */
public class Editorial {
    private static int idCurrent;
    private int idEditorial;
    private int nameEditorial;

    public Editorial(int nameEditorial) {
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

    public int getNameEditorial() {
        return nameEditorial;
    }

    public void setNameEditorial(int nameEditorial) {
        this.nameEditorial = nameEditorial;
    }
    
}
