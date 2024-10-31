package dat.nguyenxuan.apponkiemtragiuaki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainCau1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_cau1);
    }

    //XML onClick = ChuyenVeManHinhChinh
    public void ChuyenVeManHinhChinh(View v) {
        //Lấy intent về
        Intent iNhanDuoc = getIntent();
        //Bọc dữ liệu
        String data = iNhanDuoc.getStringExtra("cau1");
        //Hiện lên
        Toast.makeText(getBaseContext(), data, Toast.LENGTH_SHORT).show();
        //Chuyển về màn hình chính
        Intent iManHinhChinh = new Intent(MainCau1.this,MainActivity.class);
        startActivity(iManHinhChinh);
    }
}