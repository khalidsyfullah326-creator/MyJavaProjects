import java.util.Scanner;
public class GagetShop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Welcome to Khalid's Shop---");

        // Step 1: Ask Number of Items (Loop Control)
        System.out.print("How many items do you want to purchase?(Max 5 items): ");
        int numItems = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Limit to max 5 items
        if (numItems > 5) {
            System.out.println("Maximum 5 items allowed. Limiting to 5 items.");
            numItems = 5;
        }

        // Step 2: Store Data (Arrays)
        String[] productNames = new String[numItems];
        double[] prices = new double[numItems];
        int[] quantities = new int[numItems];

        // Step 3: Take Inputs (Loop)
        for (int i = 0; i < numItems; i++) {
            System.out.println("\n--- Item " + (i + 1) + " ---");

            System.out.print("Enter Product " + (i + 1) + " Name: ");
            productNames[i] = scanner.nextLine();

            System.out.print("Enter Price: ");
            double price = scanner.nextDouble();

            // Step 4: Input Validation (Conditionals + Continue)
            if (price <= 0) {
                System.out.println("Error: Price must be greater than 0. Skipping this item.");
                scanner.nextLine(); // Consume newline
                i--; // Decrement counter to redo this index
                continue;
            }

            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (quantity <= 0) {
                System.out.println("Error: Quantity must be greater than 0. Skipping this item.");
                i--; // Decrement counter to redo this index
                continue;
            }

            // Store valid values
            prices[i] = price;
            quantities[i] = quantity;
        }

        // Step 5: Calculate Totals (Loop)
        double subtotal = 0;
        double[] itemTotals = new double[numItems];

        for (int i = 0; i < numItems; i++) {
            itemTotals[i] = prices[i] * quantities[i];
            subtotal += itemTotals[i];
        }

        // Step 6: Discount Logic (if-else)
        double discountPercentage = 0;
        double discountAmount = 0;

        if (subtotal > 100) {
            discountPercentage = 20;
            discountAmount = subtotal * 0.20;
        } else if (subtotal > 50) {
            discountPercentage = 10;
            discountAmount = subtotal * 0.10;
        } else {
            discountPercentage = 5;
            discountAmount = subtotal * 0.05;
        }

        // Step 7: Delivery Fee (Ternary Operator)
        double deliveryFee = (subtotal < 50) ? 10 : 0;

        // Step 8: Final Total
        double finalTotal = subtotal - discountAmount + deliveryFee;

        // Step 9: Final Receipt (Loop)
        System.out.println("\n*****--- Final Receipt--");

        for (int i = 0; i < numItems; i++) {
            System.out.printf("Item %d: %s - %d x %.0f = %.0f%n",
                    (i + 1),
                    productNames[i],
                    quantities[i],
                    prices[i],
                    itemTotals[i]
            );
        }

        System.out.printf("Subtotal: %.0f%n", subtotal);
        System.out.printf("Discount Applied: %.0f (%.0f%%)%n", discountAmount, discountPercentage);
        System.out.printf("Delivery Fee: %.0f%n", deliveryFee);
        System.out.printf("Final Total: %.0f%n", finalTotal);

        scanner.close();
    }
}