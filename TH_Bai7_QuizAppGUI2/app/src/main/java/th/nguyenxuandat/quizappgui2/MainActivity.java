package th.nguyenxuandat.quizappgui2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView questionText, txtAnswer1, txtAnswer2, txtAnswer3, txtAnswer4;
    private CardView btnAnswerA, btnAnswerB, btnAnswerC, btnAnswerD;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean isAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.questionText);
        txtAnswer1 = findViewById(R.id.txtAnswer1);
        txtAnswer2 = findViewById(R.id.txtAnswer2);
        txtAnswer3 = findViewById(R.id.txtAnswer3);
        txtAnswer4 = findViewById(R.id.txtAnswer4);
        btnAnswerA = findViewById(R.id.cardViewAnswerA);
        btnAnswerB = findViewById(R.id.cardViewAnswerB);
        btnAnswerC = findViewById(R.id.cardViewAnswerC);
        btnAnswerD = findViewById(R.id.cardViewAnswerD);

        questionList = loadQuestions();

        displayQuestion();

        View.OnClickListener answerClickListener = view -> {
            if (!isAnswered) {
                CardView selectedCard = (CardView) view;
                String selectedAnswer = "";
                if (selectedCard == btnAnswerA) {
                    selectedAnswer = txtAnswer1.getText().toString();
                } else if (selectedCard == btnAnswerB) {
                    selectedAnswer = txtAnswer2.getText().toString();
                } else if (selectedCard == btnAnswerC) {
                    selectedAnswer = txtAnswer3.getText().toString();
                } else if (selectedCard == btnAnswerD) {
                    selectedAnswer = txtAnswer4.getText().toString();
                }

                checkAnswer(selectedCard, selectedAnswer);
                isAnswered = true;
            }
        };

        btnAnswerA.setOnClickListener(answerClickListener);
        btnAnswerB.setOnClickListener(answerClickListener);
        btnAnswerC.setOnClickListener(answerClickListener);
        btnAnswerD.setOnClickListener(answerClickListener);

        findViewById(R.id.btnNext).setOnClickListener(view -> {
            if (currentQuestionIndex < questionList.size() - 1) {
                currentQuestionIndex++;
                isAnswered = false;
                displayQuestion();
            } else {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("SCORE", score);
                intent.putExtra("TOTAL", questionList.size());
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayQuestion() {
        resetCardColors();
        Question currentQuestion = questionList.get(currentQuestionIndex);
        questionText.setText(currentQuestion.getQuestion());
        txtAnswer1.setText(currentQuestion.getOptions()[0]);
        txtAnswer2.setText(currentQuestion.getOptions()[1]);
        txtAnswer3.setText(currentQuestion.getOptions()[2]);
        txtAnswer4.setText(currentQuestion.getOptions()[3]);
    }

    private void checkAnswer(CardView selectedCard, String selectedAnswer) {
        String correctAnswer = questionList.get(currentQuestionIndex).getCorrectAnswer();
        if (selectedAnswer.equals(correctAnswer)) {
            selectedCard.setCardBackgroundColor(Color.GREEN);
            score++;
        } else {
            selectedCard.setCardBackgroundColor(Color.RED);
        }
        disableClick();
    }

    private void resetCardColors() {
        btnAnswerA.setCardBackgroundColor(Color.parseColor("#5D9BA4"));
        btnAnswerB.setCardBackgroundColor(Color.parseColor("#5D9BA4"));
        btnAnswerC.setCardBackgroundColor(Color.parseColor("#5D9BA4"));
        btnAnswerD.setCardBackgroundColor(Color.parseColor("#5D9BA4"));
        enableClick();
    }

    private void enableClick() {
        btnAnswerA.setClickable(true);
        btnAnswerB.setClickable(true);
        btnAnswerC.setClickable(true);
        btnAnswerD.setClickable(true);
    }

    private void disableClick() {
        btnAnswerA.setClickable(false);
        btnAnswerB.setClickable(false);
        btnAnswerC.setClickable(false);
        btnAnswerD.setClickable(false);
    }

    private List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of Vietnam?", shuffleAnswers(new String[]{"Hanoi", "Ho Chi Minh City", "Da Nang", "Hue"}), "Hanoi"));
        questions.add(new Question("Who is the first President of Vietnam?", shuffleAnswers(new String[]{"Ho Chi Minh", "Nguyen Ai Quoc", "Pham Van Dong", "Le Duan"}), "Ho Chi Minh"));
        questions.add(new Question("What is the largest river in Vietnam?", shuffleAnswers(new String[]{"Mekong", "Red River", "Dong Nai", "Ma River"}), "Mekong"));
        questions.add(new Question("Which city is known as the economic hub of Vietnam?", shuffleAnswers(new String[]{"Hanoi", "Ho Chi Minh City", "Da Nang", "Can Tho"}), "Ho Chi Minh City"));
        questions.add(new Question("What is the official language of Vietnam?", shuffleAnswers(new String[]{"Vietnamese", "English", "Chinese", "French"}), "Vietnamese"));
        questions.add(new Question("Which Vietnamese food is considered a national dish?", shuffleAnswers(new String[]{"Pho", "Bánh Mì", "Bún Chả", "Goi Cuon"}), "Pho"));
        questions.add(new Question("Which of the following is a UNESCO World Heritage Site in Vietnam?", shuffleAnswers(new String[]{"Halong Bay", "Sa Pa", "Phong Nha-Kẻ Bàng", "Mekong Delta"}), "Halong Bay"));
        questions.add(new Question("What is the currency of Vietnam?", shuffleAnswers(new String[]{"Dong", "Baht", "Riel", "Dollar"}), "Dong"));
        questions.add(new Question("Which year did Vietnam reunify?", shuffleAnswers(new String[]{"1975", "1965", "1990", "1954"}), "1975"));
        questions.add(new Question("What is the national flower of Vietnam?", shuffleAnswers(new String[]{"Lotus", "Orchid", "Rose", "Tulip"}), "Lotus"));
        return questions;
    }
    private String[] shuffleAnswers(String[] answers) {
        List<String> answerList = new ArrayList<>();
        Collections.addAll(answerList, answers);
        Collections.shuffle(answerList);
        return answerList.toArray(new String[0]);
}
}
