import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvoiceWriter {
    public static void writeInvoice(Order order, String fileName) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fullFileName = String.format("%s_%s.txt", fileName, timestamp);

        try (FileWriter writer = new FileWriter(fullFileName)) {
            writer.write(order.generateInvoice());
        }
    }
}
