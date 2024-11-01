package vn.nguyenxuandat.apponkiemtragiuaki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import vn.datnguyenxuanxuan.apponkiemtragiuaki.R;


// Java code
public class MainCau1 extends AppCompatActivity {
    private EditText editTextNumber1;
    private EditText editTextNumber2;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cau1); // Replace with your actual layout name

        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        textViewResult = findViewById(R.id.textViewResult);
    }

    public void calculateSum(View view) {
        // Get the numbers from the EditTexts
        String num1Str = editTextNumber1.getText().toString();
        String num2Str = editTextNumber2.getText().toString();

        if (!num1Str.isEmpty() && !num2Str.isEmpty()) {
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);
            double sum = num1 + num2;

            // Display the result
            textViewResult.setText("Kết quả: " + sum);
        } else {
            textViewResult.setText("Vui lòng nhập cả hai số.");
        }
    }
}
