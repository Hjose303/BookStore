package ec.edu.espe.bookstoreapp.exception;

/**
 *
 * @author Julian Olalla, 404 Founders, @ESPE
 */
public class EmptyInventoryException extends Exception {
    public EmptyInventoryException(String message) {
        super(message);
    }
}
