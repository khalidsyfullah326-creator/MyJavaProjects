import java.util.Scanner;

public class ShopCheckout
{
    public static void main(String[] args)
    {

        Scanner input = new Scanner(System.in);
        System.out.println("---- Welcome to Khalid's Shop ----");
        System.out.print("Enter Product Name: ");
        String productName = input.nextLine();
        System.out.print("Enter Unit Price: ");
        double unitPrice = input.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = input.nextInt();
        final double DISCOUNT_RATE = 0.15; // 15%
        double subtotal = unitPrice * quantity;
        double discountAmount = subtotal * DISCOUNT_RATE;
        double finalPrice = subtotal - discountAmount;
        int roundedTotal = (int) finalPrice;
        System.out.println("\n---- Final Receipt ----");
        System.out.println("Item: " + productName);
        System.out.println("Subtotal: $" + subtotal);
        System.out.println("Discount Applied: $" + discountAmount);
        System.out.println("Total (Rounded Down): $" + roundedTotal);
        System.out.println("-------------------------");

        input.close();
    }
}