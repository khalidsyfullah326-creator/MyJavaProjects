package model;

/**
 * Inheritance: Admin inherits from User.
 */
public class Admin extends User {

    public Admin(int userId, String name, String email, String phone) {
        super(userId, name, email, phone); // super: calls the parent (User) constructor
    }

    // Method Overriding: Admin gives its own version of getRole()
    @Override
    public String getRole() {
        return "Admin";
    }
}
