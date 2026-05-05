class Order {

    // ATTRIBUTES
    public String productName;       // public by choice
    private double unitPrice;
    private int quantity;

    // CONSTRUCTOR
    public Order(String productName, double unitPrice, int quantity) throws Exception {
        if (unitPrice <= 0) {
            throw new Exception("Unit price must be greater than zero.");
        }
        if (quantity <= 0) {
            throw new Exception("Quantity must be greater than zero.");
        }
        this.productName = productName;
        this.unitPrice   = unitPrice;
        this.quantity    = quantity;
    }

    // SETTERS WITH VALIDATION
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

    // GETTERS (good practice alongside private fields)
    public double getUnitPrice() { return unitPrice; }
    public int    getQuantity()  { return quantity;  }

    // CALCULATE ITEM TOTAL
    public double calculateTotal() {
        return unitPrice * quantity;
    }

    // TOSTRING
    @Override
    public String toString() {
        // e.g. "Gaming Mouse - 3 pcs x 25.75 = 77.25"
        return productName + " - " + quantity + " pcs x " + unitPrice + " = " + calculateTotal();
    }

    // MAIN — test driver
    public static void main(String[] args) {
        System.out.println("--- Order Test ---");

        try {
            Order order1 = new Order("Gaming Mouse", 25.75, 3);
            Order order2 = new Order("Mechanical Keyboard", 120.0, 1);

            System.out.println(order1);
            System.out.println(order2);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

        System.out.println("\n--- Invalid Order Attempt ---");

        try {
            Order badOrder = new Order("Broken Item", -5.0, 0);
            System.out.println(badOrder);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
