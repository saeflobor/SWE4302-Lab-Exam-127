import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Create a new order with a waffle cone
        Order order = new Order(Container.WAFFLE_CONE);

        // Add ice cream scoops
        order.addIceCream(Flavor.CHOCOLATE_FUDGE, 1);
        order.addIceCream(Flavor.MINT_CHOCOLATE_CHIP, 1);

        // Add toppings
        order.addTopping(Topping.CHOCOLATE_CHIPS, 1);
        order.addTopping(Topping.FRESH_STRAWBERRIES, 2);

        // Print invoice to console
        System.out.println(order.generateInvoice());

        // Save invoice to file
        try {
            InvoiceWriter.writeInvoice(order, "ice_cream_order");
        } catch (IOException e) {
            System.err.println("Error writing invoice: " + e.getMessage());
        }
    }
}
