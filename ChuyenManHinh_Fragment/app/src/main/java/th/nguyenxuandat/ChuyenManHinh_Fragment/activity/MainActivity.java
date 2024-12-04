package th.nguyenxuandat.ChuyenManHinh_Fragment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import th.nguyenxuandat.ChuyenManHinh_Fragment.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEnglish = findViewById(R.id.btnEnglish);
        Button btnMath = findViewById(R.id.btnMath);
        Button btnProgramming = findViewById(R.id.btnProgramming);

        //Chuyển đến màn hình English
        btnEnglish.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EnglishActivity.class);
            startActivity(intent);
        });

        //Chuyển đến màn hình Math
        btnMath.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,MathActivity.class);
            startActivity(intent);
        });

        //Chuyển đến màn hình Programming
        btnProgramming.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,ProgrammingActivity.class);
            startActivity(intent);
        });
    }
}