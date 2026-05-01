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
        System.out.println("The area of the rectangle is " +area +" cm²");
        System.out.println("Enter the radious in cm : ");
        double radious = input.nextDouble();
        double circleArea = Math.PI * Math.pow(radious, 2);
        System.out.println("The are of circle is "+circleArea+" cm²");
    }
}
