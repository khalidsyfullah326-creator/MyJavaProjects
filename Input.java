import java.util.Scanner;
public class Input {

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = input.nextLine();
        System.out.println("Your name is "+name);
        System.out.println("Enter your age: ");
        int age = input.nextInt();
        System.out.println("Your age is " +age);
        System.out.println("Enter your CGP: ");
        double cgp = input.nextDouble();
        System.out.println("Your cgp is "+cgp);
        System.out.println("Are you a regular student? (True/False):");
        boolean isStudent = input.nextBoolean();
        if(isStudent)
        {
            System.out.println("You are a student");
        }
        else{
            System.out.println("You are not a student");
        }


        input.close();
    }
}
