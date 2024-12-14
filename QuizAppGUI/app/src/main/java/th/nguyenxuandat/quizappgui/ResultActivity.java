package th.nguyenxuandat.quizappgui;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        Button retryButton = findViewById(R.id.retryButton);

        Intent intent = getIntent();
        int totalScore = intent.getIntExtra("totalScore", 0);

        scoreTextView.setText("Your Score: " + totalScore);

        retryButton.setOnClickListener(v -> {
            Intent retryIntent = new Intent(ResultActivity.this, CPlusPlusActivity.class);
            startActivity(retryIntent);
            finish();
        });
    }
}

