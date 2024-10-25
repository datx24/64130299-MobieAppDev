package dat.nguyenxuan.TinhToanSoHoc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dat.nguyenxuan.viduhello.R;

public class MainTinhToanSoHoc extends AppCompatActivity {

    Button bCong, bTru, bNhan, bChia;
    EditText editTextA, editTextB;
    TextView textViewKetQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tinh_toan_so_hoc);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getControls();

        // Gắn sự kiện cho các nút
        bCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XuLy_Cong(v);  // Gọi hàm xử lý phép cộng
            }
        });

        bTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XuLy_Tru(v);  // Gọi hàm xử lý phép trừ
            }
        });

        bNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XuLy_Nhan(v);  // Gọi hàm xử lý phép nhân
            }
        });

        bChia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XuLy_Chia(v);  // Gọi hàm xử lý phép chia
            }
        });
    }

    // Hàm tìm các điều khiển, cất vào biến ở trên
    void getControls() {
        bCong = findViewById(R.id.btnAdd);
        bTru = findViewById(R.id.btnSubtract);
        bNhan = findViewById(R.id.btnMultiply);
        bChia = findViewById(R.id.btnDivide);
        editTextA = findViewById(R.id.edtNum1);
        editTextB = findViewById(R.id.edtNum2);
        textViewKetQua = findViewById(R.id.txtResult);
    }

    // Hàm xử lý phép cộng
    public void XuLy_Cong(View v) {
        double a = Double.parseDouble(editTextA.getText().toString());
        double b = Double.parseDouble(editTextB.getText().toString());
        double kq = a + b;
        textViewKetQua.setText("Kết quả: " + String.valueOf(kq));
    }

    // Hàm xử lý phép trừ
    public void XuLy_Tru(View v) {
        double a = Double.parseDouble(editTextA.getText().toString());
        double b = Double.parseDouble(editTextB.getText().toString());
        double kq = a - b;
        textViewKetQua.setText("Kết quả: " + String.valueOf(kq));
    }

    // Hàm xử lý phép nhân
    public void XuLy_Nhan(View v) {
        double a = Double.parseDouble(editTextA.getText().toString());
        double b = Double.parseDouble(editTextB.getText().toString());
        double kq = a * b;
        textViewKetQua.setText("Kết quả: " + String.valueOf(kq));
    }

    // Hàm xử lý phép chia
    public void XuLy_Chia(View v) {
        double a = Double.parseDouble(editTextA.getText().toString());
        double b = Double.parseDouble(editTextB.getText().toString());
        if (b != 0) {
            double kq = a / b;
            textViewKetQua.setText("Kết quả: " + String.valueOf(kq));
        } else {
            textViewKetQua.setText("Không thể chia cho 0");
        }
    }
}
