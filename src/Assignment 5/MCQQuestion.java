public class MCQQuestion extends Question {

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public MCQQuestion(String questionText, AnswerOption correctAnswer,
                       String optionA, String optionB, String optionC, String optionD,
                       int points) {
        super(questionText, correctAnswer, points);
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    @Override
    public void displayQuestion() {
        System.out.println(questionText);
        System.out.println("A. " + optionA);
        System.out.println("B. " + optionB);
        System.out.println("C. " + optionC);
        System.out.println("D. " + optionD);
    }

    @Override
    public boolean evaluate(AnswerOption answer) {
        return answer == correctAnswer;
    }
}
