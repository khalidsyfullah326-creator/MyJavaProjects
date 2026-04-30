import java.util.Scanner;
public class MadLibsGame
{
   public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        String adjective1;
        String noun1;
        String adjective2;
        String verb;
        System.out.println("Enter an adjective(description): ");
        adjective1 = scan.nextLine();
        System.out.println("Enter a noun(animal or person): ");
        noun1 = scan.nextLine();
        System.out.println("Enter an adjective(description): ");
        adjective2 = scan.nextLine();
        System.out.println("Enter a verb with ing: ");
        verb = scan.nextLine();
        System.out.println("/n");
        System.out.println("Today I went to a "+adjective1+" zoo.");
        System.out.println("In an exibit,I saw "+noun1 +".");
        System.out.println(noun1+" was very "+adjective2+" and was "+verb);
     }
}
