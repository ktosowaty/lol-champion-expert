package pl.edu.wat.wcy;

import java.util.ArrayList;
import java.util.List;

public class Display {
    private String question;
    private QuestionType questionType;
    public List<String> variants;
    public String result;
    public boolean resultBool;

    public Display(String question, QuestionType questionType) {
        this.question = question;
        this.questionType = questionType;
        this.resultBool = false;
        this.variants = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setResult(String result, boolean resultBool) {
        this.result = result;
        this.resultBool = resultBool;
    }

    public void setResult(String question, String result, QuestionType QuestionType) {
        this.question = question;
        this.questionType = QuestionType;
        this.result = result;
    }

    public void setResult(String question, String result, QuestionType QuestionType, List<String> variants) {
        this.question = question;
        this.questionType = QuestionType;
        this.result = result;
        this.variants.clear();
        this.variants = variants;
    }
}
