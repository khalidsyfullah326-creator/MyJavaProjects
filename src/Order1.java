
import java.util.Scanner;
import java.util.ArrayList;

public class Order1 {
        // Attributes
        public String productName;  // Can be public as per requirements
        private double unitPrice;
        private int quantity;

        // Constructor with validation
        public Order1(String productName, double unitPrice, int quantity) throws Exception {
            this.productName = productName;
            setUnitPrice(unitPrice);
            setQuantity(quantity);
        }

        // Setter for unit price with validation
        public void setUnitPrice(double price) throws Exception {
            if (price <= 0) {
                throw new Exception("Unit price must be greater than zero.");
            }
            this.unitPrice = price;
        }

        // Setter for quantity with validation
        public void setQuantity(int qty) throws Exception {
            if (qty <= 0) {
                throw new Exception("Quantity must be greater than zero.");
            }
            this.quantity = qty;
        }

        // Method to calculate item total
        public double calculateTotal() {
            return unitPrice * quantity;
        }

        // Getter methods (optional but helpful)
        public double getUnitPrice() {
            return unitPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        // Override toString method
        @Override
        public String toString() {
            return String.format("%s - %d pcs x %.2f = %.2f",
                    productName, quantity, unitPrice, calculateTotal());
        }

        // Main method for testing with user input
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            ArrayList<Order> orders = new ArrayList<>();

            System.out.println("=== Shop Checkout System ===");
            System.out.println("Enter product orders (or type 'done' to finish)\n");

            // Keep taking orders until user types 'done'
            while (true) {
                System.out.print("Enter product name (or 'done' to finish): ");
                String productName = scanner.nextLine();

                if (productName.equalsIgnoreCase("done")) {
                    break;
                }

                // Get unit price with validation
                double unitPrice = 0;
                while (true) {
                    System.out.print("Enter unit price for " + productName + ": ");
                    try {
                        unitPrice = Double.parseDouble(scanner.nextLine());
                        if (unitPrice > 0) {
                            break;
                        } else {
                            System.out.println("Error: Unit price must be greater than zero. Please try again.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter a valid number for price.");
                    }
                }

                // Get quantity with validation
                int quantity = 0;
                while (true) {
                    System.out.print("Enter quantity for " + productName + ": ");
                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                        if (quantity > 0) {
                            break;
                        } else {
                            System.out.println("Error: Quantity must be greater than zero. Please try again.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter a valid integer for quantity.");
                    }
                }

                // Create the order with validated inputs
                try {
                    Order order = new Order(productName, unitPrice, quantity);
                    orders.add(order);
                    System.out.println("✓ Order added successfully!\n");
                } catch (Exception e) {
                    System.out.println("Error creating order: " + e.getMessage() + "\n");
                }
            }

            // Display all orders
            System.out.println("\n" + "=".repeat(50));
            System.out.println("--- All Valid Orders ---");

            if (orders.isEmpty()) {
                System.out.println("No orders were created.");
            } else {
                double grandTotal = 0;
                for (int i = 0; i < orders.size(); i++) {
                    Order order = orders.get(i);
                    System.out.println((i + 1) + ". " + order);
                    grandTotal += order.calculateTotal();
                }
                System.out.println("-".repeat(50));
                System.out.printf("GRAND TOTAL: %.2f\n", grandTotal);
            }

            // Demonstrate invalid order attempt (testing the validation)
            System.out.println("\n" + "=".repeat(50));
            System.out.println("--- Invalid Order Demonstration ---");

            // Attempt to create an order with negative price
            System.out.println("\nAttempting to create order with negative price (-15.99):");
            try {
                Order invalidOrder1 = new Order("Test Product", -15.99, 2);
                System.out.println("This should not print!");
            } catch (Exception e) {
                System.out.println("✓ Caught error: " + e.getMessage());
            }

            // Attempt to create an order with zero quantity
            System.out.println("\nAttempting to create order with zero quantity (0):");
            try {
                Order invalidOrder2 = new Order("Test Product", 10.50, 0);
                System.out.println("This should not print!");
            } catch (Exception e) {
                System.out.println("✓ Caught error: " + e.getMessage());
            }

            // Demonstrate setter validation if user wants to modify an order
            if (!orders.isEmpty()) {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("--- Setter Validation Demonstration ---");
                System.out.println("Attempting to modify the first order with invalid values:");

                Order firstOrder = orders.get(0);
                System.out.println("Original order: " + firstOrder);

                // Try to set invalid price
                try {
                    firstOrder.setUnitPrice(-20.00);
                } catch (Exception e) {
                    System.out.println("✓ Failed to update price: " + e.getMessage());
                }

                // Try to set invalid quantity
                try {
                    firstOrder.setQuantity(-3);
                } catch (Exception e) {
                    System.out.println("✓ Failed to update quantity: " + e.getMessage());
                }

                System.out.println("Order unchanged: " + firstOrder);
            }

            scanner.close();
            System.out.println("\n=== Program Ended ===");
        }
    }
