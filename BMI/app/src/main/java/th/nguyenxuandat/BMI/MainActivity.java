package th.nguyenxuandat.BMI;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần
        EditText editHeight = findViewById(R.id.editHeight);
        EditText editWeight = findViewById(R.id.editWeight);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button btnCalculate = findViewById(R.id.btnCalculate);
        TextView textResult = findViewById(R.id.textResult);

        btnCalculate.setOnClickListener(v -> {
            try {
                // Lấy giá trị chiều cao và cân nặng
                double height = Double.parseDouble(editHeight.getText().toString()) / 100; // cm -> m
                double weight = Double.parseDouble(editWeight.getText().toString());
                // Tính BMI
                double bmi = weight / (height * height);
                String classification;
                // Kiểm tra lựa chọn
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.radioAsian) {
                    // Phân loại theo Asian
                    if (bmi < 17.5) classification = "Underweight";
                    else if (bmi < 23) classification = "Normal weight";
                    else if (bmi < 28) classification = "Overweight";
                    else classification = "Obese";
                } else {
                    // Phân loại theo WHO
                    if (bmi < 18.5) classification = "Underweight";
                    else if (bmi < 25) classification = "Normal weight";
                    else if (bmi < 30) classification = "Overweight";
                    else classification = "Obese";
                }
                // Hiển thị kết quả
                textResult.setText(String.format("BMI: %.2f\nClassification: %s", bmi, classification));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Vui lòng nhập giá trị hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
