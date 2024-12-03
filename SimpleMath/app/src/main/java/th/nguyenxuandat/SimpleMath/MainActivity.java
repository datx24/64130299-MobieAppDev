package th.nguyenxuandat.SimpleMath;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ánh xạ các thành phần trên giao diện
        EditText inputA = findViewById(R.id.inputA);
        EditText inputB = findViewById(R.id.inputB);
        EditText result = findViewById(R.id.editResults);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button btnCalculate = findViewById(R.id.btnResult);

        // Xử lý sự kiện khi người dùng nhấn nút
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Lấy dữ liệu từ input
                    double numberA = Double.parseDouble(inputA.getText().toString());
                    double numberB = Double.parseDouble(inputB.getText().toString());

                    // Xác định phép toán được chọn
                    int selectedOperation = radioGroup.getCheckedRadioButtonId();
                    double calculatedResult = 0;

                    if(selectedOperation == -1) {
                        Toast.makeText(MainActivity.this, "Vui lòng chọn phép toán!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (selectedOperation == R.id.radioButtonPlus) {
                        calculatedResult = numberA + numberB;
                    } else if (selectedOperation == R.id.radioButtonSubtract) {
                        calculatedResult = numberA - numberB;
                    } else if (selectedOperation == R.id.radioButtonMultiplication) {
                        calculatedResult = numberA * numberB;
                    } else if (selectedOperation == R.id.radioButtonDivide) {
                        if (numberB != 0) {
                            calculatedResult = numberA / numberB;
                        } else {
                            Toast.makeText(MainActivity.this, "Không thể chia cho 0!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // Hiển thị kết quả
                    result.setText(String.valueOf(calculatedResult));
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}