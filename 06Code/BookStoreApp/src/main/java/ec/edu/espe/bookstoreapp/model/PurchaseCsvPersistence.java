package ec.edu.espe.bookstoreapp.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Persistencia para reportes de compras basados en JSON.
 * @author ESPE
 */
public class PurchaseCsvPersistence {

    private static final Path REPORT_DIRECTORY = Path.of("reports");
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    public static Path generatePurchaseReport(List<Purchase> purchases) throws IOException {

        if (purchases == null || purchases.isEmpty()) {
            throw new IllegalArgumentException("La lista de compras no puede estar vacía.");
        }

        Files.createDirectories(REPORT_DIRECTORY);

        String fileName = "reporte_compras_" + LocalDateTime.now().format(FILE_FORMAT) + ".csv";
        Path file = REPORT_DIRECTORY.resolve(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(
                file,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE)) {

            // BOM para compatibilidad con Excel (Uso de tildes)
            writer.write('\uFEFF');

            // Configuración del separador para Excel
            writer.write("sep=;");
            writer.newLine();

            writer.write("REPORTE HISTORICO DE COMPRAS - ADQUISICIONES");
            writer.newLine();
            writer.newLine();

            // Encabezados de columnas
            writer.write("Tipo de Producto;Codigo;Producto;Cantidad;Precio de Compra;Subtotal;Proveedor;Fecha Compra");
            writer.newLine();

            float grandTotalPurchases = 0.0f;

            // Recorrido de los bloques del JSON
            for (Purchase purchase : purchases) {
                String supplierName = purchase.getSupplier() != null ? purchase.getSupplier().getNameSupplier() : "Desconocido";
                LocalDate purchaseDate = purchase.getDate();
                
                // Acumulamos el valor total asignado a la compra en el JSON
                grandTotalPurchases += purchase.getTotalPurchase();

                for (PurchaseDetail detail : purchase.getDetails()) {
                    String productType;
                    String productCode;
                    String productName;

                    if (detail.getBook() != null) {
                        Book book = detail.getBook();
                        productType = "Libro";
                        productCode = String.valueOf(book.getIdBook());
                        productName = book.getTitle();
                    } else if (detail.getJournal() != null) {
                        Journal journal = detail.getJournal();
                        productType = "Revista";
                        productCode = String.valueOf(journal.getIdJournal());
                        productName = journal.getNameJournal();
                    } else {
                        productType = "Desconocido";
                        productCode = "N/A";
                        productName = "Detalle vacío";
                    }

                    // Escritura de la fila del artículo comprado
                    writer.write(
                            csv(productType) + ";"
                            + csv(productCode) + ";"
                            + csv(productName) + ";"
                            + detail.getStockPurchase() + ";"
                            + money(detail.getPurchasePrice()) + ";"
                            + money(detail.computeSubtotal()) + ";"
                            + csv(supplierName) + ";"
                            + csv(purchaseDate.toString())
                    );
                    writer.newLine();
                }
            }

            writer.newLine();
            // LÍNEA FINAL CLAVE: Evidencia el Gran Total consolidado de todas las compras del archivo JSON
            writer.write("TOTAL DE LAS COMPRAS;;;;;" + money(grandTotalPurchases) + ";;");
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

