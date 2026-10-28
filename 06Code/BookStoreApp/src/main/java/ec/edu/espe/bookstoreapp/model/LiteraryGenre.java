package ec.edu.espe.bookstoreapp.model;

/**
 *
 * @author Nestor Olivo, 404 Founders, @ESPE
 */
public class LiteraryGenre {
    private static int idCurrentGenre;
    private int idLiteraryGenre;
    private String LiteraryGenre;

    public LiteraryGenre(String LiteraryGenre) {
        this.LiteraryGenre = LiteraryGenre;
        this.idLiteraryGenre = idCurrentGenre++;
    }

    public static int getIdCurrent() {
        return idCurrentGenre;
    }

    public static void setIdCurrent(int idCurrent) {
        idCurrentGenre = idCurrent;
    }

    public int getIdLiteraryGenre() {
        return idLiteraryGenre;
    }

    public void setIdLiteraryGenre(int idLiteraryGenre) {
        this.idLiteraryGenre = idLiteraryGenre;
    }

    public String getLiteraryGenre() {
        return LiteraryGenre;
    }

    public void setLiteraryGenre(String LiteraryGenre) {
        this.LiteraryGenre = LiteraryGenre;
    }

    

    @Override
    public String toString() {
        return "LiteraryGenre{" 
                + "idLiteraryGenre=" + idLiteraryGenre 
                + ", LiteraryGenre=" + LiteraryGenre 
                + '}';
    }
    
    
}
