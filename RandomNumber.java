
import java.util.Random;
public class RandomNumber
{
    static void main(String[] args) {
        Random random = new Random();
        int number1;
        double number2;
        boolean coin;
      number1 = random.nextInt(1, 101);
      number2 = random.nextDouble(1, 101);
        coin = random.nextBoolean();

        System.out.println(number1);
        System.out.println(number2);
        System.out.println(coin);
        if(coin){
            System.out.println("HEADS");
        }
        else{
            System.out.println("TAILS");
        }
    }
}
