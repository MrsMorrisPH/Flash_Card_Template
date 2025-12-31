import java.util.*;

public class FlashCard {
    private ArrayList<String> questions;
    private ArrayList<String> answers;

    public FlashCard() {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
    }

    public void addQA(String question, String answer) {
        questions.add(question);
        answers.add(answer);
    }

    public String getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }

    public String getAnswer(int index) {
        if (index >= 0 && index < answers.size()) {
            return answers.get(index);
        }
        return null;
    }

    public int getSize() {
        return questions.size();
    }
}
