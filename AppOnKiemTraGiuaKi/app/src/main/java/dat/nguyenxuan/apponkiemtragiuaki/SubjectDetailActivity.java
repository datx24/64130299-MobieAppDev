package dat.nguyenxuan.apponkiemtragiuaki;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SubjectDetailActivity extends AppCompatActivity {
    private TextView subjectNameTextView, subjectCodeTextView, subjectCreditsTextView,
            subjectSemesterTextView, subjectYearTextView, subjectTypeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        // Link UI elements
        subjectNameTextView = findViewById(R.id.subjectNameTextView);
        subjectCodeTextView = findViewById(R.id.subjectCodeTextView);
        subjectCreditsTextView = findViewById(R.id.subjectCreditsTextView);
        subjectSemesterTextView = findViewById(R.id.subjectSemesterTextView);
        subjectYearTextView = findViewById(R.id.subjectYearTextView);
        subjectTypeTextView = findViewById(R.id.subjectTypeTextView);

        // Get data from Intent
        String subjectName = getIntent().getStringExtra("subjectName");
        String subjectCode = getIntent().getStringExtra("subjectCode");
        int subjectCredits = getIntent().getIntExtra("subjectCredits", 0);
        int subjectSemester = getIntent().getIntExtra("subjectSemester", 0);
        String subjectYear = getIntent().getStringExtra("subjectYear");
        String subjectType = getIntent().getStringExtra("subjectType");

        // Set the data in TextViews
        subjectNameTextView.setText("Tên môn học: " + subjectName);
        subjectCodeTextView.setText("Mã môn học: " + subjectCode);
        subjectCreditsTextView.setText("Số tín chỉ: " + subjectCredits);
        subjectSemesterTextView.setText("Học kì: " + subjectSemester);
        subjectYearTextView.setText("Năm học: " + subjectYear);
        subjectTypeTextView.setText("Nhóm kiến thức: " + subjectType);
    }
}
