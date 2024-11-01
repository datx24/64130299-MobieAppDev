package vn.nguyenxuandat.apponkiemtragiuaki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import vn.datnguyenxuanxuan.apponkiemtragiuaki.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    //XML onClick = ChuyenSangCau1
    public void ChuyenSangCau1(View v) {
        //Tạo intent
        Intent iC1 = new Intent(MainActivity.this,MainCau1.class);
        //Gói dữ liệu
        iC1.putExtra("cau1","Tính BMI");
        //Chuyển màn hình
        startActivity(iC1);
    }

    //XML onClick = ChuyenSangCau2
    public void ChuyenSangCau2(View v) {
        //Tạo intent
        Intent iC2 = new Intent(MainActivity.this,MainCau2.class);
        //Gói dữ liệu
        iC2.putExtra("cau2","Profile");
        //Chuyển màn hình
        startActivity(iC2);
    }

    //XML onClick = ChuyenSangCau3
    public void ChuyenSangCau3(View v) {
        //Tạo intent
        Intent iC3 = new Intent(MainActivity.this,MainCau3.class);
        //Gói dữ liệu
        iC3.putExtra("cau3","Listview");
        //Chuyển màn hình
        startActivity(iC3);
    }

    //XML onClick = ChuyenSangCau4
    public void ChuyenSangCau4(View v) {
        //Tạo intent
        Intent iC4 = new Intent(MainActivity.this,MainCau4.class);
        //Gói dữ liệu
        iC4.putExtra("cau4","RecyclerView");
        //Chuyển màn hình
        startActivity(iC4);
    }
}