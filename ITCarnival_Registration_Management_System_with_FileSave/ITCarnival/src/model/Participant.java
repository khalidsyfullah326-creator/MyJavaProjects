package model;

/**
 * Inheritance: Participant inherits from User.
 * A participant signs up with an ID, name, phone and email.
 */
public class Participant extends User {

    public Participant(int userId, String name, String email, String phone) {
        super(userId, name, email, phone); // super: calls the parent (User) constructor
    }

    // Constructor Overloading: a short version for quick creation
    public Participant(int userId, String name) {
        super(userId, name);
    }

    // Method Overriding
    @Override
    public String getRole() {
        return "Participant";
    }

    // Method Overriding: extends the parent's displayInfo()
    @Override
    public void displayInfo() {
        System.out.print("(Participant) ");
        super.displayInfo(); // super: reuse the parent's printing code
    }
}
