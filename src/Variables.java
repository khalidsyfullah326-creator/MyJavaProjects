public class Variables {
    public static void main(String[] args) {
        int age = 21;
        double cgp = 3.46;
        char grade = 'A';
        char currency = '$';
        double price = 36;
        boolean isStudent = true;
        boolean cgpaLine = false;
        boolean isOnline = true;
        String name = "Khalid";
        String colour = "Blue";
        String semester = "1st semester";
        System.out.println("Hi " + name);
        System.out.println("You are " + age + " years old.");
        System.out.println("Your favourite colour is " + colour);
        System.out.println(isStudent);
        System.out.println("Your " + semester + " CGP is " + cgp);
        System.out.println("Your grade is " + grade);
        System.out.println(cgpaLine);
        System.out.println("The price for your next semester enrollment fee is " + currency + price);
        if (isStudent) {
            System.out.println("You are a regular student of PSTU");
        } else {
            System.out.println("You are irregular");

        }
    }
}
