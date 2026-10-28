package ec.edu.espe.bookstoreapp.model;

/**
 *
 * @author Nestor Olivo, 404 Founders, @ESPE
 */
public class Author {
    private static int idCurrent;
    private int idAuthor;
    private String name;

    public Author(String name) {
        this.name = name;
        this.idAuthor = idCurrent++;
    }

    public static int getIdCurrent() {
        return idCurrent;
    }

    public static void setIdCurrent(int idCurrent) {
        Author.idCurrent = idCurrent;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" 
                + "idAuthor=" + idAuthor 
                + ", name=" + name 
                + '}';
    }
    
    
    
}
