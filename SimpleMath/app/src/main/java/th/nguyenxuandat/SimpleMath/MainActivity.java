package th.nguyenxuandat.SimpleMath;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

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
    }
}