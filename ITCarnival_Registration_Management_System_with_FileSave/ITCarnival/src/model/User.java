package model;

/**
 * Base class for all users (Admin and Participant).
 * Inheritance: Admin and Participant extend this class.
 */
public class User {

    // Encapsulation: fields are private, so they can only be accessed through getters/setters
    private int userId;
    private String name;
    private String email;
    private String phone;

    public User(int userId, String name, String email, String phone) {
        // 'this' keyword: separates the field from the constructor parameter with the same name
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Constructor Overloading: same constructor name, different parameter list
    public User(int userId, String name) {
        this(userId, name, "N/A", "N/A"); // 'this(...)' calls the other constructor above
    }

    // Encapsulation: getters and setters
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    // Child classes override this method to return their own role name
    public String getRole() {
        return "User";
    }

    public void displayInfo() {
        System.out.println("ID: " + userId + " | Name: " + name
                + " | Email: " + email + " | Phone: " + phone);
    }

    // Method Overriding: replaces Object.toString()
    @Override
    public String toString() {
        return getRole() + "[" + userId + ", " + name + "]";
    }
}
