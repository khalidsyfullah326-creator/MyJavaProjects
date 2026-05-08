import java.util.ArrayList;
import java.util.Scanner;

public class Order {
    // Attributes
    public String productName;
    private double unitPrice;
    private int quantity;
    public Order(String productName, double unitPrice, int quantity) throws Exception {
        this.productName = productName;
        setUnitPrice(unitPrice);
        setQuantity(quantity);
    }
    public void setUnitPrice(double price) throws Exception {
        if (price <= 0) {
            throw new Exception("Unit price must be greater than zero.");
        }
        this.unitPrice = price;
    }
    public void setQuantity(int qty) throws Exception {
        if (qty <= 0) {
            throw new Exception("Quantity must be greater than zero.");
        }
        this.quantity = qty;
    }
    public double calculateTotal() {
        return unitPrice * quantity;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public int getQuantity() {
        return quantity;
    }
    @Override
    public String toString() {
        return String.format("%s - %d pcs x %.1f = %.1f",
                productName, quantity, unitPrice, calculateTotal());
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Order> orders = new ArrayList<>();

        System.out.println("--- Welcome to Khalid's Shop ---");
        System.out.print("How many items do you want to purchase? ");
        int numItems = scanner.nextInt();
        scanner.nextLine();

        if (numItems > 5) {
            System.out.println("Maximum 5 items allowed. Setting to 5 items.");
            numItems = 5;
        }
        for (int i = 1; i <= numItems; i++) {
            System.out.println("\nEnter Product " + i + " Name: ");
            String productName = scanner.nextLine();

            System.out.print("Enter Price: ");
            double price = scanner.nextDouble();

            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            try {
                Order order = new Order(productName, price, quantity);
                orders.add(order);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + " Skipping item.");
            }
        }

        double subtotal = 0;
        for (Order order : orders) {
            subtotal += order.calculateTotal();
        }
        double discountPercent = 0;
        if (subtotal > 100) {
            discountPercent = 20;
        } else if (subtotal > 50) {
            discountPercent = 10;
        } else {
            discountPercent = 5;
        }
        double discountAmount = subtotal * (discountPercent / 100);
        double deliveryFee = (subtotal < 50) ? 10.0 : 0.0;
        double finalTotal = subtotal - discountAmount + deliveryFee;
        System.out.println("\n--- Final Receipt ---");
        int itemNumber = 1;
        for (Order order : orders) {
            System.out.println("Item " + itemNumber + ": " + order);
            itemNumber++;
        }
        System.out.printf("Subtotal: %.1f\n", subtotal);
        System.out.printf("Discount Applied (%.0f%%): %.1f\n", discountPercent, discountAmount);
        System.out.printf("Delivery Fee: %.1f\n", deliveryFee);
        System.out.printf("Final Total: %.1f\n", finalTotal);
        System.out.println("--------------------------");

        scanner.close();
    }
}
