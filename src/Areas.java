import java.util.Scanner;
public class Areas {
    public static void main(String[] args)
    {
        double width = 0;
        double length = 0;
        double area = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the value of Length: ");
        length = input.nextDouble();
        System.out.println("Enter the value of width: ");
        width = input.nextDouble();
        area = length * width;
        System.out.println("The area is "+area);

    }
}
