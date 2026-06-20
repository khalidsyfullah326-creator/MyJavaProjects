import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ── Welcome screen ──────────────────────────────────────────────
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         JAVA OOP QUIZ GAME           ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("\nEnter your name: ");
        String playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) playerName = "Player";
        System.out.println("\nWelcome, " + playerName + "! Get ready for 10 questions.\n");

        // ── Question bank (topic: Java & OOP) ───────────────────────────
        ArrayList<Question> questionBank = new ArrayList<>();

        // MCQ questions
        questionBank.add(new MCQQuestion(
            "Which keyword is used to inherit a class in Java?",
            AnswerOption.B,
            "implements", "extends", "inherits", "super", 10));

        questionBank.add(new MCQQuestion(
            "Which of the following is NOT a pillar of OOP?",
            AnswerOption.D,
            "Encapsulation", "Inheritance", "Polymorphism", "Compilation", 10));

        questionBank.add(new MCQQuestion(
            "What does the 'super' keyword do in a constructor?",
            AnswerOption.A,
            "Calls the parent class constructor",
            "Creates a new object",
            "Overrides the parent method",
            "Accesses a static field", 10));

        questionBank.add(new MCQQuestion(
            "Which access modifier makes a field visible only within its own class?",
            AnswerOption.C,
            "public", "protected", "private", "default", 10));

        questionBank.add(new MCQQuestion(
            "What is method overloading?",
            AnswerOption.B,
            "A child class redefines a parent method",
            "Multiple methods share the same name but different parameters",
            "A method calls itself recursively",
            "A method is declared in an interface", 10));

        questionBank.add(new MCQQuestion(
            "Which of these correctly declares an abstract method?",
            AnswerOption.A,
            "abstract void display();",
            "void abstract display();",
            "abstract void display() {}",
            "void display() abstract;", 10));

        questionBank.add(new MCQQuestion(
            "What does 'polymorphism' literally mean?",
            AnswerOption.D,
            "One class", "Many classes", "Single form", "Many forms", 10));

        questionBank.add(new MCQQuestion(
            "Which collection type automatically sorts its elements?",
            AnswerOption.C,
            "ArrayList", "LinkedList", "TreeSet", "HashMap", 10));

        questionBank.add(new MCQQuestion(
            "What is the return type of a constructor?",
            AnswerOption.B,
            "void", "None — constructors have no return type",
            "The class type", "int", 10));

        questionBank.add(new MCQQuestion(
            "Which exception is thrown when you access a null reference?",
            AnswerOption.A,
            "NullPointerException",
            "ArrayIndexOutOfBoundsException",
            "ClassCastException",
            "IllegalArgumentException", 10));

        questionBank.add(new MCQQuestion(
            "What does the 'final' keyword do when applied to a method?",
            AnswerOption.C,
            "Makes the method run faster",
            "Makes the method private",
            "Prevents the method from being overridden",
            "Makes the method static", 10));

        questionBank.add(new MCQQuestion(
            "Which keyword is used to handle exceptions in Java?",
            AnswerOption.B,
            "throws", "try", "catch", "handle", 10));

        // True/False questions
        questionBank.add(new TrueFalseQuestion(
            "An abstract class can be instantiated directly.", AnswerOption.B, 10));

        questionBank.add(new TrueFalseQuestion(
            "Java supports multiple class inheritance.", AnswerOption.B, 10));

        questionBank.add(new TrueFalseQuestion(
            "A subclass can override a final method.", AnswerOption.B, 10));

        questionBank.add(new TrueFalseQuestion(
            "The 'this' keyword refers to the current object instance.", AnswerOption.A, 10));

        questionBank.add(new TrueFalseQuestion(
            "In Java, every class implicitly extends Object.", AnswerOption.A, 10));

        questionBank.add(new TrueFalseQuestion(
            "A checked exception must be handled or declared with 'throws'.", AnswerOption.A, 10));

        questionBank.add(new TrueFalseQuestion(
            "Static methods can be overridden in a subclass.", AnswerOption.B, 10));

        questionBank.add(new TrueFalseQuestion(
            "An interface can contain concrete method implementations using the 'default' keyword.",
            AnswerOption.A, 10));

        questionBank.add(new TrueFalseQuestion(
            "Method overloading is an example of runtime polymorphism.", AnswerOption.B, 10));

        questionBank.add(new TrueFalseQuestion(
            "The 'protected' access modifier allows access from subclasses in different packages.",
            AnswerOption.A, 10));

        // ── Select 10 random questions ───────────────────────────────────
        Collections.shuffle(questionBank);
        ArrayList<Question> selected = new ArrayList<>(questionBank.subList(0, 10));

        // ── Score tracking ───────────────────────────────────────────────
        int score   = 0;
        int correct = 0;
        int incorrect = 0;

        // ── Game loop ────────────────────────────────────────────────────
        for (int i = 0; i < selected.size(); i++) {
            Question q = selected.get(i);

            System.out.println("\n════════════════════════════════════════");
            System.out.printf("Question %d of 10 | Score: %d pts%n", i + 1, score);
            System.out.println("════════════════════════════════════════");
            q.displayQuestion();
            System.out.println();

            // Answer loop — keeps asking until a valid, accepted answer is given
            while (true) {
                System.out.print("Your answer: ");
                String raw = scanner.nextLine().trim();
                AnswerOption parsed = parseInput(raw);

                if (parsed == null) {
                    System.out.println("✗ Unrecognized input. Please enter A, B, C, or D.\n");
                    continue;
                }

                try {
                    boolean result = q.evaluate(parsed);
                    if (result) {
                        score += q.getPoints();
                        correct++;
                        System.out.println("✓ Correct! +" + q.getPoints() + " pts");
                    } else {
                        incorrect++;
                        System.out.println("✗ Wrong! The correct answer was: " + q.getCorrectAnswer());
                    }
                    break; // valid answer processed — move to next question

                } catch (Exception e) {
                    // TrueFalseQuestion threw because C or D was entered
                    System.out.println(e.getMessage());
                    System.out.println();
                    // Loop continues — re-prompt same question
                }
            }
        }

        scanner.close();

        // ── Final screen ─────────────────────────────────────────────────
        String rank = getRank(score);
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           QUIZ COMPLETE!             ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Player : %-27s║%n", playerName);
        System.out.printf( "║  Score  : %-3d / 100                  ║%n", score);
        System.out.printf( "║  Correct: %-2d  | Wrong: %-2d             ║%n", correct, incorrect);
        System.out.printf( "║  Rank   : %-27s║%n", rank);
        System.out.println("╚══════════════════════════════════════╝");
    }

    // ── Helper: parse raw input to AnswerOption ──────────────────────────
    static AnswerOption parseInput(String input) {
        if (input == null || input.isEmpty()) return null;
        switch (input.trim().toUpperCase()) {
            case "A": return AnswerOption.A;
            case "B": return AnswerOption.B;
            case "C": return AnswerOption.C;
            case "D": return AnswerOption.D;
            default:  return null;
        }
    }

    // ── Helper: map score to star rank ───────────────────────────────────
    static String getRank(int score) {
        if (score >= 90) return "★★★★★ Excellent!";
        if (score >= 70) return "★★★★☆ Great job!";
        if (score >= 50) return "★★★☆☆ Not bad!";
        if (score >= 30) return "★★☆☆☆ Keep practicing!";
        return             "★☆☆☆☆ Better luck next time!";
    }
}
