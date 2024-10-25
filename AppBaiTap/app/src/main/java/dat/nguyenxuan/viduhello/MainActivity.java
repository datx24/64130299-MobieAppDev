package dat.nguyenxuan.viduhello;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private double laySo(String s) {
        return Double.parseDouble(s);
    }

    private void hienThiKetQua(String ketQua) {
        TextView txtResult = findViewById(R.id.edtResult);
        txtResult.setText("Kết quả: " + ketQua);
    }

    public void TinhCong(View v) {
        tinhToan('+');
    }

    public void TinhTru(View v) {
        tinhToan('-');
    }

    public void TinhNhan(View v) {
        tinhToan('*');
    }

    public void TinhChia(View v) {
        tinhToan('/');
    }

    private void tinhToan(char phepToan) {
        EditText soThuNhat = findViewById(R.id.edtNum);
        EditText soThuHai = findViewById(R.id.edtNum1);

        String s1 = soThuNhat.getText().toString();
        String s2 = soThuHai.getText().toString();

        if (s1.isEmpty() || s2.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập cả hai số.", Toast.LENGTH_SHORT).show();
            return;
        }

        double num1 = laySo(s1);
        double num2 = laySo(s2);
        double ketQua;

        switch (phepToan) {
            case '+':
                ketQua = num1 + num2;
                break;
            case '-':
                ketQua = num1 - num2;
                break;
            case '*':
                ketQua = num1 * num2;
                break;
            case '/':
                ketQua = num2 != 0 ? num1 / num2 : Double.NaN; // Kiểm tra chia cho 0
                break;
            default:
                ketQua = 0;
        }

        if (phepToan == '/' && num2 == 0) {
            hienThiKetQua("Không thể chia cho 0");
        } else {
            hienThiKetQua(String.valueOf(ketQua));
        }
    }
}