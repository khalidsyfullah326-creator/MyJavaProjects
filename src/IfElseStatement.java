import java.util.Scanner;
public class IfElseStatement {
    static void main(String[] args) {
        System.out.println("Enter your age: ");
        Scanner input = new Scanner(System.in);
        int age = input.nextInt();
        if(age>=18 && age<40)
        {
            System.out.println("You are an adult");
        }
        else if(age>=12 && age<18)
        {
            System.out.println("You are a teenager");
        }
        else if(age>=40 && age<75)
        {
            System.out.println("You are a grown man");
        }
        else if(age<=11)
        {
            System.out.println("You are a child");
        }
        else
        {
            System.out.println("You are old");
        }
    }
}
