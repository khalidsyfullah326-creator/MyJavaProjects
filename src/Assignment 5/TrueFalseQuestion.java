public class TrueFalseQuestion extends Question {

    // Hardcoded constants — True/False options never change
    private static final String OPTION_A = "True";
    private static final String OPTION_B = "False";

    public TrueFalseQuestion(String questionText, AnswerOption correctAnswer, int points) {
        super(questionText, correctAnswer, points);
    }

    @Override
    public void displayQuestion() {
        System.out.println(questionText);
        System.out.println("A. " + OPTION_A);
        System.out.println("B. " + OPTION_B);
    }

    @Override
    public boolean evaluate(AnswerOption answer) throws Exception {
        // The object enforces its own rule — the game loop never needs to know this
        if (answer == AnswerOption.C || answer == AnswerOption.D) {
            throw new Exception("✗ Invalid option. True/False questions only accept A or B.");
        }
        return answer == correctAnswer;
    }
}
