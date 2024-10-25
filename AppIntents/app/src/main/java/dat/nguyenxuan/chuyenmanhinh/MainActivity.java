package dat.nguyenxuan.chuyenmanhinh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    }

    // XML onClick = ChuyenSangManHinh2
    public void ChuyenSangManHinh2(View v) {
        //1.Tạo Intent
        Intent iMH2 = new Intent(MainActivity.this, MainActivity2.class);
        //2. Gọi dữ liệu
        iMH2.putExtra("ten","Nguyễn Xuân Đạt"); //ví dụ này không cần
        //3. Chuyển màn hình
        startActivity(iMH2);
    }


}