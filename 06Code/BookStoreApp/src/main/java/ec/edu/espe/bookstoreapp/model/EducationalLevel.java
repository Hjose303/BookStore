
package ec.edu.espe.bookstoreapp.model;

/**
 *
 * @author Nestor Olivo, Found ERROR 404, @ESPE
 */
public class EducationalLevel {
    private static int idCurrentLevel;
    private int idLevel;
    private String nameEducationalLevel;

    public EducationalLevel(String nameEducationalLevel) {
        this.idLevel = idCurrentLevel++;
        this.nameEducationalLevel = nameEducationalLevel;
    }

    public static int getIdCurrentLevel() {
        return idCurrentLevel;
    }

    public static void setIdCurrentLevel(int idCurrentLevel) {
        EducationalLevel.idCurrentLevel = idCurrentLevel;
    }

    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public String getNameEducationalLevel() {
        return nameEducationalLevel;
    }

    public void setNameEducationalLevel(String nameEducationalLevel) {
        this.nameEducationalLevel = nameEducationalLevel;
    }

    @Override
    public String toString() {
        return "EducationalLevel{" 
                + "idLevel=" + idLevel 
                + ", nameEducationalLevel=" + nameEducationalLevel 
                + '}';
    }
    
}
