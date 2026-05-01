import java.util.Scanner;
public class Hypotenuse {
    static void main(String[] args) {

        double a, b, c;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the value of a and b: ");
        a = input.nextDouble();
        b = input.nextDouble();
        c = Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
        System.out.println("The Hypotenuse is "+c);

    }
}