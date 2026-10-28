
package ec.edu.espe.bookstoreapp.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author Nestor Olivo, Found ERROR 404, @ESPE
 */
public class InvoiceCsvPersistence {

    private static final Path INVOICE_DIRECTORY =
            Path.of("invoices");

    private static final DateTimeFormatter FILE_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    public static Path generateInvoice(Sale sale) throws IOException {

        if (sale == null) {
            throw new IllegalArgumentException(
                    "La venta no puede ser null.");
        }

        if (sale.getDetails() == null || sale.getDetails().isEmpty()) {
            throw new IllegalArgumentException(
                    "No se puede generar una factura sin productos.");
        }

        if (sale.getPayment() == null) {
            throw new IllegalArgumentException(
                    "La venta todavía no tiene un pago registrado.");
        }

        Files.createDirectories(INVOICE_DIRECTORY);

        String fileName = "factura_"
                + sale.getIdSale() + "_"
                + LocalDateTime.now().format(FILE_FORMAT)
                + ".csv";

        Path file = INVOICE_DIRECTORY.resolve(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(
                file,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE)) {

            // BOM para mejorar la compatibilidad con Excel.
            writer.write('\uFEFF');

            writer.write("LIBRERIA OLIVO CULTURAL");
            writer.newLine();

            writer.write("Numero de venta;" + sale.getIdSale());
            writer.newLine();

            writer.write("Fecha;" + sale.getDate());
            writer.newLine();

            String customerName = sale.getCustomer() != null
                    ? sale.getCustomer().getName()
                    : "Consumidor Final";

            writer.write("Cliente;" + csv(customerName));
            writer.newLine();

            writer.write("Tipo de venta;" + csv(sale.getTypeSale()));
            writer.newLine();

            writer.write("Metodo de pago;"
                    + csv(sale.getPayment().getPaymentMethod()
                            .getNamePaymentMethod()));
            writer.newLine();

            writer.newLine();

            writer.write(
                    "Tipo de producto;Codigo;Producto;"
                    + "Cantidad;Precio unitario;Subtotal");
            writer.newLine();

            for (SaleDetail detail : sale.getDetails()) {

                String productType;
                String productCode;
                String productName;
                float unitPrice;

                if (detail.getBook() != null) {

                    Book book = detail.getBook();

                    productType = "Libro";
                    productCode = String.valueOf(book.getIdBook());
                    productName = book.getTitle();
                    unitPrice = book.getPrice();

                } else if (detail.getJournal() != null) {

                    Journal journal = detail.getJournal();

                    productType = "Revista";
                    productCode = String.valueOf(
                            journal.getIdJournal());
                    productName = journal.getNameJournal();
                    unitPrice = journal.getPrice();

                } else {
                    throw new IllegalArgumentException(
                            "Hay un detalle sin producto asociado.");
                }

                writer.write(
                        csv(productType) + ";"
                        + csv(productCode) + ";"
                        + csv(productName) + ";"
                        + detail.getQuantityProduct() + ";"
                        + money(unitPrice) + ";"
                        + money(detail.computeSubtotal()));

                writer.newLine();
            }

            writer.newLine();

            float total = sale.computeTotal();
            float received = sale.getPayment().getAmountPayment();
            float change = received - total;

            writer.write("TOTAL DE LA VENTA;;;;;"
                    + money(total));
            writer.newLine();

            writer.write("Monto recibido;" + money(received));
            writer.newLine();

            writer.write("Cambio;" + money(change));
            writer.newLine();

        }

        return file;
    }

    private static String csv(String value) {
        if (value == null) {
            return "\"\"";
        }

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private static String money(float value) {
        return String.format(Locale.US, "%.2f", value);
    }
}
