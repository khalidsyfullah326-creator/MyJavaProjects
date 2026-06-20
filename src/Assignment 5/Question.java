public abstract class Question {

    protected String questionText;
    protected AnswerOption correctAnswer;
    protected int points;

    public Question(String questionText, AnswerOption correctAnswer, int points) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }

    // Abstract — each subclass defines its own display and evaluation logic
    public abstract void displayQuestion();
    public abstract boolean evaluate(AnswerOption answer) throws Exception;

    // Concrete — shared by all subclasses
    public int getPoints() {
        return points;
    }

    public AnswerOption getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        return questionText;
    }
}
