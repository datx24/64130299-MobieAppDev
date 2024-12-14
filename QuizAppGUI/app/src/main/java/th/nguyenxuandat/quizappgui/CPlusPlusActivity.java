package th.nguyenxuandat.quizappgui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CPlusPlusActivity extends AppCompatActivity {

    private TextView questionTextView;
    private CardView cardA, cardB, cardC, cardD;
    private TextView answerTextA, answerTextB, answerTextC, answerTextD;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int totalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cplus_plus);

        questionTextView = findViewById(R.id.txtQuestionCPP);
        cardA = findViewById(R.id.cardACPP);
        cardB = findViewById(R.id.cardBCPP);
        cardC = findViewById(R.id.cardCCPP);
        cardD = findViewById(R.id.cardDCPP);
        answerTextA = findViewById(R.id.answerTextA);
        answerTextB = findViewById(R.id.answerTextB);
        answerTextC = findViewById(R.id.answerTextC);
        answerTextD = findViewById(R.id.answerTextD);

        questionList = getQuestions();
        Collections.shuffle(questionList);

        displayQuestion();

        setAnswerClickListeners();

        findViewById(R.id.btnNextCPP).setOnClickListener(v -> {
            if (currentQuestionIndex < questionList.size() - 1) {
                currentQuestionIndex++;
                displayQuestion();
            } else {
                Intent intent = new Intent(CPlusPlusActivity.this, ResultActivity.class);
                intent.putExtra("totalScore", totalScore);
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayQuestion() {
        resetCardColors();

        Question currentQuestion = questionList.get(currentQuestionIndex);

        questionTextView.setText(currentQuestion.getQuestion());
        answerTextA.setText(currentQuestion.getOptionA());
        answerTextB.setText(currentQuestion.getOptionB());
        answerTextC.setText(currentQuestion.getOptionC());
        answerTextD.setText(currentQuestion.getOptionD());
    }

    private void setAnswerClickListeners() {
        cardA.setOnClickListener(v -> checkAnswer("A", cardA));
        cardB.setOnClickListener(v -> checkAnswer("B", cardB));
        cardC.setOnClickListener(v -> checkAnswer("C", cardC));
        cardD.setOnClickListener(v -> checkAnswer("D", cardD));
    }

    private void checkAnswer(String selectedAnswer, CardView cardView) {
        Question currentQuestion = questionList.get(currentQuestionIndex);

        if (currentQuestion.getCorrectAnswer().equals(selectedAnswer)) {
            cardView.setCardBackgroundColor(Color.GREEN);
            totalScore++;
        } else {
            cardView.setCardBackgroundColor(Color.RED);
            showCorrectAnswer();
        }
    }

    private void resetCardColors() {
        cardA.setCardBackgroundColor(Color.WHITE);
        cardB.setCardBackgroundColor(Color.WHITE);
        cardC.setCardBackgroundColor(Color.WHITE);
        cardD.setCardBackgroundColor(Color.WHITE);
    }

    private void showCorrectAnswer() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        switch (currentQuestion.getCorrectAnswer()) {
            case "A":
                cardA.setCardBackgroundColor(Color.GREEN);
                break;
            case "B":
                cardB.setCardBackgroundColor(Color.GREEN);
                break;
            case "C":
                cardC.setCardBackgroundColor(Color.GREEN);
                break;
            case "D":
                cardD.setCardBackgroundColor(Color.GREEN);
                break;
        }
    }

    private List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is C++?", "A programming language", "A fruit", "A car brand", "A smartphone", "A"));
        questions.add(new Question("Who created C++?", "Bjarne Stroustrup", "Bill Gates", "Mark Zuckerberg", "Dennis Ritchie", "A"));
        return questions;
    }
}

