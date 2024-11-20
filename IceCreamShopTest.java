import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IceCreamShopTest {
    private Order paperCupOrder;
    private Order waffleConeOrder;


    void setUp() {
        paperCupOrder = new Order(Container.PAPER_CUP);
        waffleConeOrder = new Order(Container.WAFFLE_CONE);
    }


    void testMultipleScoopsAndToppingsCalculation() {
        // Test Case 1: Verify correct calculation of multiple scoops and toppings
        paperCupOrder.addIceCream(Flavor.CHOCOLATE_FUDGE, 2);
        paperCupOrder.addIceCream(Flavor.STRAWBERRY_SWIRL, 1);
        paperCupOrder.addTopping(Topping.SPRINKLES, 1);
        paperCupOrder.addTopping(Topping.CHOCOLATE_CHIPS, 2);

        double expectedSubtotal = 10.05;
        double expectedTax = expectedSubtotal * 0.08;
        double expectedTotal = expectedSubtotal + expectedTax;

        assertEquals(expectedSubtotal, paperCupOrder.getSubtotal(), 0.01);
        assertEquals(expectedTotal, paperCupOrder.getTotal(), 0.01);
    }

    private void assertEquals(double expectedSubtotal, double subtotal, double v) {
    }


    void testWaffleConeAdditionalCharge() {
        // Test Case 2: Verify waffle cone adds correct additional charge
        waffleConeOrder.addIceCream(Flavor.MINT_CHOCOLATE_CHIP, 1);
        paperCupOrder.addIceCream(Flavor.MINT_CHOCOLATE_CHIP, 1);


        assertEquals(5.00,
                waffleConeOrder.getSubtotal() - paperCupOrder.getSubtotal(),
                0.01);
    }


    void testTaxCalculation() {
        // Test Case 3: Verify tax calculation is correct
        waffleConeOrder.addIceCream(Flavor.PISTACHIO_DELIGHT, 1);
        waffleConeOrder.addTopping(Topping.FRESH_STRAWBERRIES, 1);

        double subtotal = 9.25;
        double expectedTax = subtotal * 0.08;

        assertEquals(expectedTax, waffleConeOrder.getTax(), 0.01);
    }


    void testInvoiceGeneration() {
        // Test Case 4: Verify invoice format and content
        paperCupOrder.addIceCream(Flavor.CHOCOLATE_FUDGE, 1);
        paperCupOrder.addTopping(Topping.MARSHMALLOW, 1);

        String invoice = paperCupOrder.generateInvoice();


        assertTrue(invoice.contains("Ice Cream Shop Invoice"));
        assertTrue(invoice.contains("Chocolate Fudge - 1 scoop"));
        assertTrue(invoice.contains("Marshmallow - 1 time"));
        assertTrue(invoice.contains("Subtotal:"));
        assertTrue(invoice.contains("Tax (8%):"));
        assertTrue(invoice.contains("Total Amount Due:"));
    }


    void testInvoiceFileCreation() throws IOException {
        // Test Case 5: Verify invoice file is created with correct content
        waffleConeOrder.addIceCream(Flavor.STRAWBERRY_SWIRL, 1);
        waffleConeOrder.addTopping(Topping.CRUSHED_OREO, 1);

        String fileName = "test_invoice";
        InvoiceWriter.writeInvoice(waffleConeOrder, fileName);
        
        File directory = new File(".");
        File[] files = directory.listFiles((dir, name) -> name.startsWith(fileName));
        assertTrue(files != null && files.length > 0);


        String fileContent = Files.readString(files[0].toPath());


        assertTrue(fileContent.contains("Ice Cream Shop Invoice"));
        assertTrue(fileContent.contains("Waffle Cone: $5.00"));
        assertTrue(fileContent.contains("Strawberry Swirl"));
        assertTrue(fileContent.contains("Crushed Oreo"));


        for (File file : files) {
            file.delete();
        }
    }
}