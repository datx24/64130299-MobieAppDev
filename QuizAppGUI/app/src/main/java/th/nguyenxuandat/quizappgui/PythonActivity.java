package th.nguyenxuandat.quizappgui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PythonActivity extends AppCompatActivity {

    private TextView questionTextView;
    private CardView cardA, cardB, cardC, cardD;
    private TextView answerTextA, answerTextB, answerTextC, answerTextD;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int totalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_python);

        questionTextView = findViewById(R.id.txtQuestionPython);
        cardA = findViewById(R.id.cardAPython);
        cardB = findViewById(R.id.cardBPython);
        cardC = findViewById(R.id.cardCPython);
        cardD = findViewById(R.id.cardDPython);
        answerTextA = findViewById(R.id.answerTextAPython);
        answerTextB = findViewById(R.id.answerTextBPython);
        answerTextC = findViewById(R.id.answerTextCPython);
        answerTextD = findViewById(R.id.answerTextDPython);

        questionList = getQuestions();
        Collections.shuffle(questionList);

        displayQuestion();

        setAnswerClickListeners();

        findViewById(R.id.btnNextPython).setOnClickListener(v -> {
            if (currentQuestionIndex < questionList.size() - 1) {
                currentQuestionIndex++;
                displayQuestion();
            } else {
                Intent intent = new Intent(PythonActivity.this, ResultActivity.class);
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
        questions.add(new Question("What is Python?", "A programming language", "A beverage", "A car model", "A planet", "A"));
        questions.add(new Question("Who created Python?", "Guido van Rossum", "James Gosling", "Brendan Eich", "Dennis Ritchie", "A"));
        questions.add(new Question("Which company owns Python?", "Python Software Foundation", "Oracle", "Google", "Apple", "A"));
        questions.add(new Question("What is PEP 8?", "Python Enhancement Proposal", "Python Execution Program", "Python Engine Process", "None of the above", "A"));
        questions.add(new Question("Which keyword is used to define a function in Python?", "def", "function", "define", "None of the above", "A"));
        return questions;
    }
}