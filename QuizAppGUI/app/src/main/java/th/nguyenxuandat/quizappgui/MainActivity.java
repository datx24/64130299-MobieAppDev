package th.nguyenxuandat.quizappgui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView btnCplusplus = findViewById(R.id.btnCplusplus);
        CardView btnJava = findViewById(R.id.btnJava);
        CardView btnPython = findViewById(R.id.btnPython);

        btnCplusplus.setOnClickListener(v -> navigateTo(CPlusPlusActivity.class));
        btnJava.setOnClickListener(v -> navigateTo(JavaActivity.class));
        btnPython.setOnClickListener(v -> navigateTo(PythonActivity.class));
    }

    private void navigateTo(Class<?> destination) {
        Intent intent = new Intent(MainActivity.this, destination);
        startActivity(intent);
    }
}
