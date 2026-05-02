import java.util.Scanner;
public class Project2ShopCheckout {
   public static void main() {
       System.out.println("-----WELCOME TO KHALID'S SHOP-----");
       System.out.println(" ");
       System.out.println("How many items do you want to purchase?(max 5)");
       int ItemNumber;
       Scanner input = new Scanner(System.in);
       ItemNumber = input.nextInt();
       if(ItemNumber>5)
       {
           System.out.println("Reached Maximum item limit");
           ItemNumber = 5;
       }
       String[] productNames = new String[ItemNumber];
       double[] prices = new double[ItemNumber];
       int[] quantities = new int[ItemNumber];
       for(int i=0; i<ItemNumber; i++)
       {
           input.nextLine();
           System.out.println("\n");
           System.out.println("--- Item"+(i+1)+" ---");
           System.out.println("Enter product "+(i+1)+" name: ");
           productNames[i] = input.nextLine();
           System.out.println("\n");
           System.out.println("Enter the product " +(i+1)+" price: ");
           double price = input.nextDouble();
           if(price<=0)
           {
               System.out.println("Error: Price must be greater than 0. Skipping this item.");
               input.nextLine(); // Consume newline
               i--; // Decrement counter to redo this index
               continue;
           }
           System.out.println("Enter quantities: ");
           int quantity = input.nextInt();
           if (quantity <= 0)
           {
               System.out.println("Error: Quantity must be greater than 0. Skipping this item.");
               i--; // Decrement counter to redo this index
               continue;
           }
           prices[i] = price;
           quantities[i] = quantity;

       }

       double subTotal = 0;
       double[] itemTotal = new double[ItemNumber];
       for(int i=0; i< ItemNumber; i++)
       {
           itemTotal[i] = prices[i] * quantities[i];
           subTotal += itemTotal[i];
       }
       double discountPercentage = 0;
       double discountAmount = 0;

       if (subTotal > 1000) {
           discountPercentage = 20;
           discountAmount = subTotal * 0.20;
       } else if (subTotal > 500) {
           discountPercentage = 10;
           discountAmount = subTotal * 0.10;
       } else {
           discountPercentage = 5;
           discountAmount = subTotal * 0.05;
       }
       double deliveryFee = (subTotal < 500) ? 50:0;
       double finalTotal = subTotal - discountAmount - deliveryFee;
       System.out.println("\n***--- Final Receipt ---***");

       for(int i=0; i<ItemNumber; i++)
       {
           System.out.println("Item "+(i+1)+": "+productNames[i]+" - "+quantities[i]+ " * " +prices[i]+" = " +itemTotal[i]);

       }

       System.out.println("Subtotal: " +subTotal);
       System.out.println("Discount Applied: " +discountAmount +" " +discountPercentage);
       System.out.println("Delivery Fee: " +deliveryFee);
       System.out.println("Final Total: " +finalTotal);
       System.out.println("Thanks for purchasing");

   }
}
