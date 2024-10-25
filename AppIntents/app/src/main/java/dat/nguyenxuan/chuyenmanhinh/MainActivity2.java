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

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
    }

    //XML onClick = ChuyenVeManHinhChinh
    public void ChuyenVeManHinhChinh(View v) {
        //1.Lấy intent về
        Intent iNhanDuoc = getIntent();
        //2. Bọc dữ liệu
        String data= iNhanDuoc.getStringExtra("ten");
        //3. Hiện lên
        Toast.makeText(getBaseContext(),data,Toast.LENGTH_LONG).show();
        //4. Chuyển về màn hình chính
        Intent iManHinhChinh = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(iManHinhChinh);
    }
}